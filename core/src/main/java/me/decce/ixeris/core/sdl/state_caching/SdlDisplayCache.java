package me.decce.ixeris.core.sdl.state_caching;

import org.lwjgl.sdl.SDLVideo;

public class SdlDisplayCache {
    public final int id;
    public final BasicSdlInt2ObjectCache<Long> currentDisplayMode;

    public SdlDisplayCache(int id) {
        this.id = id;
        this.currentDisplayMode = new BasicSdlInt2ObjectCache<>(id, SDLVideo::nSDL_GetCurrentDisplayMode);
    }
}
