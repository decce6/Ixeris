package me.decce.ixeris.core.input.win32;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.IxerisConfig;
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
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
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
    private static final int FIND_MESSAGE_MAXIMUM_RECURSION = 15;
    // The maximum amount of messages to read from the event queue during each poll, in THROTTLED mode
    // Inspired by https://ph3at.github.io/posts/Windows-Input/
    private static final int MESSAGE_THROTTLE_THRESHOLD = 5;
    private final IxerisConfig.MessageOptimizationStrategy messageOptimizationStrategy;
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
    private int findMessageRecursionGuard;
    private int messagesReadInCurrentPoll;

    public RawInputHandlerWin32(long glfwWindow) {
        this.glfwWindow = glfwWindow;
        this.hWnd = GLFWNativeWin32.glfwGetWin32Window(glfwWindow);
        if (this.hWnd == 0) {
            throw new IllegalArgumentException("Invalid HWND %d for window %d".formatted(hWnd, glfwWindow));
        }
        this.messageOptimizationStrategy = Ixeris.getConfig().getMessageOptimizationStrategy();
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
        Ixeris.LOGGER.debug("Created raw input buffer of size {} {}", size, size * RAWINPUTHEADER.SIZEOF);
    }

    private void growBuffer() {
        int newSize = Math.min(size * 2, Ixeris.getConfig().getMaxRawInputBufferSize());
        if (size < newSize) {
            this.createBuffer(newSize);
        }
    }

    @Override
    public void grab() {
        if (grabbed) {
            return;
        }
        grabbed = true;

        if (this.useRawMouse()) {
            RawInputDevice.getMouseDevice().register(hWnd);
        }
        if (this.useRawKeyboard()) {
            RawInputDevice.getKeyboardDevice().register(hWnd);
        }

        setIgnoreNextMove();
    }

    @Override
    public void release() {
        if (!grabbed) {
            return;
        }
        grabbed = false;

        if (this.useRawMouse()) {
            RawInputDevice.getMouseDevice().unregister();
        }
        if (this.useRawKeyboard()) {
            RawInputDevice.getKeyboardDevice().unregister();
        }

        handleRawInput(); // Handle WM_INPUT messages already in the event queue

        setIgnoreNextMove();
    }

    @Override
    public void pollEvents() {
        if (grabbed) {
            handleRawInput();
        }

        handleMessages();

        if (isWindowFocusedAndGrabbed()) {
            centerCursor();
        }
    }

    @Override
    public void setCursorPos(double x, double y) {
        this.lastCursorPosX = (int) x;
        this.lastCursorPosY = (int) y;
        if (grabbed) {
            this.grabbedCursorPosX = (int) x;
            this.grabbedCursorPosY = (int) y;
        }
    }

    @Override
    public boolean supported() {
        return !unsupported;
    }

    private void setIgnoreNextMove() {
        // Because our cursor pos may be different from the internal ones of GLFW, when we switch between them (i.e.
        // when grabbing/releasing cursor) we need to tell Minecraft to ignore the next movement and just update its
        // absolute mouse position
        // Using RenderThreadDispatcher.runLater makes it run on the render thread, before the next callback is invoked
        RenderThreadDispatcher.runLater(() -> Ixeris.accessor.setIgnoreFirstMouseMove());
    }

    private void handleRawInput() {
        int totalCount = 0;
        while (true) {
            this.sizeBuffer.put(0, size * RAWINPUT.SIZEOF);
            var count = User32Ex.GetRawInputBuffer(rawInput.get(0), sizeBuffer, RAWINPUTHEADER.SIZEOF);
            if (count == -1) {
                if (size < Ixeris.getConfig().getMaxRawInputBufferSize()) {
                    this.growBuffer(); // GetRawInputBuffer may fail when the buffer is too small, with error code 122 (ERROR_INSUFFICIENT_BUFFER)
                }
                else {
                    this.setUnsupported();
                }
                return;
            }
            else if (count == 0) {
                break;
            }
            totalCount += count;
            processRawInputBuffer(this.rawInput, count);
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

    private void processRawInputBuffer(RAWINPUT.Buffer buffer, int count) {
        for (int i = 0; i < count; i++) {
            var data = buffer.get(i);
            var dwType = data.header().dwType();
            if (dwType == User32Ex.RIM_TYPEMOUSE) {
                processMouse(data.mouse());
            }
            else if (dwType == User32Ex.RIM_TYPEKEYBOARD) {
                processKeyboard(data.keyboard());
            }
        }
    }

    private void processKeyboard(RAWKEYBOARD keyboard) {
        short flags = keyboard.Flags();
        short makeCode = keyboard.MakeCode();
        short vKey = keyboard.VKey();
        if (makeCode == User32Ex.KEYBOARD_OVERRUN_MAKE_CODE || vKey >= User32Ex.UCHAR_MAX) {
            return;
        }
        boolean release = (flags & User32Ex.RI_KEY_BREAK) != 0;
        boolean e0 = (flags & User32Ex.RI_KEY_E0) != 0;
        boolean e1 = (flags & User32Ex.RI_KEY_E1) != 0;

        int scanCode;
        if (makeCode == 0) {
            scanCode = User32Ex.MapVirtualKeyW(vKey, User32Ex.MAPVK_VK_TO_VSC_EX);
        }
        else {
            scanCode = makeCode & 0x7F;
        }

        // See [WM_KEYDOWN](https://learn.microsoft.com/en-us/windows/win32/inputdev/wm-keydown and
        // [WM_KEYUP](https://learn.microsoft.com/en-us/windows/win32/inputdev/wm-keyup)
        int lParam = 0;
        // Bits 0~15: repeat count
        lParam |= 1;
        // Bits 16~23: scan code
        //lParam |= scanCode << 16;
        lParam |= scanCode << 16;
        // Bit 24: extended flag
        lParam |= (e0 || e1) ? (1 << 24) : 0;
        // Bits 25~28: system reserved
        // Bit 29: context code (0 for both WM_KEYDOWN and WM_KEYUP)
        // Bit 30: previous state (1 if the key is down before the message is sent, 0 if it is up)
        lParam |= release ? 1 << 30 : 0;
        // Bit 31: transition state (0 for WM_KEYDOWN, 1 for WM_KEYUP)
        lParam |= release ? 1 << 31 : 0;

        inputRawKey(release ? User32.WM_KEYUP : User32.WM_KEYDOWN, vKey, lParam);
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

    private void processMouse(RAWMOUSE mouse) {
        var usFlags = mouse.usFlags();
        var lLastX = mouse.lLastX();
        var lLastY = mouse.lLastY();

        processCursor(usFlags,  lLastX, lLastY);

        var buttonFlags = mouse.usButtonFlags();
        var buttonData = mouse.usButtonData();

        processMouseButton(buttonFlags);
        processScroll(buttonFlags, buttonData);
    }

    private static int mulDiv(int number, int numerator, int denominator) {
        return Math.round(((long) number * numerator) * 1.0f / denominator);
    }

    // See: https://learn.microsoft.com/en-us/windows/win32/api/winuser/ns-winuser-rawmouse
    private void processCursor(short usFlags, int lLastX, int lLastY) {
        int dx = 0, dy = 0;

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

            x += mulDiv(lLastX, width, User32Ex.USHRT_MAX);
            y += mulDiv(lLastY, height, User32Ex.USHRT_MAX);

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

    private void inputRawKey(int message, int wParam, int lParam) {
        // Update internal keyboard state before dispatching message. This makes sure any updated modifier key will be seen by GLFW, which uses GetKeyState
        updateKeyState(message, wParam);
        // No need to call TranslateMessage because we don't want character messages in grabbed mode
        dispatchMessage(message, wParam, lParam);
    }

    private void inputRawMouseButton(int message, int wParam) {
        if (message == User32.WM_LBUTTONDOWN || message == User32.WM_LBUTTONUP || message == User32.WM_RBUTTONDOWN || message == User32.WM_RBUTTONUP) {
            message = considerSwapButtons(message);
        }
        inputMouseButton(message, wParam);
    }

    private void inputMouseButton(int message, int wParam) {
        //TODO: sticky mouse buttons support
        // lParam indicates the coordinate of the cursor; GLFW does not use it so we can safely leave it to zero.
        dispatchMessage(message, wParam, 0);
        updateMessageButtonState(message);

        if (lostFocus) {
            lostFocus = false;
            User32Ex.SetFocus(hWnd);
        }
    }

    private void updateKeyState(int message, int vKey) {
        if (message == User32.WM_KEYDOWN) {
            KeyboardStateHelper.setDown(vKey);
        }
        else if (message == User32.WM_KEYUP) {
            KeyboardStateHelper.setUp(vKey);
        }
    }

    private void updateMessageButtonState(int message) {
        // Updates the mouse button state read by GetKeyState, which reflects the state when the last message was posted
        // Note: this currently ignores XButtons, which are not usually read by external applications
        switch (message) {
            case User32.WM_LBUTTONDOWN -> KeyboardStateHelper.setDown(User32.VK_LBUTTON);
            case User32.WM_LBUTTONUP -> KeyboardStateHelper.setUp(User32.VK_LBUTTON);
            case User32.WM_MBUTTONDOWN -> KeyboardStateHelper.setDown(User32.VK_MBUTTON);
            case User32.WM_MBUTTONUP -> KeyboardStateHelper.setUp(User32.VK_MBUTTON);
            case User32.WM_RBUTTONDOWN -> KeyboardStateHelper.setDown(User32.VK_RBUTTON);
            case User32.WM_RBUTTONUP -> KeyboardStateHelper.setUp(User32.VK_RBUTTON);
        }
    }

    private void dispatchMessage(int message, int wParam, int lParam) {
        msg.hwnd(hWnd).message(message).wParam(wParam).lParam(lParam);
        User32.DispatchMessage(msg);
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
    *
    * Version 4.2.0: Added findMessageRecursionGuard to prevent StackOverflowException's, as some overlays seem to
    * block getting raw input buffer.
    * */
    private boolean findMessage(MSG msg) {
        findMessageRecursionGuard = 0;
        return innerFindMessage(msg);
    }

    private boolean innerFindMessage(MSG msg) {
        messagesReadInCurrentPoll++;
        if (shouldAbortMessageReading()) {
            return false;
        }
        findMessageRecursionGuard++;
        if (User32.PeekMessage(msg, 0, 0, 0, User32.PM_NOREMOVE)) {
            if (msg.message() == User32.WM_INPUT && msg.hwnd() == this.hWnd && !unsupported && findMessageRecursionGuard <= FIND_MESSAGE_MAXIMUM_RECURSION) {
                handleRawInput(); // this will remove the User32.WM_INPUT messages from the event queue
                return innerFindMessage(msg);
            }
            else {
                // This PeekMessage will get the same MSG as the previous NOREMOVE call, but remove it from
                // the event queue
                return User32.PeekMessage(msg, 0, 0, 0, User32.PM_REMOVE);
            }
        }
        return false;
    }

    private boolean shouldAbortMessageReading() {
        return messageOptimizationStrategy == IxerisConfig.MessageOptimizationStrategy.THROTTLED &&
                grabbed &&
                messagesReadInCurrentPoll >= MESSAGE_THROTTLE_THRESHOLD;

    }

    private void handleMessages() {
        messagesReadInCurrentPoll = 0;
        while (findMessage(msg)) {
            processMessage(msg);
        }

        if (receivedWMQuit) {
            receivedWMQuit = false;
            User32Ex.PostMessageW(0, User32.WM_QUIT, wmQuitExitCode, 0);
            GLFW.glfwPollEvents();
        }
    }

    private void processMessage(MSG msg) {
        switch (msg.message()) {
            case User32.WM_QUIT -> {
                receivedWMQuit = true; // GLFW processes this message in the event loop, not window procedure, so we repost the event later and call glfwPollEvents
                wmQuitExitCode = (int) msg.wParam();
            }
            // Drop messages that we process with raw input
            case User32.WM_MOUSEMOVE, User32.WM_MOUSEHWHEEL, User32.WM_MOUSEWHEEL,
                 User32.WM_LBUTTONDOWN, User32.WM_LBUTTONUP, 
                 User32.WM_MBUTTONDOWN, User32.WM_MBUTTONUP, 
                 User32.WM_RBUTTONDOWN, User32.WM_RBUTTONUP,
                 User32.WM_XBUTTONDOWN, User32.WM_XBUTTONUP -> {
                if (messageOptimizationStrategy != IxerisConfig.MessageOptimizationStrategy.NOLEGACY) {
                    if (useRawMouse() && grabbed) {
                        return;
                    }
                }
            }
            case User32.WM_KEYDOWN, User32.WM_KEYUP -> {
                if (messageOptimizationStrategy != IxerisConfig.MessageOptimizationStrategy.NOLEGACY) {
                    if (useRawKeyboard() && grabbed) {
                        return;
                    }
                }
            }
        }
        User32.TranslateMessage(msg);
        User32.DispatchMessage(msg);
    }
}
