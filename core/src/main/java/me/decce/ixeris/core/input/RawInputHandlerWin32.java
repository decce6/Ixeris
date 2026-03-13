package me.decce.ixeris.core.input;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.CommonCallbacks;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import me.decce.ixeris.core.input.win32.KeyCodeTranslatorWin32;
import me.decce.ixeris.core.win32.RAWINPUT;
import me.decce.ixeris.core.win32.RAWINPUTDEVICE;
import me.decce.ixeris.core.win32.RAWINPUTHEADER;
import me.decce.ixeris.core.win32.RAWKEYBOARD;
import me.decce.ixeris.core.win32.RAWMOUSE;
import me.decce.ixeris.core.win32.User32;
import me.decce.ixeris.core.win32.Win32Exception;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.system.windows.MSG;
import org.lwjgl.system.windows.POINT;
import org.lwjgl.system.windows.RECT;
import org.lwjgl.system.windows.WinBase;

import static me.decce.ixeris.core.win32.User32.GetActiveWindow;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_HOME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_INSERT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DECIMAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_NUM_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.windows.User32.*;
import static org.lwjgl.system.windows.User32.DispatchMessage;
import static org.lwjgl.system.windows.User32.PM_REMOVE;
import static org.lwjgl.system.windows.User32.PeekMessage;
import static org.lwjgl.system.windows.User32.PostMessage;
import static org.lwjgl.system.windows.User32.TranslateMessage;
import static org.lwjgl.system.windows.User32.WM_INPUT;
import static org.lwjgl.system.windows.User32.WM_QUIT;

public class RawInputHandlerWin32 implements RawInputHandler {
    private final long glfwWindow;
    private final long hWnd;
    private final POINT point = POINT.calloc();
    private final MSG msg = MSG.calloc();
    private RAWINPUTDEVICE rawKeyboard;
    private RAWINPUTDEVICE rawMouse;
    private RAWINPUTDEVICE unregisterRawMouse;
    private RAWINPUT.Buffer rawInput;
    private boolean grabbed;
    private int size;
    private int lastCursorPosX;
    private int lastCursorPosY;
    private int grabbedCursorPosX;
    private int grabbedCursorPosY;
    private boolean receivedWMQuit;
    private int wmQuitExitCode;

    public RawInputHandlerWin32(long glfwWindow) {
        this.glfwWindow = glfwWindow;
        this.hWnd = GLFWNativeWin32.glfwGetWin32Window(glfwWindow);
        if (this.hWnd <= 0) {
            throw new IllegalArgumentException("Invalid HWND %d for window %d".formatted(hWnd, glfwWindow));
        }
        this.size = Ixeris.getConfig().getMinRawInputBufferSize();
        this.createBuffer(this.size);
        this.setup();
        Ixeris.LOGGER.info("Set up raw input handler for {} (hWnd={})", glfwWindow, hWnd);
    }

    private boolean useRawKeyboard() {
        return Ixeris.getConfig().isBufferedRawKeyboard();
    }

    private void createBuffer(int size) {
        if (this.rawInput != null && size == this.size) {
            return;
        }
        if (this.rawInput != null) {
            this.rawInput.free();
        }
        this.size = size;
        this.rawInput = RAWINPUT.calloc(size);
        Ixeris.LOGGER.info("Created raw input buffer of size {}", size);
    }

    private RAWINPUTDEVICE getRawKeyboard() {
        if (this.rawKeyboard == null) {
            this.rawKeyboard = RAWINPUTDEVICE.create()
                    .usUsagePage(User32.HID_USAGE_PAGE_GENERIC)
                    .usUsage(User32.HID_USAGE_GENERIC_KEYBOARD)
                    .dwFlags(User32.RIDEV_NOLEGACY)
                    .hwndTarget(hWnd);
        }
        return this.rawKeyboard;
    }

    private RAWINPUTDEVICE getRawMouse() {
        if (this.rawMouse == null) {
            this.rawMouse = RAWINPUTDEVICE.create()
                    .usUsagePage(User32.HID_USAGE_PAGE_GENERIC)
                    .usUsage(User32.HID_USAGE_GENERIC_MOUSE)
                    .dwFlags(User32.RIDEV_NOLEGACY)
                    .hwndTarget(hWnd);
        }
        return this.rawMouse;
    }

    private RAWINPUTDEVICE getUnregisterRawMouse() {
        if (this.unregisterRawMouse == null) {
            this.unregisterRawMouse = RAWINPUTDEVICE.create()
                    .usUsagePage(User32.HID_USAGE_PAGE_GENERIC)
                    .usUsage(User32.HID_USAGE_GENERIC_MOUSE)
                    .dwFlags(User32.RIDEV_REMOVE)
                    .hwndTarget(0);
        }
        return this.unregisterRawMouse;
    }

    private void register(RAWINPUTDEVICE device) {
        if (!User32.RegisterRawInputDevices(device, 1, device.sizeof())) {
            throw new Win32Exception("Failed to register raw input device! %s %s %s %s".formatted(device.usUsagePage(), device.usUsage(), device.dwFlags(), device.hwndTarget()), WinBase.GetLastError());
        }
    }

    public void setup() {
        if (useRawKeyboard()) {
            Ixeris.LOGGER.info("register raw keyboard");
            var rid = this.getRawKeyboard();
            this.register(rid);
        }
    }

    @Override
    public void grab() {
        if (grabbed) {
            return;
        }
        grabbed = true;

        Ixeris.LOGGER.info("Grab");
        var rid = this.getRawMouse();
        register(rid);
    }

    @Override
    public void release() {
        if (!grabbed) {
            return;
        }
        grabbed = false;

        Ixeris.LOGGER.info("Release");
        var rid = getUnregisterRawMouse();
        register(rid);
    }

    @Override
    public void pollEvents() {
        handleRawInput();

        if (isWindowFocusedAndGrabbed()) {
            centerCursor();
        }

        handleMessages();
    }

    private void handleRawInput() {
        try (var stack = stackPush()) {
            var sizeBuffer = stack.ints(size * RAWINPUTHEADER.SIZEOF);
            int totalCount = 0;
            while (true) {
                var count = User32.GetRawInputBuffer(rawInput.get(0), sizeBuffer, RAWINPUTHEADER.SIZEOF);
                if (count == -1) {
                    throw new Win32Exception("Failed to get raw input buffer", WinBase.GetLastError());
                }
                else if (count == 0) {
                    break;
                }
                totalCount += count;
                for (int i = 0; i < count; i++) {
                    var data = rawInput.get(i);
                    var dwType = data.header().dwType();
                    if (dwType == User32.RIM_TYPEMOUSE) {
                        processMouse(data.mouse());
                    }
                    else if (dwType == User32.RIM_TYPEKEYBOARD) {
                        processKeyboard(data.keyboard());
                    }
                }
            }

            if (totalCount > size) {
                this.createBuffer(Math.min(totalCount, Ixeris.getConfig().getMaxRawInputBufferSize()));
            }
        }
    }

    private void processKeyboard(RAWKEYBOARD keyboard) {
        var flags = keyboard.Flags();
        var makeCode = keyboard.MakeCode();
        var vKey = keyboard.VKey();
        if (makeCode == User32.KEYBOARD_OVERRUN_MAKE_CODE || vKey >= User32.UCHAR_MAX) {
            return;
        }

        // Properly handling keyboard input: https://blog.molecular-matters.com/2011/09/05/properly-handling-keyboard-input/
        if (vKey == VK_SHIFT) {
            // correct left-hand / right-hand SHIFT
            vKey = (short) User32.MapVirtualKeyW(makeCode, User32.MAPVK_VSC_TO_VK_EX);
        }
        else if (vKey == VK_NUMLOCK) {
            // correct PAUSE/BREAK and NUM LOCK silliness, and set the extended bit
            makeCode = (short) (User32.MapVirtualKeyW(vKey, User32.MAPVK_VK_TO_VSC) | 0x100);
        }
        boolean isE0 = ((flags & User32.RI_KEY_E0) != 0);
        boolean isE1 = ((flags & User32.RI_KEY_E1) != 0);

        if (isE1)
        {
            // for escaped sequences, turn the virtual key into the correct scan code using MapVirtualKey.
            // however, MapVirtualKey is unable to map VK_PAUSE (this is a known bug), hence we map that by hand.
            if (vKey == VK_PAUSE)
                makeCode = 0x45;
            else
                makeCode = (short) User32.MapVirtualKeyW(vKey, User32.MAPVK_VK_TO_VSC);
        }

        int glfwKey = switch (vKey)
        {
            // right-hand CONTROL and ALT have their e0 bit set
            case VK_CONTROL -> isE0 ?
                    GLFW_KEY_RIGHT_CONTROL :
                    GLFW_KEY_LEFT_CONTROL;
            case VK_MENU -> isE0 ?
                    GLFW_KEY_RIGHT_ALT :
                    GLFW_KEY_LEFT_ALT;
            // NUMPAD ENTER has its e0 bit set
            case VK_RETURN -> isE0 ?
                    GLFW_KEY_KP_ENTER :
                    GLFW_KEY_ENTER;
            // the standard INSERT, DELETE, HOME, END, PRIOR and NEXT keys will always have their e0 bit set, but the
            // corresponding keys on the NUMPAD will not.
            case VK_INSERT -> !isE0 ?
                    GLFW_KEY_KP_0 :
                    GLFW_KEY_INSERT;
            case VK_DELETE -> !isE0 ?
                    GLFW_KEY_KP_DECIMAL :
                    GLFW_KEY_DELETE;
            case VK_HOME -> !isE0 ?
                    GLFW_KEY_KP_7 :
                    GLFW_KEY_HOME;
            case VK_END -> !isE0 ?
                    GLFW_KEY_KP_1 :
                    GLFW_KEY_END;
            case VK_PRIOR -> !isE0 ?
                    GLFW_KEY_KP_9 :
                    GLFW_KEY_PAGE_UP;
            case VK_NEXT -> !isE0 ?
                    GLFW_KEY_KP_3 :
                    GLFW_KEY_PAGE_DOWN;
            // the standard arrow keys will always have their e0 bit set, but the
            // corresponding keys on the NUMPAD will not.
            case VK_LEFT -> !isE0 ?
                    GLFW_KEY_KP_4 :
                    GLFW_KEY_LEFT;
            case VK_RIGHT -> !isE0 ?
                    GLFW_KEY_KP_6 :
                    GLFW_KEY_RIGHT;
            case VK_UP -> !isE0 ?
                    GLFW_KEY_KP_8 :
                    GLFW_KEY_UP;
            case VK_DOWN -> !isE0 ?
                    GLFW_KEY_KP_2 :
                    GLFW_KEY_DOWN;
            // NUMPAD 5 doesn't have its e0 bit set
            case VK_CLEAR -> !isE0 ?
                    GLFW_KEY_KP_5 :
                    GLFW_KEY_NUM_LOCK; //TODO?
            default -> KeyCodeTranslatorWin32.keycodes[makeCode];
        };

        var release = (flags & User32.RI_KEY_BREAK) != 0;

        inputKey(glfwKey, makeCode, release ? GLFW_RELEASE : GLFW_PRESS, getKeyMods());
    }

    private void centerCursor() {
        try (var stack = stackPush()) {
            int width, height;
            if (GlfwCacheManager.hasWindowCache(this.glfwWindow)) {
                var cache = GlfwCacheManager.getWindowCache(glfwWindow);
                width = cache.windowSize().width();
                height = cache.windowSize().height();
            }
            else {
                var rect = RECT.malloc(stack);
                User32.GetClientRect(hWnd, rect);
                width = rect.right();
                height = rect.bottom();
            }
            if (lastCursorPosX != width / 2 || lastCursorPosY != height / 2) {
                lastCursorPosX = width / 2;
                lastCursorPosY = height / 2;
                point.set(lastCursorPosX, lastCursorPosY);
                ClientToScreen(hWnd, point);
                SetCursorPos(point.x(), point.y());
            }
        }
    }

    /*
    * Based on the RAWMOUSE handling code from [GLFW](https://github.com/glfw/glfw/blob/master/src/win32_window.c)
    * */
    private void processMouse(RAWMOUSE mouse) {
        int dx = 0, dy = 0;

        var usFlags = mouse.usFlags();
        var lLastX = mouse.lLastX();
        var lLastY = mouse.lLastY();
        if ((usFlags & User32.MOUSE_MOVE_ABSOLUTE) != 0)
        {
            int x = 0, y = 0;
            int width, height;

            if ((usFlags & User32.MOUSE_VIRTUAL_DESKTOP) != 0) {
                x += GetSystemMetrics(SM_XVIRTUALSCREEN);
                y += GetSystemMetrics(SM_YVIRTUALSCREEN);
                width = GetSystemMetrics(SM_CXVIRTUALSCREEN);
                height = GetSystemMetrics(SM_CYVIRTUALSCREEN);
            }
            else {
                width = GetSystemMetrics(SM_CXSCREEN);
                height = GetSystemMetrics(SM_CYSCREEN);
            }

            x += (int) ((mouse.lLastX() / 65535.0f) * width);
            y += (int) ((mouse.lLastY() / 65535.0f) * height);

            point.set(x, y);
            User32.ScreenToClient(hWnd, point);
            dx = point.x() - lastCursorPosX;
            dy = point.y() - lastCursorPosY;
        }
        else if (lLastX != 0 || lLastY != 0) {
            dx = lLastX;
            dy = lLastY;
        }

        if (dx != 0 || dy != 0) {
            inputCursorPos(grabbedCursorPosX + dx, grabbedCursorPosY + dy);
            lastCursorPosX += dx;
            lastCursorPosY += dy;
        }

        var buttonFlags = mouse.usButtonFlags();
        var buttonData = mouse.usButtonData();

        processMouseButton(buttonFlags);
        processScroll(buttonFlags, buttonData);
    }

    private void processMouseButton(short buttonFlags) {
        if ((buttonFlags & User32.RI_MOUSE_LEFT_BUTTON_DOWN) != 0) {
            inputMouseButton(GLFW_MOUSE_BUTTON_LEFT, GLFW_PRESS, getKeyMods());
        }
        if ((buttonFlags & User32.RI_MOUSE_LEFT_BUTTON_UP) != 0) {
            inputMouseButton(GLFW_MOUSE_BUTTON_LEFT, GLFW_RELEASE, getKeyMods());
        }
        if ((buttonFlags & User32.RI_MOUSE_RIGHT_BUTTON_DOWN) != 0) {
            inputMouseButton(GLFW_MOUSE_BUTTON_RIGHT, GLFW_PRESS, getKeyMods());
        }
        if ((buttonFlags & User32.RI_MOUSE_RIGHT_BUTTON_UP) != 0) {
            inputMouseButton(GLFW_MOUSE_BUTTON_RIGHT, GLFW_RELEASE, getKeyMods());
        }
        if ((buttonFlags & User32.RI_MOUSE_MIDDLE_BUTTON_DOWN) != 0) {
            inputMouseButton(GLFW_MOUSE_BUTTON_MIDDLE, GLFW_PRESS, getKeyMods());
        }
        if ((buttonFlags & User32.RI_MOUSE_MIDDLE_BUTTON_UP) != 0) {
            inputMouseButton(GLFW_MOUSE_BUTTON_MIDDLE, GLFW_RELEASE, getKeyMods());
        }
        if ((buttonFlags & User32.RI_MOUSE_BUTTON_4_DOWN) != 0) {
            inputMouseButton(GLFW_MOUSE_BUTTON_4, GLFW_PRESS, getKeyMods());
        }
        if ((buttonFlags & User32.RI_MOUSE_BUTTON_4_UP) != 0) {
            inputMouseButton(GLFW_MOUSE_BUTTON_4, GLFW_RELEASE, getKeyMods());
        }
        if ((buttonFlags & User32.RI_MOUSE_BUTTON_5_DOWN) != 0) {
            inputMouseButton(GLFW_MOUSE_BUTTON_5, GLFW_PRESS, getKeyMods());
        }
        if ((buttonFlags & User32.RI_MOUSE_BUTTON_5_UP) != 0) {
            inputMouseButton(GLFW_MOUSE_BUTTON_5, GLFW_RELEASE, getKeyMods());
        }
    }

    private void processScroll(short buttonFlags, short buttonData) {
        if ((buttonFlags & User32.RI_MOUSE_WHEEL) != 0) {
            inputScroll(0.0, buttonData / (double) WHEEL_DELTA);
        }

        if ((buttonFlags & User32.RI_MOUSE_HWHEEL) != 0) {
            inputScroll(-buttonData / (double) WHEEL_DELTA, 0.0);
        }
    }

    private void inputCursorPos(int x, int y) {
        if (grabbedCursorPosX == x && grabbedCursorPosY == y) {
            return;
        }
        grabbedCursorPosX = x;
        grabbedCursorPosY = y;
        CommonCallbacks.cursorPosCallback.invoke(glfwWindow, x, y);
    }

    private void inputKey(int key, int scancode, int action, int mods) {
        Ixeris.LOGGER.info("InputKey {} {} {} {}", key, scancode, action, mods);
        CommonCallbacks.keyCallback.invoke(glfwWindow, key, scancode, action, mods);
    }

    private void inputMouseButton(int button, int action, int mods) {
        //TODO: sticky mouse buttons support
        if (button == GLFW_MOUSE_BUTTON_LEFT || button == GLFW_MOUSE_BUTTON_RIGHT) {
            button = considerSwapButtons(button);
        }
        CommonCallbacks.mouseButtonCallback.invoke(glfwWindow, button, action, mods);
    }

    private void inputScroll(double xoffset, double yoffset) {
        CommonCallbacks.scrollCallback.invoke(glfwWindow, xoffset, yoffset);
    }

    private static int considerSwapButtons(int button) {
        var swap = GetSystemMetrics(SM_SWAPBUTTON) != 0;
        if (swap) {
            if (button == GLFW_MOUSE_BUTTON_LEFT) button = GLFW_MOUSE_BUTTON_RIGHT;
            else if (button == GLFW_MOUSE_BUTTON_RIGHT) button = GLFW_MOUSE_BUTTON_LEFT;
        }
        return button;
    }

    private static int getKeyMods()
    {
        int mods = 0;

        //TODO optimize downcalls
        if ((User32.GetKeyState(VK_SHIFT) & 0x8000) != 0)
            mods |= GLFW_MOD_SHIFT;
        if ((User32.GetKeyState(VK_CONTROL) & 0x8000) != 0)
            mods |= GLFW_MOD_CONTROL;
        if ((User32.GetKeyState(VK_MENU) & 0x8000) != 0)
            mods |= GLFW_MOD_ALT;
        if (((User32.GetKeyState(VK_LWIN) | User32.GetKeyState(VK_RWIN)) & 0x8000) != 0)
            mods |= GLFW_MOD_SUPER;
        if ((User32.GetKeyState(VK_CAPITAL) & 1) != 0)
            mods |= GLFW_MOD_CAPS_LOCK;
        if ((User32.GetKeyState(VK_NUMLOCK) & 1) != 0)
            mods |= GLFW_MOD_NUM_LOCK;

        return mods;
    }

    private boolean isWindowFocused() {
        if (GlfwCacheManager.hasWindowCache(glfwWindow)) {
            var cache = GlfwCacheManager.getWindowCache(glfwWindow);
            return cache.attrib().get(GLFW_FOCUSED) == GLFW_TRUE;
        }
        return GetActiveWindow() == this.hWnd;
    }

    private boolean isWindowFocusedAndGrabbed() {
        return grabbed && isWindowFocused();
    }

    private void checkMessage(MSG msg) {
        /* We used to pass 0 as the hWnd parameter when calling PeekMessage, which means reading all events for the
         * current thread. However, that caused the game window to intermittently lose & regain focus. The window
         * also seemingly got hidden and re-shown. At this moment, some spurious messages are posted - with a
         * nonexistent hWnd (not found in Spy++) and an invalid message (0x60).
         * We have worked around the issue by only reading messages from the game window and the thread messages; the
         * debug code is intentionally kept for future reference & investigation
         */

        if (msg.hwnd() != hWnd) {
            Ixeris.LOGGER.warn("{} {} {} {}", msg.hwnd(), msg.message(), msg.wParam(), msg.lParam());
        }

    }

    private boolean findMessage(MSG msg) {
        if (isWindowFocusedAndGrabbed()) {
            // Filter out the WM_INPUT events - they should be handled via GetRawInputBuffer
            // First find messages *before* WM_INPUT
            if (PeekMessage(msg, hWnd, 0, WM_INPUT - 1, PM_REMOVE)) {
                return true;
            }
            // Then find messages *after* WM_INPUT
            // Pass 0x0000FFFF as wMsgFilterMax: the documentation for this function requires that applications only
            // use the low word in the parameter; the high word is reserved for the system.
            // See: https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-peekmessagew
            if (PeekMessage(msg, hWnd, WM_INPUT + 1, 0x0000FFFF, PM_REMOVE)) {
                return true;
            }
            // Retrieve thread messages
            return PeekMessage(msg, -1, 0, 0, PM_REMOVE);
        }
        // When the cursor is not grabbed, or the window non-focused, we simply read all messages
        return PeekMessage(msg, 0, 0, 0, PM_REMOVE);
    }

    private void handleMessages() {
        while (findMessage(msg)) {
            Ixeris.LOGGER.info("Found msg {} {} {} {}", msg.hwnd(), msg.message(), msg.wParam(), msg.lParam());
            checkMessage(msg);
            processMessage(msg);
        }

        if (receivedWMQuit) {
            receivedWMQuit = false;
            PostMessage(null, 0, WM_QUIT, wmQuitExitCode, 0);
            GLFW.glfwPollEvents();
        }
    }

    private void processMessage(MSG msg) {
        if (msg.message() == WM_QUIT) {
            receivedWMQuit = true; // GLFW processes this message in the event loop, not window procedure, so we repost the event later and call glfwPollEvents
            wmQuitExitCode = (int) msg.wParam();
        }
        TranslateMessage(msg);
        DispatchMessage(msg);
    }
}
