package me.decce.ixeris.glfw.state_caching;

import me.decce.ixeris.glfw.state_caching.global.GlfwKeyNameCache;
import me.decce.ixeris.glfw.state_caching.global.GlfwMonitorCache;
import me.decce.ixeris.glfw.state_caching.global.GlfwStandardCursorCache;

public class GlfwGlobalCacheManager {
    private final GlfwMonitorCache monitorCache;
    private final GlfwKeyNameCache keyNameCache;
    private final GlfwStandardCursorCache standardCursorCache;

    public GlfwGlobalCacheManager() {
        this.keyNameCache = new GlfwKeyNameCache();
        this.monitorCache = new GlfwMonitorCache();
        this.standardCursorCache = new GlfwStandardCursorCache();
    }

    public GlfwKeyNameCache keyNames() {
        return keyNameCache;
    }

    public GlfwMonitorCache monitors() {
        return monitorCache;
    }

    public GlfwStandardCursorCache standardCursors() {
        return standardCursorCache;
    }
}
