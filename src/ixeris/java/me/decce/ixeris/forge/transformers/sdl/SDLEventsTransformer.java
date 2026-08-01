//? if forge { 
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.sdl;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.sdl.SdlEventHandler;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.SDLEvents;
import org.lwjgl.sdl.SDL_Event;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = SDLEvents.class)
public class SDLEventsTransformer {
    @CInline @CInject(method = "nSDL_PollEvent", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_PollEvent(long event, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            if (Ixeris.getEventHandler() instanceof SdlEventHandler sdlEventHandler) {
                cir.setReturnValue(sdlEventHandler.readEvents(event));
            }
            else {
                throw new IllegalStateException();
            }
        }
    }

    @CInline @CInject(method = "nSDL_PeepEvents", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_PeepEvent(long events, int numevents, int action, int minType, int maxType, InjectionCallback cir) {
        if (action == SDLEvents.SDL_GETEVENT && Ixeris.getEventHandler() instanceof SdlEventHandler sdlEventHandler) {
            if (minType != SDLEvents.SDL_EVENT_FIRST || maxType != SDLEvents.SDL_EVENT_LAST) {
                throw new RuntimeException("Unsupported arguments for SDL_PeepEvents: minType=" + minType + ", maxType=" + maxType);
            }
            int i;
            for (i = 0; i < numevents; i++) {
                if (!sdlEventHandler.readEvents(events + (long)(SDL_Event.SIZEOF) * i)) {
                    break;
                }
            }
            cir.setReturnValue(i);
        }
    }
}

*///? }