package me.decce.ixeris.core.sdl.state_caching;

import org.lwjgl.sdl.SDLVideo;

public class SdlWindowCache {
    public final long window;
    public final BasicSdlLong2ObjectCache<Float> windowPixelDensity;
    public final BasicSdlLong2ObjectCache<Long> windowFullscreenMode;
    public final BasicSdlLong2ObjectCache<Integer> displayForWindow;

    public SdlWindowCache(long window) {
        this.window = window;
        this.windowPixelDensity = new BasicSdlLong2ObjectCache<>(window, SDLVideo::SDL_GetWindowPixelDensity);
        this.windowFullscreenMode = new BasicSdlLong2ObjectCache<>(window, SDLVideo::nSDL_GetWindowFullscreenMode);
        this.displayForWindow = new BasicSdlLong2ObjectCache<>(window, SDLVideo::SDL_GetDisplayForWindow);
    }
}
