//? if forge { 
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.sdl.SdlEventHandler;
import me.decce.ixeris.core.sdl.SdlMemoryHelper;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.*;
import org.lwjgl.system.MemoryUtil;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = SDLKeyboard.class)
public final class SDLKeyboardTransformer {    
    @CInline @CInject(method = "SDL_HasKeyboard", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_HasKeyboard(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::SDL_HasKeyboard)));
        }
    }
    
    @CInline @CInject(method = "nSDL_GetKeyboards", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetKeyboards(long count, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::nSDL_GetKeyboards, count)));
        }
    }
    
    @CInline @CInject(method = "nSDL_GetKeyboardNameForID", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetKeyboardNameForID(int instance_id, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::nSDL_GetKeyboardNameForID, instance_id)));
        }
    }
    
    @CInline @CInject(method = "SDL_GetKeyboardFocus", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetKeyboardFocus(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::SDL_GetKeyboardFocus)));
        }
    }
    
    @CInline @CInject(method = "SDL_ResetKeyboard", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_ResetKeyboard(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true); MainThreadDispatcher.run(makeRunnable(SDLKeyboard::SDL_ResetKeyboard));
        }
    }
    
    @CInline @CInject(method = "SDL_StartTextInput", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_StartTextInput(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::SDL_StartTextInput, window)));
        }
    }
    
    @CInline @CInject(method = "SDL_StartTextInputWithProperties", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_StartTextInputWithProperties(long window, int props, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::SDL_StartTextInputWithProperties, window, props)));
        }
    }
    
    @CInline @CInject(method = "SDL_TextInputActive", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_TextInputActive(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::SDL_TextInputActive, window)));
        }
    }
    
    @CInline @CInject(method = "SDL_StopTextInput", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_StopTextInput(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::SDL_StopTextInput, window)));
        }
    }
    
    @CInline @CInject(method = "SDL_ClearComposition", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_ClearComposition(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::SDL_ClearComposition, window)));
        }
    }
    
    @CInline @CInject(method = "nSDL_SetTextInputArea", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetTextInputArea(long window, long rect, int cursor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            if (Ixeris.getConfig().isFullyBlockingMode()) {
                cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::nSDL_SetTextInputArea, window, rect, cursor)));
            }
            else {
                if (Ixeris.getEventHandler() instanceof SdlEventHandler sdlEventHandler) {
                    sdlEventHandler.requestSetTextInputArea(window, rect, cursor);
                    cir.setReturnValue(true);
                }
            }
        }
    }
    
    @CInline @CInject(method = "nSDL_GetTextInputArea", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetTextInputArea(long window, long rect, long cursor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::nSDL_GetTextInputArea, window, rect, cursor)));
        }
    }
    
    @CInline @CInject(method = "SDL_HasScreenKeyboardSupport", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_HasScreenKeyboardSupport(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::SDL_HasScreenKeyboardSupport)));
        }
    }
    
    @CInline @CInject(method = "SDL_ScreenKeyboardShown", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_ScreenKeyboardShown(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLKeyboard::SDL_ScreenKeyboardShown, window)));
        }
    }
}

*///? }