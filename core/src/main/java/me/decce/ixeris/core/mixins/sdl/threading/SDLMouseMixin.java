package me.decce.ixeris.core.mixins.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.PointerBuffer;
import org.lwjgl.sdl.*;
import java.nio.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SDLMouse.class, remap = false)
public final class SDLMouseMixin {    
    @Inject(method = "SDL_HasMouse", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_HasMouse(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_HasMouse()));
        }
    }
    
    @Inject(method = "nSDL_GetMice", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetMice(long count, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.nSDL_GetMice(count)));
        }
    }
    
    @Inject(method = "nSDL_GetMouseNameForID", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetMouseNameForID(int instance_id, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.nSDL_GetMouseNameForID(instance_id)));
        }
    }
    
    @Inject(method = "SDL_GetMouseFocus", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetMouseFocus(CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_GetMouseFocus()));
        }
    }
    
    @Inject(method = "nSDL_GetMouseState", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetMouseState(long x, long y, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.nSDL_GetMouseState(x, y)));
        }
    }
    
    @Inject(method = "nSDL_GetGlobalMouseState", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetGlobalMouseState(long x, long y, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.nSDL_GetGlobalMouseState(x, y)));
        }
    }
    
    @Inject(method = "nSDL_GetRelativeMouseState", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetRelativeMouseState(long x, long y, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.nSDL_GetRelativeMouseState(x, y)));
        }
    }
    
    @Inject(method = "SDL_WarpMouseInWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_WarpMouseInWindow(long window, float x, float y, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel(); MainThreadDispatcher.run(() -> SDLMouse.SDL_WarpMouseInWindow(window, x, y));
        }
    }
    
    @Inject(method = "SDL_WarpMouseGlobal", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_WarpMouseGlobal(float x, float y, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_WarpMouseGlobal(x, y)));
        }
    }
    
    @Inject(method = "nSDL_SetRelativeMouseTransform", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetRelativeMouseTransform(long callback, long userdata, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.nSDL_SetRelativeMouseTransform(callback, userdata)));
        }
    }
    
    @Inject(method = "SDL_SetWindowRelativeMouseMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowRelativeMouseMode(long window, boolean enabled, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            if (Ixeris.getConfig().isFullyBlockingMode()) {
                cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_SetWindowRelativeMouseMode(window, enabled)));
            }
            else {
                MainThreadDispatcher.run(() -> SDLMouse.SDL_SetWindowRelativeMouseMode(window, enabled));
                cir.setReturnValue(true);
            }
        }
    }
    
    @Inject(method = "SDL_GetWindowRelativeMouseMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowRelativeMouseMode(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_GetWindowRelativeMouseMode(window)));
        }
    }
    
    @Inject(method = "SDL_CaptureMouse", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_CaptureMouse(boolean enabled, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_CaptureMouse(enabled)));
        }
    }
    
    @Inject(method = "nSDL_CreateCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_CreateCursor(long data, long mask, int w, int h, int hot_x, int hot_y, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.nSDL_CreateCursor(data, mask, w, h, hot_x, hot_y)));
        }
    }
    
    @Inject(method = "nSDL_CreateColorCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_CreateColorCursor(long surface, int hot_x, int hot_y, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.nSDL_CreateColorCursor(surface, hot_x, hot_y)));
        }
    }
    
    @Inject(method = "nSDL_CreateAnimatedCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_CreateAnimatedCursor(long frames, int frame_count, int hot_x, int hot_y, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.nSDL_CreateAnimatedCursor(frames, frame_count, hot_x, hot_y)));
        }
    }
    
    @Inject(method = "SDL_CreateSystemCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_CreateSystemCursor(int id, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_CreateSystemCursor(id)));
        }
    }
    
    @Inject(method = "SDL_SetCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetCursor(long cursor, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_SetCursor(cursor)));
        }
    }
    
    @Inject(method = "SDL_GetCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetCursor(CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_GetCursor()));
        }
    }
    
    @Inject(method = "SDL_GetDefaultCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetDefaultCursor(CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_GetDefaultCursor()));
        }
    }
    
    @Inject(method = "SDL_DestroyCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_DestroyCursor(long cursor, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel(); MainThreadDispatcher.run(() -> SDLMouse.SDL_DestroyCursor(cursor));
        }
    }
    
    @Inject(method = "SDL_ShowCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_ShowCursor(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            if (Ixeris.getConfig().isFullyBlockingMode()) {
                cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_ShowCursor()));
            }
            else {
                MainThreadDispatcher.run(() -> SDLMouse.SDL_ShowCursor());
                cir.setReturnValue(true);
            }
        }
    }
    
    @Inject(method = "SDL_HideCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_HideCursor(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            if (Ixeris.getConfig().isFullyBlockingMode()) {
                cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_HideCursor()));
            }
            else {
                MainThreadDispatcher.run(() -> SDLMouse.SDL_HideCursor());
                cir.setReturnValue(true);
            }
        }
    }
    
    @Inject(method = "SDL_CursorVisible", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_CursorVisible(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLMouse.SDL_CursorVisible()));
        }
    }
}
