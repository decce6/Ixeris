package me.decce.ixeris.glfw.state_caching;

import me.decce.ixeris.glfw.state_caching.window.GlfwInputModeCache;
import me.decce.ixeris.glfw.state_caching.window.GlfwKeyCache;

public class GlfwWindowCacheManager {
    public static volatile boolean useKeyCache = true;
    public static volatile boolean useInputModeCache = true;

    private final GlfwInputModeCache inputModeCache;
    private final GlfwKeyCache keyCache;

    public GlfwWindowCacheManager(long window) {
        this.inputModeCache = new GlfwInputModeCache(window);
        this.keyCache = new GlfwKeyCache(window);
    }

    public GlfwInputModeCache inputMode() {
        return inputModeCache;
    }

    public GlfwKeyCache keys() {
        return keyCache;
    }
}
