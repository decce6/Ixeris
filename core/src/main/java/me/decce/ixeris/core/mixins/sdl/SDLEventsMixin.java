package me.decce.ixeris.core.mixins.sdl;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.sdl.SdlEventHandler;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.SDLEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SDLEvents.class, remap = false)
public class SDLEventsMixin {
    @Inject(method = "nSDL_PollEvent", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_PollEvent(long event, CallbackInfoReturnable<Boolean> cir) {
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
