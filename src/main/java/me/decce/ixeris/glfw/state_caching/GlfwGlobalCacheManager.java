package me.decce.ixeris.glfw.state_caching;

import me.decce.ixeris.glfw.state_caching.global.GlfwKeyNameCache;

public class GlfwGlobalCacheManager {
    private final GlfwKeyNameCache keyNameCache = new GlfwKeyNameCache();

    public static volatile boolean useKeyNameCache = true;

    public GlfwKeyNameCache keyNames() {
        return keyNameCache;
    }
}
