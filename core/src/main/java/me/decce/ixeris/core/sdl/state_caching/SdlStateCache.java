package me.decce.ixeris.core.sdl.state_caching;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;

public class SdlStateCache {
    private static final Long2ObjectArrayMap<SdlWindowCache> windowMap = new Long2ObjectArrayMap<>(1);
    private static final Int2ObjectArrayMap<SdlDisplayCache> displayMap = new Int2ObjectArrayMap<>(1);

    public static SdlWindowCache forWindow(long window) {
        return windowMap.computeIfAbsent(window, SdlWindowCache::new);
    }

    public static SdlDisplayCache forDisplay(int display) {
        return displayMap.computeIfAbsent(display, SdlDisplayCache::new);
    }
}
