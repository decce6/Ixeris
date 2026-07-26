package me.decce.ixeris.core.mixins.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SDLInit.class, remap = false)
public final class SDLInitMixin {
    @Inject(method = "SDL_Init", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_Init(int flags, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLInit.SDL_Init(flags)));
        }
    }

    @Inject(method = "SDL_InitSubSystem", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_InitSubSystem(int flags, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLInit.SDL_InitSubSystem(flags)));
        }
    }

    @Inject(method = "SDL_Quit", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_Quit(CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel(); MainThreadDispatcher.run(() -> SDLInit.SDL_Quit());
        }
    }
}
