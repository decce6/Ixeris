//? if forge { 
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.PointerBuffer;
import org.lwjgl.sdl.*;
import java.nio.*;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = SDLMouse.class)
public final class SDLMouseTransformer {    
    @CInline @CInject(method = "SDL_HasMouse", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_HasMouse(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_HasMouse)));
        }
    }
    
    @CInline @CInject(method = "nSDL_GetMice", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetMice(long count, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::nSDL_GetMice, count)));
        }
    }
    
    @CInline @CInject(method = "nSDL_GetMouseNameForID", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetMouseNameForID(int instance_id, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::nSDL_GetMouseNameForID, instance_id)));
        }
    }
    
    @CInline @CInject(method = "SDL_GetMouseFocus", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetMouseFocus(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_GetMouseFocus)));
        }
    }
    
    @CInline @CInject(method = "nSDL_GetMouseState", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetMouseState(long x, long y, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::nSDL_GetMouseState, x, y)));
        }
    }
    
    @CInline @CInject(method = "nSDL_GetGlobalMouseState", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetGlobalMouseState(long x, long y, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::nSDL_GetGlobalMouseState, x, y)));
        }
    }
    
    @CInline @CInject(method = "nSDL_GetRelativeMouseState", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetRelativeMouseState(long x, long y, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::nSDL_GetRelativeMouseState, x, y)));
        }
    }
    
    @CInline @CInject(method = "SDL_WarpMouseInWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_WarpMouseInWindow(long window, float x, float y, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true); MainThreadDispatcher.run(makeRunnable(SDLMouse::SDL_WarpMouseInWindow, window, x, y));
        }
    }
    
    @CInline @CInject(method = "SDL_WarpMouseGlobal", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_WarpMouseGlobal(float x, float y, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_WarpMouseGlobal, x, y)));
        }
    }
    
    @CInline @CInject(method = "nSDL_SetRelativeMouseTransform", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetRelativeMouseTransform(long callback, long userdata, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::nSDL_SetRelativeMouseTransform, callback, userdata)));
        }
    }
    
    @CInline @CInject(method = "SDL_SetWindowRelativeMouseMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowRelativeMouseMode(long window, boolean enabled, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_SetWindowRelativeMouseMode, window, enabled)));
        }
    }
    
    @CInline @CInject(method = "SDL_GetWindowRelativeMouseMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowRelativeMouseMode(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_GetWindowRelativeMouseMode, window)));
        }
    }
    
    @CInline @CInject(method = "SDL_CaptureMouse", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_CaptureMouse(boolean enabled, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_CaptureMouse, enabled)));
        }
    }
    
    @CInline @CInject(method = "nSDL_CreateCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_CreateCursor(long data, long mask, int w, int h, int hot_x, int hot_y, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::nSDL_CreateCursor, data, mask, w, h, hot_x, hot_y)));
        }
    }
    
    @CInline @CInject(method = "nSDL_CreateColorCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_CreateColorCursor(long surface, int hot_x, int hot_y, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::nSDL_CreateColorCursor, surface, hot_x, hot_y)));
        }
    }
    
    @CInline @CInject(method = "nSDL_CreateAnimatedCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_CreateAnimatedCursor(long frames, int frame_count, int hot_x, int hot_y, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::nSDL_CreateAnimatedCursor, frames, frame_count, hot_x, hot_y)));
        }
    }
    
    @CInline @CInject(method = "SDL_CreateSystemCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_CreateSystemCursor(int id, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_CreateSystemCursor, id)));
        }
    }
    
    @CInline @CInject(method = "SDL_SetCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetCursor(long cursor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_SetCursor, cursor)));
        }
    }
    
    @CInline @CInject(method = "SDL_GetCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetCursor(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_GetCursor)));
        }
    }
    
    @CInline @CInject(method = "SDL_GetDefaultCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetDefaultCursor(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_GetDefaultCursor)));
        }
    }
    
    @CInline @CInject(method = "SDL_DestroyCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_DestroyCursor(long cursor, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true); MainThreadDispatcher.run(makeRunnable(SDLMouse::SDL_DestroyCursor, cursor));
        }
    }
    
    @CInline @CInject(method = "SDL_ShowCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_ShowCursor(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_ShowCursor)));
        }
    }
    
    @CInline @CInject(method = "SDL_HideCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_HideCursor(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_HideCursor)));
        }
    }
    
    @CInline @CInject(method = "SDL_CursorVisible", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_CursorVisible(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLMouse::SDL_CursorVisible)));
        }
    }
}

*///? }