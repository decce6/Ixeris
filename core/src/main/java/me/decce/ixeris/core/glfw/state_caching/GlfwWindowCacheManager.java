package me.decce.ixeris.core.glfw.state_caching;

import me.decce.ixeris.core.glfw.state_caching.window.*;

public class GlfwWindowCacheManager {
    private final GlfwCursorPosCache cursorPosCache;
    private final GlfwInputModeCache inputModeCache;
    private final GlfwMonitorCache monitorCache;
    private final GlfwKeyCache keyCache;
    private final GlfwMouseButtonCache mouseButtonCache;
    private final GlfwWindowSizeCache windowSizeCache;
    private final GlfwFramebufferSizeCache framebufferSizeCache;
    private final GlfwWindowContentScaleCache windowContentScaleCache;
    private final GlfwWindowAttribCache windowAttribCache;
    private final long window;

    public GlfwWindowCacheManager(long window) {
        this.window = window;
        this.cursorPosCache = new GlfwCursorPosCache(window);
        this.inputModeCache = new GlfwInputModeCache(window);
        this.monitorCache = new GlfwMonitorCache(window);
        this.keyCache = new GlfwKeyCache(window);
        this.mouseButtonCache = new GlfwMouseButtonCache(window);
        this.windowSizeCache = new GlfwWindowSizeCache(window);
        this.framebufferSizeCache = new GlfwFramebufferSizeCache(window);
        this.windowContentScaleCache = new GlfwWindowContentScaleCache(window);
        this.windowAttribCache = new GlfwWindowAttribCache(window);
    }

    public GlfwCursorPosCache cursorPos() {
        return cursorPosCache;
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

    public GlfwWindowAttribCache attrib() {
        return windowAttribCache;
    }
}
