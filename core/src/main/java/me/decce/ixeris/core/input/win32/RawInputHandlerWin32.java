package me.decce.ixeris.core.input.win32;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.CommonCallbacks;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowFocusCallbackDispatcher;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import me.decce.ixeris.core.input.RawInputHandler;
import me.decce.ixeris.core.natives.win32.RAWINPUT;
import me.decce.ixeris.core.natives.win32.RAWINPUTHEADER;
import me.decce.ixeris.core.natives.win32.RAWKEYBOARD;
import me.decce.ixeris.core.natives.win32.RAWMOUSE;
import me.decce.ixeris.core.natives.win32.User32Ex;
import me.decce.ixeris.core.natives.win32.Win32Exception;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.system.windows.User32;
import org.lwjgl.system.windows.MSG;
import org.lwjgl.system.windows.POINT;
import org.lwjgl.system.windows.RECT;
import org.lwjgl.system.windows.WinBase;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class RawInputHandlerWin32 implements RawInputHandler {
    private final long glfwWindow;
    private final long hWnd;
    private final POINT point = POINT.calloc();
    private final RECT rect = RECT.calloc();
    private final MSG msg = MSG.calloc();
    private final IntBuffer sizeBuffer;
    private RAWINPUT.Buffer rawInput;
    private boolean grabbed;
    private boolean lostFocus;
    private int size;
    private int lastCursorPosX;
    private int lastCursorPosY;
    private int grabbedCursorPosX;
    private int grabbedCursorPosY;
    private boolean receivedWMQuit;
    private boolean unsupported;
    private int wmQuitExitCode;

    public RawInputHandlerWin32(long glfwWindow) {
        this.glfwWindow = glfwWindow;
        this.hWnd = GLFWNativeWin32.glfwGetWin32Window(glfwWindow);
        if (this.hWnd <= 0) {
            throw new IllegalArgumentException("Invalid HWND %d for window %d".formatted(hWnd, glfwWindow));
        }
        this.size = Ixeris.getConfig().getMinRawInputBufferSize();
        this.sizeBuffer = BufferUtils.createIntBuffer(1);
        this.createBuffer(this.size);
        WindowFocusCallbackDispatcher.get(glfwWindow).registerMainThreadCallback(this::onWindowFocusChanged);
    }

    private void onWindowFocusChanged(long glfwWindow, boolean focused) {
        if (this.glfwWindow != glfwWindow || !Ixeris.input().isRawInputEnabled()) {
            return;
        }
        if (grabbed && !focused) {
            lostFocus = true;
        }
    }

    private boolean useRawMouse() {
        return Ixeris.getConfig().isBufferedRawMouse();
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
        Ixeris.LOGGER.debug("Created raw input buffer of size {}", size);
    }

    @Override
    public void grab() {
        if (grabbed) {
            return;
        }
        grabbed = true;

        if (this.useRawMouse()) {
            RawInputDevices.MOUSE.register(hWnd);
        }
        if (this.useRawKeyboard()) {
            RawInputDevices.KEYBOARD.register(hWnd);
        }
    }

    @Override
    public void release() {
        if (!grabbed) {
            return;
        }
        grabbed = false;

        if (this.useRawMouse()) {
            RawInputDevices.MOUSE.unregister();
        }
        if (this.useRawKeyboard()) {
            RawInputDevices.KEYBOARD.unregister();
        }

        handleRawInput(); // Handle WM_INPUT messages already in the event queue
    }

    @Override
    public void pollEvents() {
        handleRawInput();

        handleMessages();

        if (isWindowFocusedAndGrabbed()) {
            centerCursor();
        }
    }

    @Override
    public boolean supported() {
        return !unsupported;
    }

    private void handleRawInput() {
        this.sizeBuffer.put(0, size * RAWINPUTHEADER.SIZEOF);
        int totalCount = 0;
        while (true) {
            var count = User32Ex.GetRawInputBuffer(rawInput.get(0), sizeBuffer, RAWINPUTHEADER.SIZEOF);
            if (count == -1) {
                this.setUnsupported();
                return;
            }
            else if (count == 0) {
                break;
            }
            totalCount += count;
            for (int i = 0; i < count; i++) {
                var data = rawInput.get(i);
                var dwType = data.header().dwType();
                if (dwType == User32Ex.RIM_TYPEMOUSE) {
                    processMouse(data.mouse());
                }
                else if (dwType == User32Ex.RIM_TYPEKEYBOARD) {
                    processKeyboard(data.keyboard());
                }
            }
        }

        if (totalCount > size) {
            this.createBuffer(Math.min(totalCount, Ixeris.getConfig().getMaxRawInputBufferSize()));
        }
    }

    private void setUnsupported() {
        if (!unsupported) {
            unsupported = true;
            Ixeris.LOGGER.error("Failed to get raw input buffer! Buffered raw input will be disabled.", new Win32Exception(WinBase.GetLastError()));
            this.release();
            // If we could reach here, raw mouse motion must be enabled, so no query needed
            GLFW.glfwSetInputMode(glfwWindow, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
        }
    }

    private void processKeyboard(RAWKEYBOARD keyboard) {
        var flags = keyboard.Flags();
        var makeCode = keyboard.MakeCode();
        var vKey = keyboard.VKey();
        if (makeCode == User32Ex.KEYBOARD_OVERRUN_MAKE_CODE || vKey >= User32Ex.UCHAR_MAX) {
            return;
        }

        // Properly handling keyboard input: https://blog.molecular-matters.com/2011/09/05/properly-handling-keyboard-input/
        if (vKey == User32.VK_SHIFT) {
            // correct left-hand / right-hand SHIFT
            vKey = (short) User32Ex.MapVirtualKeyW(makeCode, User32Ex.MAPVK_VSC_TO_VK_EX);
        }
        else if (vKey == User32.VK_NUMLOCK) {
            // correct PAUSE/BREAK and NUM LOCK silliness, and set the extended bit
            makeCode = (short) (User32Ex.MapVirtualKeyW(vKey, User32Ex.MAPVK_VK_TO_VSC) | 0x100);
        }
        boolean isE0 = ((flags & User32Ex.RI_KEY_E0) != 0);
        boolean isE1 = ((flags & User32Ex.RI_KEY_E1) != 0);

        if (isE1)
        {
            // for escaped sequences, turn the virtual key into the correct scan code using MapVirtualKey.
            // however, MapVirtualKey is unable to map User32.VK_PAUSE (this is a known bug), hence we map that by hand.
            if (vKey == User32.VK_PAUSE)
                makeCode = 0x45;
            else
                makeCode = (short) User32Ex.MapVirtualKeyW(vKey, User32Ex.MAPVK_VK_TO_VSC);
        }

        int glfwKey = switch (vKey)
        {
            // right-hand CONTROL and ALT have their e0 bit set
            case User32.VK_CONTROL -> isE0 ?
                    GLFW_KEY_RIGHT_CONTROL :
                    GLFW_KEY_LEFT_CONTROL;
            case User32.VK_MENU -> isE0 ?
                    GLFW_KEY_RIGHT_ALT :
                    GLFW_KEY_LEFT_ALT;
            // NUMPAD ENTER has its e0 bit set
            case User32.VK_RETURN -> isE0 ?
                    GLFW_KEY_KP_ENTER :
                    GLFW_KEY_ENTER;
            // the standard INSERT, DELETE, HOME, END, PRIOR and NEXT keys will always have their e0 bit set, but the
            // corresponding keys on the NUMPAD will not.
            case User32.VK_INSERT -> !isE0 ?
                    GLFW_KEY_KP_0 :
                    GLFW_KEY_INSERT;
            case User32.VK_DELETE -> !isE0 ?
                    GLFW_KEY_KP_DECIMAL :
                    GLFW_KEY_DELETE;
            case User32.VK_HOME -> !isE0 ?
                    GLFW_KEY_KP_7 :
                    GLFW_KEY_HOME;
            case User32.VK_END -> !isE0 ?
                    GLFW_KEY_KP_1 :
                    GLFW_KEY_END;
            case User32.VK_PRIOR -> !isE0 ?
                    GLFW_KEY_KP_9 :
                    GLFW_KEY_PAGE_UP;
            case User32.VK_NEXT -> !isE0 ?
                    GLFW_KEY_KP_3 :
                    GLFW_KEY_PAGE_DOWN;
            // the standard arrow keys will always have their e0 bit set, but the
            // corresponding keys on the NUMPAD will not.
            case User32.VK_LEFT -> !isE0 ?
                    GLFW_KEY_KP_4 :
                    GLFW_KEY_LEFT;
            case User32.VK_RIGHT -> !isE0 ?
                    GLFW_KEY_KP_6 :
                    GLFW_KEY_RIGHT;
            case User32.VK_UP -> !isE0 ?
                    GLFW_KEY_KP_8 :
                    GLFW_KEY_UP;
            case User32.VK_DOWN -> !isE0 ?
                    GLFW_KEY_KP_2 :
                    GLFW_KEY_DOWN;
            // NUMPAD 5 doesn't have its e0 bit set
            case User32.VK_CLEAR -> !isE0 ?
                    GLFW_KEY_KP_5 :
                    GLFW_KEY_NUM_LOCK; //TODO?
            default -> KeyCodeTranslatorWin32.keycodes[makeCode];
        };

        var release = (flags & User32Ex.RI_KEY_BREAK) != 0;

        inputKey(glfwKey, makeCode, release ? GLFW_RELEASE : GLFW_PRESS, getKeyMods());
    }

    private void centerCursor() {
        int width, height;
        if (GlfwCacheManager.hasWindowCache(this.glfwWindow)) {
            var cache = GlfwCacheManager.getWindowCache(glfwWindow);
            width = cache.windowSize().width();
            height = cache.windowSize().height();
        }
        else {
            User32Ex.GetClientRect(hWnd, rect);
            width = rect.right();
            height = rect.bottom();
        }
        if (lastCursorPosX != width / 2 || lastCursorPosY != height / 2) {
            lastCursorPosX = width / 2;
            lastCursorPosY = height / 2;
            point.set(lastCursorPosX, lastCursorPosY);
            User32Ex.ClientToScreen(hWnd, point);
            User32.SetCursorPos(point.x(), point.y());
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
        if ((usFlags & User32Ex.MOUSE_MOVE_ABSOLUTE) != 0)
        {
            int x = 0, y = 0;
            int width, height;

            if ((usFlags & User32Ex.MOUSE_VIRTUAL_DESKTOP) != 0) {
                x += User32.GetSystemMetrics(User32.SM_XVIRTUALSCREEN);
                y += User32.GetSystemMetrics(User32.SM_YVIRTUALSCREEN);
                width = User32.GetSystemMetrics(User32.SM_CXVIRTUALSCREEN);
                height = User32.GetSystemMetrics(User32.SM_CYVIRTUALSCREEN);
            }
            else {
                width = User32.GetSystemMetrics(User32.SM_CXSCREEN);
                height = User32.GetSystemMetrics(User32.SM_CYSCREEN);
            }

            x += (int) ((mouse.lLastX() / 65535.0f) * width);
            y += (int) ((mouse.lLastY() / 65535.0f) * height);

            point.set(x, y);
            User32Ex.ScreenToClient(hWnd, point);
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
        if ((buttonFlags & User32Ex.RI_MOUSE_LEFT_BUTTON_DOWN) != 0) {
            inputRawMouseButton(User32.WM_LBUTTONDOWN, 0);
        }
        if ((buttonFlags & User32Ex.RI_MOUSE_LEFT_BUTTON_UP) != 0) {
            inputRawMouseButton(User32.WM_LBUTTONUP, 0);
        }
        if ((buttonFlags & User32Ex.RI_MOUSE_RIGHT_BUTTON_DOWN) != 0) {
            inputRawMouseButton(User32.WM_RBUTTONDOWN, 0);
        }
        if ((buttonFlags & User32Ex.RI_MOUSE_RIGHT_BUTTON_UP) != 0) {
            inputRawMouseButton(User32.WM_RBUTTONUP, 0);
        }
        if ((buttonFlags & User32Ex.RI_MOUSE_MIDDLE_BUTTON_DOWN) != 0) {
            inputRawMouseButton(User32.WM_MBUTTONDOWN, 0);
        }
        if ((buttonFlags & User32Ex.RI_MOUSE_MIDDLE_BUTTON_UP) != 0) {
            inputRawMouseButton(User32.WM_MBUTTONUP, 0);
        }
        if ((buttonFlags & User32Ex.RI_MOUSE_BUTTON_4_DOWN) != 0) {
            inputRawMouseButton(User32.WM_XBUTTONDOWN, User32.XBUTTON1 << 16);
        }
        if ((buttonFlags & User32Ex.RI_MOUSE_BUTTON_4_UP) != 0) {
            inputRawMouseButton(User32.WM_XBUTTONUP, User32.XBUTTON1 << 16);
        }
        if ((buttonFlags & User32Ex.RI_MOUSE_BUTTON_5_DOWN) != 0) {
            inputRawMouseButton(User32.WM_XBUTTONDOWN, User32.XBUTTON2 << 16);
        }
        if ((buttonFlags & User32Ex.RI_MOUSE_BUTTON_5_UP) != 0) {
            inputRawMouseButton(User32.WM_XBUTTONUP, User32.XBUTTON2 << 16);
        }
    }

    private void processScroll(short buttonFlags, short buttonData) {
        if ((buttonFlags & User32Ex.RI_MOUSE_WHEEL) != 0) {
            inputScroll(0.0, buttonData / (double) User32.WHEEL_DELTA);
        }

        if ((buttonFlags & User32Ex.RI_MOUSE_HWHEEL) != 0) {
            inputScroll(-buttonData / (double) User32.WHEEL_DELTA, 0.0);
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
        CommonCallbacks.keyCallback.invoke(glfwWindow, key, scancode, action, mods);
    }

    private void inputRawMouseButton(int message, int wParam) {
        if (message == User32.WM_LBUTTONDOWN || message == User32.WM_LBUTTONUP || message == User32.WM_RBUTTONDOWN || message == User32.WM_RBUTTONUP) {
            message = considerSwapButtons(message);
        }
        inputMouseButton(message, wParam);
    }

    private void inputMouseButton(int message, int wParam) {
        //TODO: sticky mouse buttons support
        msg.hwnd(hWnd)
            .message(message)
            .wParam(wParam)
            // lParam indicates the coordinate of the cursor; GLFW does not use it so we can safely leave it to zero.
            .lParam(0);
        User32.DispatchMessage(msg);

        if (lostFocus) {
            lostFocus = false;
            User32Ex.SetFocus(hWnd);
        }
    }

    private void inputScroll(double xoffset, double yoffset) {
        CommonCallbacks.scrollCallback.invoke(glfwWindow, xoffset, yoffset);
    }

    private static int considerSwapButtons(int message) {
        var swap = User32.GetSystemMetrics(User32.SM_SWAPBUTTON) != 0;
        if (swap) {
            if (message == User32.WM_LBUTTONDOWN) message = User32.WM_RBUTTONDOWN;
            else if (message == User32.WM_LBUTTONUP) message = User32.WM_RBUTTONUP;
            else if (message == User32.WM_RBUTTONDOWN) message = User32.WM_LBUTTONDOWN;
            else if (message == User32.WM_RBUTTONUP) message = User32.WM_LBUTTONUP;
        }
        return message;
    }

    private static int getKeyMods()
    {
        int mods = 0;

        //TODO optimize downcalls
        if ((User32Ex.GetKeyState(User32.VK_SHIFT) & 0x8000) != 0)
            mods |= GLFW_MOD_SHIFT;
        if ((User32Ex.GetKeyState(User32.VK_CONTROL) & 0x8000) != 0)
            mods |= GLFW_MOD_CONTROL;
        if ((User32Ex.GetKeyState(User32.VK_MENU) & 0x8000) != 0)
            mods |= GLFW_MOD_ALT;
        if (((User32Ex.GetKeyState(User32.VK_LWIN) | User32Ex.GetKeyState(User32.VK_RWIN)) & 0x8000) != 0)
            mods |= GLFW_MOD_SUPER;
        if ((User32Ex.GetKeyState(User32.VK_CAPITAL) & 1) != 0)
            mods |= GLFW_MOD_CAPS_LOCK;
        if ((User32Ex.GetKeyState(User32.VK_NUMLOCK) & 1) != 0)
            mods |= GLFW_MOD_NUM_LOCK;

        return mods;
    }

    private boolean isWindowFocused() {
        if (GlfwCacheManager.hasWindowCache(glfwWindow)) {
            var cache = GlfwCacheManager.getWindowCache(glfwWindow);
            return cache.attrib().get(GLFW_FOCUSED) == GLFW_TRUE;
        }
        return User32Ex.GetActiveWindow() == this.hWnd;
    }

    private boolean isWindowFocusedAndGrabbed() {
        return grabbed && isWindowFocused();
    }

    /*
    * QUIRK! Avoid using the filter parameters.
    *
    * Previously, we filtered out the User32.WM_INPUT messages here, by first trying to find messages before User32.WM_INPUT, then
    * messages after User32.WM_INPUT. However, this caused issues with some applications, most notably IMEs, presumably because
    * the event queue is read in a non-FIFO manner, though that does not stand as a  plausible explanation. The symptom
    * was the game window flickered every ~3 seconds, seeming as if the window has been hidden and re-shown. losing focus
    * then regaining it instantly. This issue only happens when idle (not moving mouse) and if the mouse button has not
    * been pressed when grabbing the cursor. There were also some obscure messages with a nonexistent hWnd and message
    * code (0x60).
    *
    * To address that, we changed to first poll events on the game window (specifying the hWnd parameter), keeping the
    * filters. Then we poll again with -1 as hWnd which would find thread messages. However, there was no easy way to
    * poll all events on other windows, so they remain untouched, causing issues again. The symptoms included not being
    * able to move, still when IMEs are enabled.
    *
    * Our current solution is to accept *any* message, but when we find a User32.WM_INPUT message, we keep it in the event
    * queue and trigger the buffered raw input reading code. This means we might do the buffered read more than once
    * every time we poll events, but when there are fewer input messages in the queue GetRawInputBuffer is also faster,
    * so this seems an acceptable solution.
    * */
    private boolean findMessage(MSG msg) {
        if (User32.PeekMessage(msg, 0, 0, 0, User32.PM_NOREMOVE)) {
            if (msg.message() == User32.WM_INPUT && !unsupported) {
                handleRawInput(); // this will remove the User32.WM_INPUT messages from the event queue
                return findMessage(msg);
            }
            else {
                // This PeekMessage will get the same MSG as the previous NOREMOVE call, but remove it from
                // the event queue
                return User32.PeekMessage(msg, 0, 0, 0, User32.PM_REMOVE);
            }
        }
        return false;
    }

    private void handleMessages() {
        while (findMessage(msg)) {
            processMessage(msg);
        }

        if (receivedWMQuit) {
            receivedWMQuit = false;
            User32.PostMessage(null, 0, User32.WM_QUIT, wmQuitExitCode, 0);
            GLFW.glfwPollEvents();
        }
    }

    private void processMessage(MSG msg) {
        if (msg.message() == User32.WM_QUIT) {
            receivedWMQuit = true; // GLFW processes this message in the event loop, not window procedure, so we repost the event later and call glfwPollEvents
            wmQuitExitCode = (int) msg.wParam();
        }
        User32.TranslateMessage(msg);
        User32.DispatchMessage(msg);
    }
}
