package me.decce.ixeris.core.sdl.state_caching;

import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.SDLVideo;

public class SdlWindowCache {
    public final long window;
    public final BasicSdlLong2ObjectCache<Float> windowPixelDensity;
    public final BasicSdlLong2ObjectCache<Long> windowFullscreenMode;
    public final BasicSdlLong2ObjectCache<Integer> displayForWindow;
    volatile long flags;
    volatile boolean flagsTracked;

    public SdlWindowCache(long window) {
        this.window = window;
        this.windowPixelDensity = new BasicSdlLong2ObjectCache<>(window, SDLVideo::SDL_GetWindowPixelDensity);
        this.windowFullscreenMode = new BasicSdlLong2ObjectCache<>(window, SDLVideo::nSDL_GetWindowFullscreenMode);
        this.displayForWindow = new BasicSdlLong2ObjectCache<>(window, SDLVideo::SDL_GetDisplayForWindow);
    }

    public long windowFlags() {
        if (window == 0L) {
            return 0L; // NULL never becomes valid, don't block on it
        }
        if (!flagsTracked) {
            MainThreadDispatcher.runNow(() -> SdlStateCache.trackWindowFlags(this));
        }
        return flags;
    }
}
