package me.decce.ixeris.glfw.state_caching;

import me.decce.ixeris.glfw.state_caching.global.GlfwKeyNameCache;
import me.decce.ixeris.glfw.state_caching.global.GlfwMonitorCache;
import me.decce.ixeris.glfw.state_caching.global.GlfwStandardCursorCache;

public class GlfwGlobalCacheManager {
    private final GlfwKeyNameCache keyNameCache = new GlfwKeyNameCache();
    private final GlfwMonitorCache monitorCache = new GlfwMonitorCache();
    private final GlfwStandardCursorCache standardCursorCache = new GlfwStandardCursorCache();

    public static volatile boolean useKeyNameCache = true;
    public static volatile boolean useMonitorCache = true;
    public static volatile boolean useStandardCursorCache = true;

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
