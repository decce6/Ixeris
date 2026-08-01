package me.decce.ixeris.core.mixins.sdl;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.sdl.SdlEventHandler;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.SDLEvents;
import org.lwjgl.sdl.SDL_Event;
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

    @Inject(method = "nSDL_PeepEvents", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_PeepEvent(long events, int numevents, int action, int minType, int maxType, CallbackInfoReturnable<Integer> cir) {
        if (action == SDLEvents.SDL_GETEVENT && Ixeris.getEventHandler() instanceof SdlEventHandler sdlEventHandler) {
            if (minType != SDLEvents.SDL_EVENT_FIRST || maxType != SDLEvents.SDL_EVENT_LAST) {
                throw new RuntimeException("Unsupported arguments for SDL_PeepEvents: minType=" + minType + ", maxType=" + maxType);
            }
            int i;
            for (i = 0; i < numevents; i++) {
                if (!sdlEventHandler.readEvents(events + (long)(SDL_Event.SIZEOF) * i)) {
                    break;
                }
            }
            cir.setReturnValue(i);
        }
    }
}
