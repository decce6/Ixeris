package me.decce.ixeris.glfw.state_caching;

import me.decce.ixeris.glfw.state_caching.global.GlfwKeyNameCache;
import me.decce.ixeris.glfw.state_caching.global.GlfwMonitorCache;
import me.decce.ixeris.glfw.state_caching.global.GlfwStandardCursorCache;

import java.util.concurrent.atomic.AtomicInteger;

public class GlfwGlobalCacheManager {
    // Cache is used when >0
    public static final AtomicInteger useKeyNameCache = new AtomicInteger();
    public static final AtomicInteger useMonitorCache = new AtomicInteger();
    public static final AtomicInteger useStandardCursorCache = new AtomicInteger();

    private GlfwMonitorCache monitorCache;
    private final GlfwKeyNameCache keyNameCache;
    private final GlfwStandardCursorCache standardCursorCache;

    public GlfwGlobalCacheManager() {
        this.keyNameCache = new GlfwKeyNameCache();
        useKeyNameCache.getAndIncrement();
        this.standardCursorCache = new GlfwStandardCursorCache();
        useStandardCursorCache.getAndIncrement();
    }

    public void initializeMonitorCache() {
        this.monitorCache = new GlfwMonitorCache();
        useMonitorCache.getAndIncrement();
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
