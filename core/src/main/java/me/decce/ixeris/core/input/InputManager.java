package me.decce.ixeris.core.input;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import org.lwjgl.glfw.GLFW;

public class InputManager {
    // The current GLFW window which has cursor input mode set to GLFW_CURSOR_DISABLED
    private long currentInputWindow;

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
            GlfwCacheManager.getWindowCache(currentInputWindow).rawInput().processInput();
        }
        GLFW.glfwPollEvents();

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
