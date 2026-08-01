package me.decce.ixeris.core.mixins.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.sdl.SdlHelper;
import me.decce.ixeris.core.sdl.state_caching.SdlStateCache;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.util.MemoryHelper;
import org.lwjgl.sdl.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SDLVideo.class, remap = false)
public final class SDLVideoMixin {
    @Inject(method = "SDL_GetNumVideoDrivers", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetNumVideoDrivers(CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetNumVideoDrivers()));
        }
    }

    @Inject(method = "nSDL_GetVideoDriver", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetVideoDriver(int index, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetVideoDriver(index)));
        }
    }

    @Inject(method = "nSDL_GetCurrentVideoDriver", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetCurrentVideoDriver(CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetCurrentVideoDriver()));
        }
    }

    @Inject(method = "SDL_GetSystemTheme", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetSystemTheme(CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetSystemTheme()));
        }
    }

    @Inject(method = "nSDL_GetDisplays", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplays(long count, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetDisplays(count)));
        }
    }

    @Inject(method = "SDL_GetPrimaryDisplay", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetPrimaryDisplay(CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetPrimaryDisplay()));
        }
    }

    @Inject(method = "SDL_GetDisplayProperties", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetDisplayProperties(int displayID, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetDisplayProperties(displayID)));
        }
    }

    @Inject(method = "nSDL_GetDisplayName", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplayName(int displayID, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetDisplayName(displayID)));
        }
    }

    @Inject(method = "nSDL_GetDisplayBounds", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplayBounds(int displayID, long rect, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetDisplayBounds(displayID, rect)));
        }
    }

    @Inject(method = "nSDL_GetDisplayUsableBounds", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplayUsableBounds(int displayID, long rect, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetDisplayUsableBounds(displayID, rect)));
        }
    }

    @Inject(method = "SDL_GetNaturalDisplayOrientation", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetNaturalDisplayOrientation(int displayID, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetNaturalDisplayOrientation(displayID)));
        }
    }

    @Inject(method = "SDL_GetCurrentDisplayOrientation", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetCurrentDisplayOrientation(int displayID, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetCurrentDisplayOrientation(displayID)));
        }
    }

    @Inject(method = "SDL_GetDisplayContentScale", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetDisplayContentScale(int displayID, CallbackInfoReturnable<Float> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetDisplayContentScale(displayID)));
        }
    }

    @Inject(method = "nSDL_GetFullscreenDisplayModes", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetFullscreenDisplayModes(int displayID, long count, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetFullscreenDisplayModes(displayID, count)));
        }
    }

    @Inject(method = "nSDL_GetClosestFullscreenDisplayMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetClosestFullscreenDisplayMode(int displayID, int w, int h, float refresh_rate, boolean include_high_density_modes, long closest, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetClosestFullscreenDisplayMode(displayID, w, h, refresh_rate, include_high_density_modes, closest)));
        }
    }

    @Inject(method = "nSDL_GetDesktopDisplayMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDesktopDisplayMode(int displayID, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetDesktopDisplayMode(displayID)));
        }
    }

    @Inject(method = "nSDL_GetCurrentDisplayMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetCurrentDisplayMode(int displayID, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(SdlStateCache.forDisplay(displayID).currentDisplayMode.get());
        }
    }

    @Inject(method = "nSDL_GetDisplayForPoint", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplayForPoint(long point, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetDisplayForPoint(point)));
        }
    }

    @Inject(method = "nSDL_GetDisplayForRect", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplayForRect(long rect, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetDisplayForRect(rect)));
        }
    }

    @Inject(method = "SDL_GetDisplayForWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetDisplayForWindow(long window, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(SdlStateCache.forWindow(window).displayForWindow.get());
        }
    }

    @Inject(method = "SDL_GetWindowPixelDensity", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowPixelDensity(long window, CallbackInfoReturnable<Float> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(SdlStateCache.forWindow(window).windowPixelDensity.get());
        }
    }

    @Inject(method = "SDL_GetWindowDisplayScale", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowDisplayScale(long window, CallbackInfoReturnable<Float> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowDisplayScale(window)));
        }
    }

    @Inject(method = "nSDL_SetWindowFullscreenMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowFullscreenMode(long window, long mode, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_SetWindowFullscreenMode(window, mode)));
        }
    }

    @Inject(method = "nSDL_GetWindowFullscreenMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowFullscreenMode(long window, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(SdlStateCache.forWindow(window).windowFullscreenMode.get());
        }
    }

    @Inject(method = "nSDL_GetWindowICCProfile", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowICCProfile(long window, long size, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowICCProfile(window, size)));
        }
    }

    @Inject(method = "SDL_GetWindowPixelFormat", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowPixelFormat(long window, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowPixelFormat(window)));
        }
    }

    @Inject(method = "nSDL_GetWindows", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindows(long count, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindows(count)));
        }
    }

    @Inject(method = "nSDL_CreateWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_CreateWindow(long title, int w, int h, long flags, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_CreateWindow(title, w, h, flags)));
        }
    }

    @Inject(method = "SDL_CreatePopupWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_CreatePopupWindow(long parent, int offset_x, int offset_y, int w, int h, long flags, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_CreatePopupWindow(parent, offset_x, offset_y, w, h, flags)));
        }
    }

    @Inject(method = "SDL_CreateWindowWithProperties", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_CreateWindowWithProperties(int props, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_CreateWindowWithProperties(props)));
        }
    }

    @Inject(method = "SDL_GetWindowID", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowID(long window, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowID(window)));
        }
    }

    @Inject(method = "SDL_GetWindowFromID", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowFromID(int id, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(SdlStateCache.windowFromId(id));
        }
    }

    @Inject(method = "SDL_GetWindowParent", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowParent(long window, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowParent(window)));
        }
    }

    @Inject(method = "SDL_GetWindowProperties", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowProperties(long window, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowProperties(window)));
        }
    }

    @Inject(method = "SDL_GetWindowFlags", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowFlags(long window, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowFlags(window)));
        }
    }

    @Inject(method = "nSDL_SetWindowTitle", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowTitle(long window, long title, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            if (Ixeris.getConfig().isFullyBlockingMode()) {
                cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_SetWindowTitle(window, title)));
            }
            else {
                SdlHelper.setWindowTitleLater(window, title);
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "nSDL_GetWindowTitle", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowTitle(long window, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowTitle(window)));
        }
    }

    @Inject(method = "nSDL_SetWindowIcon", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowIcon(long window, long icon, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_SetWindowIcon(window, icon)));
        }
    }

    @Inject(method = "SDL_SetWindowPosition", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowPosition(long window, int x, int y, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowPosition(window, x, y)));
        }
    }

    @Inject(method = "nSDL_GetWindowPosition", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowPosition(long window, long x, long y, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowPosition(window, x, y)));
        }
    }

    @Inject(method = "SDL_SetWindowSize", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowSize(long window, int w, int h, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowSize(window, w, h)));
        }
    }

    @Inject(method = "nSDL_GetWindowSize", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowSize(long window, long w, long h, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowSize(window, w, h)));
        }
    }

    @Inject(method = "nSDL_GetWindowSafeArea", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowSafeArea(long window, long rect, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowSafeArea(window, rect)));
        }
    }

    @Inject(method = "SDL_SetWindowAspectRatio", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowAspectRatio(long window, float min_aspect, float max_aspect, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowAspectRatio(window, min_aspect, max_aspect)));
        }
    }

    @Inject(method = "nSDL_GetWindowAspectRatio", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowAspectRatio(long window, long min_aspect, long max_aspect, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowAspectRatio(window, min_aspect, max_aspect)));
        }
    }

    @Inject(method = "nSDL_GetWindowBordersSize", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowBordersSize(long window, long top, long left, long bottom, long right, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowBordersSize(window, top, left, bottom, right)));
        }
    }

    @Inject(method = "nSDL_GetWindowSizeInPixels", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowSizeInPixels(long window, long w, long h, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowSizeInPixels(window, w, h)));
        }
    }

    @Inject(method = "SDL_SetWindowMinimumSize", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowMinimumSize(long window, int min_w, int min_h, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowMinimumSize(window, min_w, min_h)));
        }
    }

    @Inject(method = "nSDL_GetWindowMinimumSize", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowMinimumSize(long window, long w, long h, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowMinimumSize(window, w, h)));
        }
    }

    @Inject(method = "SDL_SetWindowMaximumSize", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowMaximumSize(long window, int max_w, int max_h, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowMaximumSize(window, max_w, max_h)));
        }
    }

    @Inject(method = "nSDL_GetWindowMaximumSize", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowMaximumSize(long window, long w, long h, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowMaximumSize(window, w, h)));
        }
    }

    @Inject(method = "SDL_SetWindowBordered", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowBordered(long window, boolean bordered, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowBordered(window, bordered)));
        }
    }

    @Inject(method = "SDL_SetWindowResizable", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowResizable(long window, boolean resizable, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowResizable(window, resizable)));
        }
    }

    @Inject(method = "SDL_SetWindowAlwaysOnTop", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowAlwaysOnTop(long window, boolean on_top, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowAlwaysOnTop(window, on_top)));
        }
    }

    @Inject(method = "SDL_SetWindowFillDocument", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowFillDocument(long window, boolean fill, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowFillDocument(window, fill)));
        }
    }

    @Inject(method = "SDL_ShowWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_ShowWindow(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_ShowWindow(window)));
        }
    }

    @Inject(method = "SDL_HideWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_HideWindow(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_HideWindow(window)));
        }
    }

    @Inject(method = "SDL_RaiseWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_RaiseWindow(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_RaiseWindow(window)));
        }
    }

    @Inject(method = "SDL_MaximizeWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_MaximizeWindow(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_MaximizeWindow(window)));
        }
    }

    @Inject(method = "SDL_MinimizeWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_MinimizeWindow(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_MinimizeWindow(window)));
        }
    }

    @Inject(method = "SDL_RestoreWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_RestoreWindow(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_RestoreWindow(window)));
        }
    }

    @Inject(method = "SDL_SetWindowFullscreen", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowFullscreen(long window, boolean fullscreen, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowFullscreen(window, fullscreen)));
        }
    }

    @Inject(method = "SDL_SyncWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SyncWindow(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SyncWindow(window)));
        }
    }

    @Inject(method = "SDL_WindowHasSurface", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_WindowHasSurface(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_WindowHasSurface(window)));
        }
    }

    @Inject(method = "nSDL_GetWindowSurface", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowSurface(long window, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowSurface(window)));
        }
    }

    @Inject(method = "SDL_SetWindowSurfaceVSync", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowSurfaceVSync(long window, int vsync, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowSurfaceVSync(window, vsync)));
        }
    }

    @Inject(method = "nSDL_GetWindowSurfaceVSync", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowSurfaceVSync(long window, long vsync, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowSurfaceVSync(window, vsync)));
        }
    }

    @Inject(method = "SDL_UpdateWindowSurface", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_UpdateWindowSurface(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_UpdateWindowSurface(window)));
        }
    }

    @Inject(method = "nSDL_UpdateWindowSurfaceRects", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_UpdateWindowSurfaceRects(long window, long rects, int numrects, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_UpdateWindowSurfaceRects(window, rects, numrects)));
        }
    }

    @Inject(method = "SDL_DestroyWindowSurface", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_DestroyWindowSurface(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_DestroyWindowSurface(window)));
        }
    }

    @Inject(method = "SDL_SetWindowKeyboardGrab", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowKeyboardGrab(long window, boolean grabbed, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowKeyboardGrab(window, grabbed)));
        }
    }

    @Inject(method = "SDL_SetWindowMouseGrab", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowMouseGrab(long window, boolean grabbed, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowMouseGrab(window, grabbed)));
        }
    }

    @Inject(method = "SDL_GetWindowKeyboardGrab", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowKeyboardGrab(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowKeyboardGrab(window)));
        }
    }

    @Inject(method = "SDL_GetWindowMouseGrab", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowMouseGrab(long window, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowMouseGrab(window)));
        }
    }

    @Inject(method = "SDL_GetGrabbedWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetGrabbedWindow(CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetGrabbedWindow()));
        }
    }

    @Inject(method = "nSDL_SetWindowMouseRect", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowMouseRect(long window, long rect, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_SetWindowMouseRect(window, rect)));
        }
    }

    @Inject(method = "nSDL_GetWindowMouseRect", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowMouseRect(long window, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GetWindowMouseRect(window)));
        }
    }

    @Inject(method = "SDL_SetWindowOpacity", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowOpacity(long window, float opacity, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowOpacity(window, opacity)));
        }
    }

    @Inject(method = "SDL_GetWindowOpacity", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowOpacity(long window, CallbackInfoReturnable<Float> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowOpacity(window)));
        }
    }

    @Inject(method = "SDL_SetWindowParent", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowParent(long window, long parent, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowParent(window, parent)));
        }
    }

    @Inject(method = "SDL_SetWindowModal", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowModal(long window, boolean modal, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowModal(window, modal)));
        }
    }

    @Inject(method = "SDL_SetWindowFocusable", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowFocusable(long window, boolean focusable, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowFocusable(window, focusable)));
        }
    }

    @Inject(method = "SDL_ShowWindowSystemMenu", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_ShowWindowSystemMenu(long window, int x, int y, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_ShowWindowSystemMenu(window, x, y)));
        }
    }

    @Inject(method = "nSDL_SetWindowHitTest", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowHitTest(long window, long callback, long callback_data, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_SetWindowHitTest(window, callback, callback_data)));
        }
    }

    @Inject(method = "nSDL_SetWindowShape", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowShape(long window, long shape, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_SetWindowShape(window, shape)));
        }
    }

    @Inject(method = "SDL_FlashWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_FlashWindow(long window, int operation, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_FlashWindow(window, operation)));
        }
    }

    @Inject(method = "SDL_SetWindowProgressState", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowProgressState(long window, int state, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowProgressState(window, state)));
        }
    }

    @Inject(method = "SDL_GetWindowProgressState", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowProgressState(long window, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowProgressState(window)));
        }
    }

    @Inject(method = "SDL_SetWindowProgressValue", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowProgressValue(long window, float value, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_SetWindowProgressValue(window, value)));
        }
    }

    @Inject(method = "SDL_GetWindowProgressValue", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowProgressValue(long window, CallbackInfoReturnable<Float> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GetWindowProgressValue(window)));
        }
    }

    @Inject(method = "SDL_DestroyWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_DestroyWindow(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel(); MainThreadDispatcher.run(() -> SDLVideo.SDL_DestroyWindow(window));
        }
    }

    @Inject(method = "SDL_ScreenSaverEnabled", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_ScreenSaverEnabled(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_ScreenSaverEnabled()));
        }
    }

    @Inject(method = "SDL_EnableScreenSaver", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_EnableScreenSaver(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_EnableScreenSaver()));
        }
    }

    @Inject(method = "SDL_DisableScreenSaver", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_DisableScreenSaver(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_DisableScreenSaver()));
        }
    }

    @Inject(method = "nSDL_GL_LoadLibrary", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GL_LoadLibrary(long path, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GL_LoadLibrary(path)));
        }
    }

    @Inject(method = "nSDL_GL_GetProcAddress", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GL_GetProcAddress(long proc, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GL_GetProcAddress(proc)));
        }
    }

    @Inject(method = "nSDL_EGL_GetProcAddress", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_EGL_GetProcAddress(long proc, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_EGL_GetProcAddress(proc)));
        }
    }

    @Inject(method = "SDL_GL_UnloadLibrary", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_UnloadLibrary(CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel(); MainThreadDispatcher.run(() -> SDLVideo.SDL_GL_UnloadLibrary());
        }
    }

    @Inject(method = "nSDL_GL_ExtensionSupported", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GL_ExtensionSupported(long extension, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GL_ExtensionSupported(extension)));
        }
    }

    @Inject(method = "SDL_GL_ResetAttributes", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_ResetAttributes(CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel(); MainThreadDispatcher.run(() -> SDLVideo.SDL_GL_ResetAttributes());
        }
    }

    @Inject(method = "SDL_GL_SetAttribute", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_SetAttribute(int attr, int value, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GL_SetAttribute(attr, value)));
        }
    }

    @Inject(method = "nSDL_GL_GetAttribute", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GL_GetAttribute(int attr, long value, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GL_GetAttribute(attr, value)));
        }
    }

    @Inject(method = "SDL_GL_CreateContext", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_CreateContext(long window, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GL_CreateContext(window)));
            return;
        }
    }

    @Inject(method = "SDL_GL_MakeCurrent", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_MakeCurrent(long window, long context, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            MainThreadDispatcher.query(() -> SDLVideo.SDL_GL_MakeCurrent(window, 0L));
        }
    }

    @Inject(method = "SDL_GL_GetCurrentWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_GetCurrentWindow(CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GL_GetCurrentWindow()));
        }
    }

//    @Inject(method = "SDL_GL_GetCurrentContext", at = @At("HEAD"), cancellable = true)
//    private static void ixeris$SDL_GL_GetCurrentContext(CallbackInfoReturnable<Long> cir) {
//        if (!Ixeris.isOnMainThread()) {
//            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GL_GetCurrentContext()));
//        }
//    }

    @Inject(method = "SDL_EGL_GetCurrentDisplay", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_EGL_GetCurrentDisplay(CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_EGL_GetCurrentDisplay()));
        }
    }

    @Inject(method = "SDL_EGL_GetCurrentConfig", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_EGL_GetCurrentConfig(CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_EGL_GetCurrentConfig()));
        }
    }

    @Inject(method = "SDL_EGL_GetWindowSurface", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_EGL_GetWindowSurface(long window, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_EGL_GetWindowSurface(window)));
        }
    }

    @Inject(method = "nSDL_EGL_SetAttributeCallbacks", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_EGL_SetAttributeCallbacks(long platformAttribCallback, long surfaceAttribCallback, long contextAttribCallback, long userdata, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel(); MainThreadDispatcher.run(() -> SDLVideo.nSDL_EGL_SetAttributeCallbacks(platformAttribCallback, surfaceAttribCallback, contextAttribCallback, userdata));
        }
    }

    @Inject(method = "SDL_GL_SetSwapInterval", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_SetSwapInterval(int interval, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GL_SetSwapInterval(interval)));
        }
    }

    @Inject(method = "nSDL_GL_GetSwapInterval", at = @At("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GL_GetSwapInterval(long interval, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.nSDL_GL_GetSwapInterval(interval)));
        }
    }

//    @Inject(method = "SDL_GL_SwapWindow", at = @At("HEAD"), cancellable = true)
//    private static void ixeris$SDL_GL_SwapWindow(long window, CallbackInfoReturnable<Boolean> cir) {
//        if (!Ixeris.isOnMainThread()) {
//            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GL_SwapWindow(window)));
//        }
//    }

    @Inject(method = "SDL_GL_DestroyContext", at = @At("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_DestroyContext(long context, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> SDLVideo.SDL_GL_DestroyContext(context)));
        }
    }
}
