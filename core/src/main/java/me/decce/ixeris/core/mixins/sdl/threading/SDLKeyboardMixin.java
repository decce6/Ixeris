package me.decce.ixeris.core.mixins.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.sdl.SdlMemoryHelper;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.*;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SDLKeyboard.class, remap = false)
public final class SDLKeyboardMixin {    
    @Inject(method = "SDL_HasKeyboard", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_HasKeyboard(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.SDL_HasKeyboard()));
        }
    }
    
    @Inject(method = "nSDL_GetKeyboards", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetKeyboards(long count, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.nSDL_GetKeyboards(count)));
        }
    }
    
    @Inject(method = "nSDL_GetKeyboardNameForID", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetKeyboardNameForID(int instance_id, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.nSDL_GetKeyboardNameForID(instance_id)));
        }
    }
    
    @Inject(method = "SDL_GetKeyboardFocus", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetKeyboardFocus(CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.SDL_GetKeyboardFocus()));
        }
    }
    
    @Inject(method = "SDL_ResetKeyboard", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_ResetKeyboard(CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel(); MainThreadDispatcher.run(() -> SDLKeyboard.SDL_ResetKeyboard());
        }
    }
    
    @Inject(method = "SDL_StartTextInput", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_StartTextInput(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.SDL_StartTextInput(window)));
        }
    }
    
    @Inject(method = "SDL_StartTextInputWithProperties", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_StartTextInputWithProperties(long window, int props, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.SDL_StartTextInputWithProperties(window, props)));
        }
    }
    
    @Inject(method = "SDL_TextInputActive", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_TextInputActive(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.SDL_TextInputActive(window)));
        }
    }
    
    @Inject(method = "SDL_StopTextInput", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_StopTextInput(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.SDL_StopTextInput(window)));
        }
    }
    
    @Inject(method = "SDL_ClearComposition", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_ClearComposition(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.SDL_ClearComposition(window)));
        }
    }
    
    @Inject(method = "nSDL_SetTextInputArea", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetTextInputArea(long window, long rect, int cursor, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            if (Ixeris.getConfig().isFullyBlockingMode()) {
                cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.nSDL_SetTextInputArea(window, rect, cursor)));
            }
            else {
                var copiedRect = SdlMemoryHelper.copySDL_Rect(rect);
                MainThreadDispatcher.run(() -> {
                    SDLKeyboard.nSDL_SetTextInputArea(window, copiedRect, cursor);
                    MemoryUtil.nmemFree(copiedRect);
                });
                cir.setReturnValue(true);
            }
        }
    }
    
    @Inject(method = "nSDL_GetTextInputArea", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetTextInputArea(long window, long rect, long cursor, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.nSDL_GetTextInputArea(window, rect, cursor)));
        }
    }
    
    @Inject(method = "SDL_HasScreenKeyboardSupport", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_HasScreenKeyboardSupport(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.SDL_HasScreenKeyboardSupport()));
        }
    }
    
    @Inject(method = "SDL_ScreenKeyboardShown", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_ScreenKeyboardShown(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLKeyboard.SDL_ScreenKeyboardShown(window)));
        }
    }
}
