package me.decce.ixeris.core.input;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.windows.MSG;

import static org.lwjgl.system.windows.User32.DispatchMessage;
import static org.lwjgl.system.windows.User32.PM_REMOVE;
import static org.lwjgl.system.windows.User32.PeekMessage;
import static org.lwjgl.system.windows.User32.PostMessage;
import static org.lwjgl.system.windows.User32.TranslateMessage;
import static org.lwjgl.system.windows.User32.WM_INPUT;
import static org.lwjgl.system.windows.User32.WM_QUIT;

public class InputManager {
    // The current GLFW window which has cursor input mode set to GLFW_CURSOR_DISABLED
    private long currentInputWindow;
    private boolean receivedWMQuit;
    private int wmQuitExitCode;

    public long window() {
        return currentInputWindow;
    }

    public boolean cursorGrabbed() {
        return currentInputWindow != 0L;
    }

    private boolean buffered() {
        return Ixeris.getConfig().isBufferedRawInput();
    }

    public void pollEvents() {
        if (buffered() && cursorGrabbed() && GlfwCacheManager.hasWindowCache(currentInputWindow)) {
            _pollEvents();
            GlfwCacheManager.getWindowCache(currentInputWindow).rawInput().processInput();
        }
        else {
            GLFW.glfwPollEvents();
        }
    }

    private void _pollEvents() {
        MSG msg = MSG.malloc();

        // Process messages *before* WM_INPUT
        while (PeekMessage(msg, 0, 0, WM_INPUT - 1, PM_REMOVE)) {
            processMessage(msg);
        }

        // Process messages *after* WM_INPUT
        while(PeekMessage(msg, 0, WM_INPUT + 1, -1, PM_REMOVE)) {
            processMessage(msg);
        }

        if (receivedWMQuit) {
            receivedWMQuit = false;
            PostMessage(null, 0, WM_QUIT, wmQuitExitCode, 0);
            GLFW.glfwPollEvents();
        }

        msg.free();
    }

    private void processMessage(MSG msg) {
        if (msg.message() == WM_QUIT) {
            receivedWMQuit = true; // GLFW processes this message in the event loop, not window procedure, so we repost the event later and call glfwPollEvents
            wmQuitExitCode = (int) msg.wParam();
        }
        TranslateMessage(msg);
        DispatchMessage(msg);
    }

    public void grab(long window) {
        if (!buffered() || window == 0L || currentInputWindow == window) {
            return;
        }
        currentInputWindow = window;
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window);
            cache.rawInput().enable();
        }
    }

    public void release(long window) {
        if (!buffered() || window == 0L || currentInputWindow != window) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window);
            cache.rawInput().disable();
        }
        currentInputWindow = 0L;
    }
}
