package me.decce.ixeris.glfw.state_caching;

import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;

public class GlfwCacheManager {
    private static final Long2ReferenceMap<GlfwKeyCache> keyCaches = new Long2ReferenceOpenHashMap<>(1);
    private static final Long2ReferenceMap<GlfwInputModeCache> inputModeCaches = new Long2ReferenceOpenHashMap<>(1);
    private static final GlfwKeyNameCache keyNameCache = new GlfwKeyNameCache();
    private static final Object lock = new Object();

    public static volatile boolean useKeyCache = true;
    public static volatile boolean useKeyNameCache = true;
    public static volatile boolean useInputModeCache = true;

    public static GlfwKeyCache initializeKeyCache(long window) {
        var cache = new GlfwKeyCache(window);
        keyCaches.put(window, cache);
        return cache;
    }

    public static GlfwKeyCache getKeyCache(long window) {
        var cache = keyCaches.get(window);
        if (cache == null) {
            synchronized (lock) {
                cache = keyCaches.get(window);
                if (cache == null) {
                    cache = initializeKeyCache(window);
                }
            }
        }
        return cache;
    }

    public static GlfwInputModeCache initializeInputModeCache(long window) {
        var cache = new GlfwInputModeCache(window);
        inputModeCaches.put(window, cache);
        return cache;
    }

    public static GlfwInputModeCache getInputModeCache(long window) {
        var cache = inputModeCaches.get(window);
        if (cache == null) {
            synchronized (lock) {
                cache = inputModeCaches.get(window);
                if (cache == null) {
                    cache = initializeInputModeCache(window);
                }
            }
        }
        return cache;
    }

    public static GlfwKeyNameCache getKeyNameCache() {
        return keyNameCache;
    }
}
