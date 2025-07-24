package me.decce.ixeris.glfw.state_caching;

import me.decce.ixeris.glfw.state_caching.window.GlfwFramebufferSizeCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwInputModeCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwKeyCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwMonitorCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwMouseButtonCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwWindowContentScaleCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwWindowSizeCache;

public class GlfwWindowCacheManager {
    private final GlfwInputModeCache inputModeCache;
    private final GlfwMonitorCache monitorCache;
    private final GlfwKeyCache keyCache;
    private final GlfwMouseButtonCache mouseButtonCache;
    private final GlfwWindowSizeCache windowSizeCache;
    private final GlfwFramebufferSizeCache framebufferSizeCache;
    private final GlfwWindowContentScaleCache windowContentScaleCache;
    private final long window;

    public GlfwWindowCacheManager(long window) {
        this.window = window;
        this.inputModeCache = new GlfwInputModeCache(window);
        this.monitorCache = new GlfwMonitorCache(window);
        this.keyCache = new GlfwKeyCache(window);
        this.mouseButtonCache = new GlfwMouseButtonCache(window);
        this.windowSizeCache = new GlfwWindowSizeCache(window);
        this.framebufferSizeCache = new GlfwFramebufferSizeCache(window);
        this.windowContentScaleCache = new GlfwWindowContentScaleCache(window);
    }

    public GlfwInputModeCache inputMode() {
        return inputModeCache;
    }

    public GlfwKeyCache keys() {
        return keyCache;
    }

    public GlfwMouseButtonCache mouseButtons() {
        return mouseButtonCache;
    }

    public GlfwMonitorCache monitor() {
        return monitorCache;
    }

    public GlfwWindowSizeCache windowSize() {
        return windowSizeCache;
    }

    public GlfwFramebufferSizeCache framebufferSize() {
        return framebufferSizeCache;
    }

    public GlfwWindowContentScaleCache contentScale() {
        return windowContentScaleCache;
    }
}
