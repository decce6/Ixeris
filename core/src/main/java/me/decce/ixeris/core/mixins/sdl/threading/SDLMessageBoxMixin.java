package me.decce.ixeris.core.mixins.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SDLMessageBox.class, remap = false)
public final class SDLMessageBoxMixin {
    @Inject(method = "nSDL_ShowMessageBox", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_ShowMessageBox(long messageboxdata, long buttonid, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMessageBox.nSDL_ShowMessageBox(messageboxdata, buttonid)));
        }
    }

    @Inject(method = "nSDL_ShowSimpleMessageBox", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_ShowSimpleMessageBox(int flags, long title, long message, long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMessageBox.nSDL_ShowSimpleMessageBox(flags, title, message, window)));
        }
    }
}
