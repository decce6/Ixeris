//? if forge { 
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.sdl;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.sdl.SdlEventHandler;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.SDLEvents;
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
}

*///? }