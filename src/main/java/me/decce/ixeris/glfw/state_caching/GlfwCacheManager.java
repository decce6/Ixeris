package me.decce.ixeris.glfw.state_caching;

import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMaps;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;

public class GlfwCacheManager {
    private static final GlfwGlobalCacheManager globalCache = new GlfwGlobalCacheManager();
    private static final Long2ReferenceMap<GlfwWindowCacheManager> windowCaches = Long2ReferenceMaps.synchronize(new Long2ReferenceOpenHashMap<>(1));

    public static GlfwGlobalCacheManager getGlobalCache() {
        return globalCache;
    }

    public static GlfwWindowCacheManager getWindowCache(long window) {
        return windowCaches.computeIfAbsent(window, GlfwWindowCacheManager::new);
    }
}
