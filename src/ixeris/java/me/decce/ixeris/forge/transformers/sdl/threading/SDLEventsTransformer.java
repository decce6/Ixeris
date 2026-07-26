//? if forge { 
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.sdl.SdlEventHandler;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.PointerBuffer;
import org.lwjgl.sdl.*;
import java.nio.*;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = SDLEvents.class)
public final class SDLEventsTransformer {    
    @CInline @CInject(method = "SDL_PumpEvents", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_PumpEvents(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true); MainThreadDispatcher.run(makeRunnable(SDLEvents::SDL_PumpEvents));
        }
    }

    @CInline @CInject(method = "nSDL_WaitEvent", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_WaitEvent(long event, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLEvents::nSDL_WaitEvent, event)));
        }
    }
    
    @CInline @CInject(method = "nSDL_WaitEventTimeout", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_WaitEventTimeout(long event, int timeoutMS, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLEvents::nSDL_WaitEventTimeout, event, timeoutMS)));
        }
    }
}

*///? }