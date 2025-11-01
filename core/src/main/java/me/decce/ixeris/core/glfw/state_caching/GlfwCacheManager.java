package me.decce.ixeris.core.glfw.state_caching;

import it.unimi.dsi.fastutil.longs.Long2ReferenceArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMaps;
import me.decce.ixeris.core.Ixeris;

public class GlfwCacheManager {
    private static final GlfwGlobalCacheManager globalCache = new GlfwGlobalCacheManager();
    private static final Long2ReferenceMap<GlfwWindowCacheManager> windowCaches = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    public static GlfwGlobalCacheManager getGlobalCache() {
        return globalCache;
    }

    public static boolean hasWindowCache(long window) {
        return window == Ixeris.accessor.getMinecraftWindow() || windowCaches.containsKey(window);
    }

    public static GlfwWindowCacheManager getWindowCache(long window) {
        if (window == Ixeris.accessor.getMinecraftWindow()) {
            return windowCaches.computeIfAbsent(window, GlfwWindowCacheManager::new);
        }
        return windowCaches.get(window);
    }
}
