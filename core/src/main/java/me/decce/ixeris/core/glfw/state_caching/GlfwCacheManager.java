package me.decce.ixeris.core.glfw.state_caching;

import it.unimi.dsi.fastutil.longs.Long2ReferenceArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMaps;

public class GlfwCacheManager {
    private static final GlfwGlobalCacheManager globalCache = new GlfwGlobalCacheManager();
    private static final Long2ReferenceMap<GlfwWindowCacheManager> windowCaches = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    public static GlfwGlobalCacheManager getGlobalCache() {
        return globalCache;
    }

    public static GlfwWindowCacheManager getWindowCache(long window) {
        return windowCaches.computeIfAbsent(window, GlfwWindowCacheManager::new);
    }
}
