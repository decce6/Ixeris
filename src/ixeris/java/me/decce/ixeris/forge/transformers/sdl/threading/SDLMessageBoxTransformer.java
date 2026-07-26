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

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = SDLMessageBox.class)
public final class SDLMessageBoxTransformer {
    @CInline @CInject(method = "nSDL_ShowMessageBox", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_ShowMessageBox(long messageboxdata, long buttonid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMessageBox::nSDL_ShowMessageBox, messageboxdata, buttonid)));
        }
    }

    @CInline @CInject(method = "nSDL_ShowSimpleMessageBox", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_ShowSimpleMessageBox(int flags, long title, long message, long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMessageBox::nSDL_ShowSimpleMessageBox, flags, title, message, window)));
        }
    }
}

*///? }