package me.decce.ixeris.glfw.state_caching.window;

import me.decce.ixeris.glfw.state_caching.GlfwWindowCacheManager;
import org.lwjgl.glfw.GLFW;

public class GlfwMonitorCache {
    public static final Long WINDOW_MONITOR_NOT_INITIALIZED = null;
    private final long window;
    private Long monitor;

    public GlfwMonitorCache(long window) {
        this.window = window;
        this.monitor = WINDOW_MONITOR_NOT_INITIALIZED;
    }

    public long get() {
        if (this.monitor == WINDOW_MONITOR_NOT_INITIALIZED) {
            var ret = blockingGet();
            this.monitor = ret;
            return ret;
        }
        return this.monitor;
    }

    private long blockingGet() {
        GlfwWindowCacheManager.useMonitorCache = false;
        var ret = GLFW.glfwGetWindowMonitor(window);
        GlfwWindowCacheManager.useMonitorCache = true;
        return ret;
    }

    public void set(Long monitor) {
        this.monitor = monitor;
    }

    // Currently not called.
    // Reference: https://github.com/glfw/glfw/issues/2137 (quote from author: GLFW currently assumes only it can move a fullscreen window)
    public void invalidateCache() {
        this.monitor = WINDOW_MONITOR_NOT_INITIALIZED;
    }
}
