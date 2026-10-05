package me.decce.ixeris.core.sdl.state_caching;

import it.unimi.dsi.fastutil.ints.Int2LongArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.SDLVideo;

import java.util.ArrayList;
import java.util.List;

public class SdlStateCache {
    private static final Long2ObjectArrayMap<SdlWindowCache> windowMap = new Long2ObjectArrayMap<>(1);
    private static final Int2ObjectArrayMap<SdlDisplayCache> displayMap = new Int2ObjectArrayMap<>(1);
    private static final Int2LongArrayMap idToWindowMap = new Int2LongArrayMap(1);
    private static final SdlGlobalCache globalCache = new SdlGlobalCache();
    private static final List<SdlWindowCache> flagsTrackedWindows = new ArrayList<>(1);

    public static synchronized SdlWindowCache forWindow(long window) {
        return windowMap.computeIfAbsent(window, SdlWindowCache::new);
    }

    public static synchronized SdlDisplayCache forDisplay(int display) {
        return displayMap.computeIfAbsent(display, SdlDisplayCache::new);
    }

    public static SdlGlobalCache global() {
        return globalCache;
    }

    public static long windowFromId(int id) {
        if (id == 0) {
            return 0L; // never a valid id, don't block on it
        }
        long cached;
        synchronized (SdlStateCache.class) {
            cached = idToWindowMap.get(id);
        }
        if (cached != 0L) {
            return cached;
        }
        return MainThreadDispatcher.query(() -> {
            long window = SDLVideo.SDL_GetWindowFromID(id);
            if (window != 0L) {
                synchronized (SdlStateCache.class) {
                    idToWindowMap.put(id, window);
                }
            }
            return window;
        });
    }

    static void trackWindowFlags(SdlWindowCache cache) {
        if (cache.flagsTracked) {
            return;
        }
        cache.flags = SDLVideo.SDL_GetWindowFlags(cache.window);
        if (!isCurrent(cache)) {
            return;
        }
        if (SDLVideo.SDL_GetWindowID(cache.window) != 0) {
            flagsTrackedWindows.add(cache);
            cache.flagsTracked = true;
        }
        else {
            removeWindow(cache.window);
        }
    }

    public static void refreshWindowFlags() {
        for (int i = flagsTrackedWindows.size() - 1; i >= 0; i--) {
            var cache = flagsTrackedWindows.get(i);
            long flags = SDLVideo.SDL_GetWindowFlags(cache.window);
            cache.flags = flags;
            if (flags == 0L && SDLVideo.SDL_GetWindowID(cache.window) == 0) {
                cache.flagsTracked = false;
                flagsTrackedWindows.remove(i);
                removeWindow(cache.window);
            }
        }
    }

    public static synchronized void onQuit() {
        flagsTrackedWindows.forEach(cache -> cache.flagsTracked = false);
        flagsTrackedWindows.clear();
        windowMap.clear();
        displayMap.clear();
        idToWindowMap.clear();
    }

    public static void onWindowDestroyed(long window) {
        flagsTrackedWindows.removeIf(cache -> {
            if (isSameOrDescendant(cache.window, window)) {
                cache.flagsTracked = false;
                return true;
            }
            return false;
        });
        synchronized (SdlStateCache.class) {
            windowMap.keySet().removeIf((long cached) -> isSameOrDescendant(cached, window));
            idToWindowMap.values().removeIf((long cached) -> isSameOrDescendant(cached, window));
        }
    }

    private static synchronized boolean isCurrent(SdlWindowCache cache) {
        return windowMap.get(cache.window) == cache;
    }

    private static boolean isSameOrDescendant(long window, long ancestor) {
        while (window != 0L) {
            if (window == ancestor) {
                return true;
            }
            window = SDLVideo.SDL_GetWindowParent(window);
        }
        return false;
    }

    private static synchronized void removeWindow(long window) {
        windowMap.remove(window);
        idToWindowMap.int2LongEntrySet().removeIf(entry -> entry.getLongValue() == window);
    }
}
