//? if forge { 
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.*;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = SDLInit.class)
public final class SDLInitTransformer {
    @CInline @CInject(method = "SDL_Init", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_Init(int flags, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLInit::SDL_Init, flags)));
        }
    }

    @CInline @CInject(method = "SDL_InitSubSystem", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_InitSubSystem(int flags, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLInit::SDL_InitSubSystem, flags)));
        }
    }

    @CInline @CInject(method = "SDL_Quit", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_Quit(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true); MainThreadDispatcher.run(makeRunnable(SDLInit::SDL_Quit));
        }
    }
}

*///? }