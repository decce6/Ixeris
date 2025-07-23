package me.decce.ixeris.glfw.state_caching;

import me.decce.ixeris.glfw.state_caching.window.GlfwFramebufferSizeCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwInputModeCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwKeyCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwMonitorCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwMouseButtonCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwWindowContentScaleCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwWindowSizeCache;

import java.util.concurrent.atomic.AtomicInteger;

public class GlfwWindowCacheManager {
    // Cache is used when >0
    public static final AtomicInteger useKeyCache = new AtomicInteger();
    public static final AtomicInteger useInputModeCache = new AtomicInteger();
    public static final AtomicInteger useMouseButtonCache = new AtomicInteger();
    public static final AtomicInteger useMonitorCache = new AtomicInteger();
    public static final AtomicInteger useWindowSizeCache = new AtomicInteger();
    public static final AtomicInteger useFramebufferSizeCache = new AtomicInteger();
    public static final AtomicInteger useWindowContentScaleCache = new AtomicInteger();

    private final GlfwInputModeCache inputModeCache;
    private final GlfwMonitorCache monitorCache;
    private GlfwKeyCache keyCache;
    private GlfwMouseButtonCache mouseButtonCache;
    private GlfwWindowSizeCache windowSizeCache;
    private GlfwFramebufferSizeCache framebufferSizeCache;
    private GlfwWindowContentScaleCache windowContentScaleCache;
    private final long window;

    public GlfwWindowCacheManager(long window) {
        this.window = window;
        this.inputModeCache = new GlfwInputModeCache(window);
        this.monitorCache = new GlfwMonitorCache(window);
        useInputModeCache.getAndIncrement();
        useMonitorCache.getAndIncrement();
    }

    public void initializeKeyCache() {
        this.keyCache = new GlfwKeyCache(window);
        useKeyCache.getAndIncrement();
    }

    public void initializeMouseButtonCache() {
        this.mouseButtonCache = new GlfwMouseButtonCache(window);
        useMouseButtonCache.getAndIncrement();
    }

    public void initializeWindowCaches() {
        this.windowSizeCache = new GlfwWindowSizeCache(window);
        this.framebufferSizeCache = new GlfwFramebufferSizeCache(window);
        this.windowContentScaleCache = new GlfwWindowContentScaleCache(window);
        useWindowSizeCache.getAndIncrement();
        useMouseButtonCache.getAndIncrement();
        useWindowContentScaleCache.getAndIncrement();
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
