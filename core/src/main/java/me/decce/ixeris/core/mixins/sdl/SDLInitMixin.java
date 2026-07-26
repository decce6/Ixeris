package me.decce.ixeris.core.mixins.sdl;

import me.decce.ixeris.core.Ixeris;
import org.lwjgl.sdl.SDLInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SDLInit.class, remap = false)
public final class SDLInitMixin {
    @Inject(method = "SDL_Init", at = @At("TAIL"))
    private static void ixeris$SDL_Init(CallbackInfoReturnable<Boolean> cir) {
        Ixeris.sdlInitialized = true;
    }

    @Inject(method = "SDL_Quit", at = @At("TAIL"))
    private static void ixeris$SDL_Quit(CallbackInfo ci) {
        Ixeris.sdlInitialized = false;
    }
}
