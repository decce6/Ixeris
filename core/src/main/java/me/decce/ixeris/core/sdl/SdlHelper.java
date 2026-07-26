package me.decce.ixeris.core.sdl;

import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.util.MemoryHelper;
import org.lwjgl.sdl.SDLVideo;

public class SdlHelper {
    public static void setWindowTitleLater(long window, long title) {
        var copiedTitle = MemoryHelper.copyString(title);
        MainThreadDispatcher.run(() -> {
            SDLVideo.nSDL_SetWindowTitle(window, copiedTitle);
            MemoryHelper.free(copiedTitle);
        });
    }
}
