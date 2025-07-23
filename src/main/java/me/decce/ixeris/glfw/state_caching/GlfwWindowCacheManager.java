package me.decce.ixeris.glfw.state_caching;

import me.decce.ixeris.glfw.state_caching.window.GlfwFramebufferSizeCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwInputModeCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwKeyCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwMonitorCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwMouseButtonCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwWindowSizeCache;

public class GlfwWindowCacheManager {
    public static volatile boolean useKeyCache = true;
    public static volatile boolean useInputModeCache = true;
    public static volatile boolean useMouseButtonCache = true;
    public static volatile boolean useMonitorCache = true;
    public static volatile boolean useWindowSizeCache = true;
    public static volatile boolean useFramebufferSizeCache = true;

    private final GlfwInputModeCache inputModeCache;
    private final GlfwKeyCache keyCache;
    private final GlfwMonitorCache monitorCache;
    private final GlfwMouseButtonCache mouseButtonCache;
    private final GlfwWindowSizeCache windowSizeCache;
    private final GlfwFramebufferSizeCache framebufferSizeCache;

    public GlfwWindowCacheManager(long window) {
        this.inputModeCache = new GlfwInputModeCache(window);
        this.keyCache = new GlfwKeyCache(window);
        this.mouseButtonCache = new GlfwMouseButtonCache(window);
        this.monitorCache = new GlfwMonitorCache(window);
        this.windowSizeCache = new GlfwWindowSizeCache(window);
        this.framebufferSizeCache = new GlfwFramebufferSizeCache(window);
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
}
