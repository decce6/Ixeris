package me.decce.ixeris.core.mixins.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.SDLError;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SDLError.class, remap = false)
public final class SDLErrorMixin {
    @Inject(method = "nSDL_GetError", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetError(CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLError.nSDL_GetError()));
        }
    }
}
