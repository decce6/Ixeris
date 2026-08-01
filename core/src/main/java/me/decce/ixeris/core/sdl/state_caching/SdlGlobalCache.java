package me.decce.ixeris.core.sdl.state_caching;

import org.lwjgl.sdl.SDLKeyboard;

public class SdlGlobalCache {
    public final BasicSdlVoid2LongCache keyboardFocus;

    public SdlGlobalCache() {
        this.keyboardFocus = new BasicSdlVoid2LongCache(SDLKeyboard::SDL_GetKeyboardFocus);
    }
}
