/*
* A portion of the input processing code is based on that from [GLFW](https://github.com/glfw/glfw/blob/master/src/win32_window.c); necessary adaptations were made for use in Java.
* */

package me.decce.ixeris.core.input;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.CommonCallbacks;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import me.decce.ixeris.core.win32.RAWINPUT;
import me.decce.ixeris.core.win32.RAWINPUTDEVICE;
import me.decce.ixeris.core.win32.RAWINPUTHEADER;
import me.decce.ixeris.core.win32.User32;
import me.decce.ixeris.core.win32.Win32Exception;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.windows.POINT;
import org.lwjgl.system.windows.RECT;
import org.lwjgl.system.windows.WinBase;

import static org.lwjgl.glfw.GLFW.GLFW_MOD_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_CAPS_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_NUM_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_4;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_5;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.windows.User32.ClientToScreen;
import static org.lwjgl.system.windows.User32.GetSystemMetrics;
import static org.lwjgl.system.windows.User32.SM_CXSCREEN;
import static org.lwjgl.system.windows.User32.SM_CXVIRTUALSCREEN;
import static org.lwjgl.system.windows.User32.SM_CYSCREEN;
import static org.lwjgl.system.windows.User32.SM_CYVIRTUALSCREEN;
import static org.lwjgl.system.windows.User32.SM_XVIRTUALSCREEN;
import static org.lwjgl.system.windows.User32.SM_YVIRTUALSCREEN;
import static org.lwjgl.system.windows.User32.SetCursorPos;
import static org.lwjgl.system.windows.User32.VK_CAPITAL;
import static org.lwjgl.system.windows.User32.VK_CONTROL;
import static org.lwjgl.system.windows.User32.VK_LWIN;
import static org.lwjgl.system.windows.User32.VK_MENU;
import static org.lwjgl.system.windows.User32.VK_NUMLOCK;
import static org.lwjgl.system.windows.User32.VK_RWIN;
import static org.lwjgl.system.windows.User32.VK_SHIFT;
import static org.lwjgl.system.windows.User32.WHEEL_DELTA;

public class RawInputHandlerWin32 implements RawInputHandler {
    private final long glfwWindow;
    private final long hWnd;
    private RAWINPUTDEVICE rid;
    private RAWINPUT.Buffer rawInput;
    private int size;
    private int lastCursorPosX;
    private int lastCursorPosY;
    private int grabbedCursorPosX;
    private int grabbedCursorPosY;

    public RawInputHandlerWin32(long glfwWindow) {
        this.glfwWindow = glfwWindow;
        this.hWnd = GLFWNativeWin32.glfwGetWin32Window(glfwWindow);
        if (this.hWnd <= 0) {
            throw new IllegalArgumentException("Invalid HWND %d for window %d".formatted(hWnd, glfwWindow));
        }
        this.size = Ixeris.getConfig().getMinRawInputBufferSize();
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

    /**
     * Sets {@link GLFW#GLFW_RAW_MOUSE_MOTION} to {GLFW#GLFW_FALSE} to make GLFW stop processing WM_INPUT message
     */
    private void setupGlfw() {
         GLFW.glfwSetInputMode(glfwWindow, GLFW.GLFW_RAW_MOUSE_MOTION, GLFW.GLFW_FALSE);
    }

    private void unregisterRawInputDevice() {
        try (var stack = MemoryStack.stackPush()) {
            var remove = RAWINPUTDEVICE.malloc(stack)
                    .usUsagePage((short) 0x01)
                    .usUsage((short) 0x02)
                    .dwFlags(User32.RIDEV_REMOVE)
                    .hwndTarget(0);
            if (!User32.RegisterRawInputDevices(remove, 1, rid.sizeof())) {
                throw new Win32Exception("Failed to disable buffered raw input!", WinBase.GetLastError());
            }
        }
    }

    private RAWINPUTDEVICE getRawInputDevice() {
        if (this.rid == null) {
            this.rid = RAWINPUTDEVICE.create()
                    .usUsagePage((short) 0x01)
                    .usUsage((short) 0x02)
                    .dwFlags(User32.RIDEV_NOLEGACY)
                    .hwndTarget(hWnd);
        }
        return this.rid;
    }

    @Override
    public void enable() {
        if (Ixeris.input().window() != 0 && Ixeris.input().window() != this.glfwWindow) {
            Ixeris.LOGGER.warn("Raw input has already been registered on another window");
            return;
        }

        setupGlfw();

        var rid = this.getRawInputDevice();

        if (!User32.RegisterRawInputDevices(rid, 1, rid.sizeof())) {
            throw new Win32Exception("Failed to enable buffered raw input!", WinBase.GetLastError());
        }

        createBuffer(size);
    }

    @Override
    public void disable() {
        if (Ixeris.input().window() == 0) {
            return;
        }
        if (Ixeris.input().window() != this.glfwWindow) {
            Ixeris.LOGGER.error("Cannot unregistered raw input from a window that has no raw input registered");
            return;
        }
        unregisterRawInputDevice();
        var previousRawMouseMotion = GlfwCacheManager.getWindowCache(glfwWindow).inputMode().get(GLFW.GLFW_RAW_MOUSE_MOTION);
        GLFW.glfwSetInputMode(glfwWindow, GLFW.GLFW_RAW_MOUSE_MOTION, previousRawMouseMotion);
    }

    @Override
    public void processInput() {
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
                    if (data.header().dwType() != User32.RIM_TYPEMOUSE) {
                        continue;
                    }
                    int dx = 0, dy = 0;
                    var mouse = data.mouse();
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

                        POINT pos = POINT.malloc(stack).x(x).y(y);
                        User32.ScreenToClient(hWnd, pos);
                        dx = pos.x() - lastCursorPosX;
                        dy = pos.y() - lastCursorPosY;
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
            }

            if (totalCount > size) {
                this.createBuffer(Math.min(totalCount, Ixeris.getConfig().getMaxRawInputBufferSize()));
            }
        }

        centerCursor();
    }

    private void centerCursor() {
        try (var stack = stackPush()) {
            var rect = RECT.malloc(stack);
            User32.GetClientRect(hWnd, rect);
            var width = rect.right();
            var height = rect.bottom();
            if (lastCursorPosX != width / 2 || lastCursorPosY != height / 2) {
                lastCursorPosX = width / 2;
                lastCursorPosY = height / 2;
                var point = POINT.malloc(stack).x(lastCursorPosX).y(lastCursorPosY);
                ClientToScreen(hWnd, point);
                SetCursorPos(point.x(), point.y());
            }
        }
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
        CommonCallbacks.cursorPosCallback.invoke(glfwWindow,  x, y);
    }

    private void inputMouseButton(int button, int action, int mods) {
        //TODO: sticky mouse buttons support
        CommonCallbacks.mouseButtonCallback.invoke(glfwWindow, button, action, mods);
    }

    private void inputScroll(double xoffset, double yoffset) {
        CommonCallbacks.scrollCallback.invoke(glfwWindow, xoffset, yoffset);
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
}
