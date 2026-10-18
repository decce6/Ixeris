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

    public static long createGlContextWithoutMakingCurrent(long window) {
        var context = MainThreadDispatcher.query(() -> {
            var originalWindow = SDLVideo.SDL_GL_GetCurrentWindow();
            var originalContext = SDLVideo.SDL_GL_GetCurrentContext();
            var newContext = SDLVideo.SDL_GL_CreateContext(window); // implicitly makes it current on thread
            SDLVideo.SDL_GL_MakeCurrent(originalWindow, originalContext); // restore original window & context
            return newContext;
        });
        SDLVideo.SDL_GL_MakeCurrent(window, context);
        return context;
    }
}
