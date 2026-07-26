//? if forge { 
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.sdl;

import me.decce.ixeris.core.Ixeris;
import org.lwjgl.sdl.SDLInit;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = SDLInit.class)
public final class SDLInitTransformer {
    @CInline @CInject(method = "SDL_Init", target = @CTarget("TAIL"))
    private static void ixeris$SDL_Init(InjectionCallback cir) {
        Ixeris.sdlInitialized = true;
    }

    @CInline @CInject(method = "SDL_Quit", target = @CTarget("TAIL"))
    private static void ixeris$SDL_Quit(InjectionCallback ci) {
        Ixeris.sdlInitialized = false;
    }
}

*///? }