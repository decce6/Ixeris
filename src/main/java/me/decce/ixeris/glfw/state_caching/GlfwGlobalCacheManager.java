package me.decce.ixeris.glfw.state_caching;

import me.decce.ixeris.glfw.state_caching.global.GlfwKeyNameCache;
import me.decce.ixeris.glfw.state_caching.global.GlfwMonitorCache;

public class GlfwGlobalCacheManager {
    private final GlfwKeyNameCache keyNameCache = new GlfwKeyNameCache();
    private final GlfwMonitorCache monitorCache = new GlfwMonitorCache();

    public static volatile boolean useKeyNameCache = true;
    public static volatile boolean useMonitorCache = true;

    public GlfwKeyNameCache keyNames() {
        return keyNameCache;
    }

    public GlfwMonitorCache monitors() {
        return monitorCache;
    }
}
