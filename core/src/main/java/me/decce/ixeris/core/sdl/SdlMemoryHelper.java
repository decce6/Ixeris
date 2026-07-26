package me.decce.ixeris.core.sdl;

import org.lwjgl.sdl.SDL_Rect;
import org.lwjgl.system.MemoryUtil;

public class SdlMemoryHelper {
    public static long copySDL_Rect(long original) {
        if (original == 0L) {
            return 0L;
        }
        var copied = MemoryUtil.nmemAlloc(SDL_Rect.SIZEOF);
        MemoryUtil.memCopy(original, copied, SDL_Rect.SIZEOF);
        return copied;
    }
}
