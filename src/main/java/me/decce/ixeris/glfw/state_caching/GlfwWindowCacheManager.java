package me.decce.ixeris.glfw.state_caching;

import me.decce.ixeris.glfw.state_caching.window.GlfwInputModeCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwKeyCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwMouseButtonCache;

public class GlfwWindowCacheManager {
    public static volatile boolean useKeyCache = true;
    public static volatile boolean useInputModeCache = true;
    public static volatile boolean useMouseButtonCache = true;

    private final GlfwInputModeCache inputModeCache;
    private final GlfwKeyCache keyCache;
    private final GlfwMouseButtonCache mouseButtonCache;

    public GlfwWindowCacheManager(long window) {
        this.inputModeCache = new GlfwInputModeCache(window);
        this.keyCache = new GlfwKeyCache(window);
        this.mouseButtonCache = new GlfwMouseButtonCache(window);
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
}
