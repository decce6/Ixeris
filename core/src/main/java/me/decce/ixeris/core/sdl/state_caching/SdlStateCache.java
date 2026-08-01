package me.decce.ixeris.core.sdl.state_caching;

import it.unimi.dsi.fastutil.ints.Int2LongArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.SDLVideo;

public class SdlStateCache {
    private static final Long2ObjectArrayMap<SdlWindowCache> windowMap = new Long2ObjectArrayMap<>(1);
    private static final Int2ObjectArrayMap<SdlDisplayCache> displayMap = new Int2ObjectArrayMap<>(1);
    private static final Int2LongArrayMap idToWindowMap = new Int2LongArrayMap(1);
    private static final SdlGlobalCache globalCache = new SdlGlobalCache();

    public static SdlWindowCache forWindow(long window) {
        return windowMap.computeIfAbsent(window, SdlWindowCache::new);
    }

    public static SdlDisplayCache forDisplay(int display) {
        return displayMap.computeIfAbsent(display, SdlDisplayCache::new);
    }

    public static SdlGlobalCache global() {
        return globalCache;
    }

    public static long windowFromId(int id) {
        return idToWindowMap.computeIfAbsent(id, i -> MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowFromID(i)));
    }
}
