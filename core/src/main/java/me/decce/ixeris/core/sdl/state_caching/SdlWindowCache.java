package me.decce.ixeris.core.sdl.state_caching;

import org.lwjgl.sdl.SDLVideo;

public class SdlWindowCache {
    public final long window;
    public final BasicSdlLongCache<Float> windowPixelDensity;
    public final BasicSdlLongCache<Long> windowFullscreenMode;
    public final BasicSdlLongCache<Integer> displayForWindow;

    public SdlWindowCache(long window) {
        this.window = window;
        this.windowPixelDensity = new BasicSdlLongCache<>(window, SDLVideo::SDL_GetWindowPixelDensity);
        this.windowFullscreenMode = new BasicSdlLongCache<>(window, SDLVideo::nSDL_GetWindowFullscreenMode);
        this.displayForWindow = new BasicSdlLongCache<>(window, SDLVideo::SDL_GetDisplayForWindow);
    }
}
