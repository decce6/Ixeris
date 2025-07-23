package me.decce.ixeris.glfw.state_caching.window;

import org.lwjgl.glfw.GLFW;

public class GlfwMonitorCache extends GlfwWindowCache {
    public static final Long WINDOW_MONITOR_NOT_INITIALIZED = null;
    private Long monitor;

    public GlfwMonitorCache(long window) {
        super(window);
        this.monitor = WINDOW_MONITOR_NOT_INITIALIZED;
        this.enableCache();
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
        this.disableCache();
        var ret = GLFW.glfwGetWindowMonitor(window);
        this.enableCache();
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
