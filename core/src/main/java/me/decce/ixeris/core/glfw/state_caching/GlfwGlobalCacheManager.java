package me.decce.ixeris.core.glfw.state_caching;

import me.decce.ixeris.core.glfw.state_caching.global.GlfwKeyNameCache;
import me.decce.ixeris.core.glfw.state_caching.global.GlfwMonitorCache;
import me.decce.ixeris.core.glfw.state_caching.global.GlfwCursorCache;

public class GlfwGlobalCacheManager {
    private final GlfwMonitorCache monitorCache;
    private final GlfwKeyNameCache keyNameCache;
    private final GlfwCursorCache cursorCache;

    public GlfwGlobalCacheManager() {
        this.keyNameCache = new GlfwKeyNameCache();
        this.monitorCache = new GlfwMonitorCache();
        this.cursorCache = new GlfwCursorCache();
    }

    public GlfwKeyNameCache keyNames() {
        return keyNameCache;
    }

    public GlfwMonitorCache monitors() {
        return monitorCache;
    }

    public GlfwCursorCache cursor() {
        return cursorCache;
    }
}
