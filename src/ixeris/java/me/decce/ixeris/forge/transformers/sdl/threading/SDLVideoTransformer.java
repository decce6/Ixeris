//? if forge { 
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.sdl.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.sdl.SdlHelper;
import me.decce.ixeris.core.sdl.state_caching.SdlStateCache;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.util.MemoryHelper;
import org.lwjgl.sdl.*;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = SDLVideo.class)
public final class SDLVideoTransformer {
    @CInline @CInject(method = "SDL_GetNumVideoDrivers", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetNumVideoDrivers(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetNumVideoDrivers)));
        }
    }

    @CInline @CInject(method = "nSDL_GetVideoDriver", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetVideoDriver(int index, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetVideoDriver, index)));
        }
    }

    @CInline @CInject(method = "nSDL_GetCurrentVideoDriver", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetCurrentVideoDriver(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetCurrentVideoDriver)));
        }
    }

    @CInline @CInject(method = "SDL_GetSystemTheme", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetSystemTheme(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetSystemTheme)));
        }
    }

    @CInline @CInject(method = "nSDL_GetDisplays", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplays(long count, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetDisplays, count)));
        }
    }

    @CInline @CInject(method = "SDL_GetPrimaryDisplay", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetPrimaryDisplay(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetPrimaryDisplay)));
        }
    }

    @CInline @CInject(method = "SDL_GetDisplayProperties", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetDisplayProperties(int displayID, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetDisplayProperties, displayID)));
        }
    }

    @CInline @CInject(method = "nSDL_GetDisplayName", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplayName(int displayID, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetDisplayName, displayID)));
        }
    }

    @CInline @CInject(method = "nSDL_GetDisplayBounds", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplayBounds(int displayID, long rect, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetDisplayBounds, displayID, rect)));
        }
    }

    @CInline @CInject(method = "nSDL_GetDisplayUsableBounds", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplayUsableBounds(int displayID, long rect, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetDisplayUsableBounds, displayID, rect)));
        }
    }

    @CInline @CInject(method = "SDL_GetNaturalDisplayOrientation", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetNaturalDisplayOrientation(int displayID, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetNaturalDisplayOrientation, displayID)));
        }
    }

    @CInline @CInject(method = "SDL_GetCurrentDisplayOrientation", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetCurrentDisplayOrientation(int displayID, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetCurrentDisplayOrientation, displayID)));
        }
    }

    @CInline @CInject(method = "SDL_GetDisplayContentScale", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetDisplayContentScale(int displayID, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetDisplayContentScale, displayID)));
        }
    }

    @CInline @CInject(method = "nSDL_GetFullscreenDisplayModes", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetFullscreenDisplayModes(int displayID, long count, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetFullscreenDisplayModes, displayID, count)));
        }
    }

    @CInline @CInject(method = "nSDL_GetClosestFullscreenDisplayMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetClosestFullscreenDisplayMode(int displayID, int w, int h, float refresh_rate, boolean include_high_density_modes, long closest, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetClosestFullscreenDisplayMode, displayID, w, h, refresh_rate, include_high_density_modes, closest)));
        }
    }

    @CInline @CInject(method = "nSDL_GetDesktopDisplayMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDesktopDisplayMode(int displayID, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetDesktopDisplayMode, displayID)));
        }
    }

    @CInline @CInject(method = "nSDL_GetCurrentDisplayMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetCurrentDisplayMode(int displayID, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(SdlStateCache.forDisplay(displayID).currentDisplayMode.get());
        }
    }

    @CInline @CInject(method = "nSDL_GetDisplayForPoint", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplayForPoint(long point, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetDisplayForPoint, point)));
        }
    }

    @CInline @CInject(method = "nSDL_GetDisplayForRect", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetDisplayForRect(long rect, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetDisplayForRect, rect)));
        }
    }

    @CInline @CInject(method = "SDL_GetDisplayForWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetDisplayForWindow(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(SdlStateCache.forWindow(window).displayForWindow.get());
        }
    }

    @CInline @CInject(method = "SDL_GetWindowPixelDensity", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowPixelDensity(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(SdlStateCache.forWindow(window).windowPixelDensity.get());
        }
    }

    @CInline @CInject(method = "SDL_GetWindowDisplayScale", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowDisplayScale(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowDisplayScale, window)));
        }
    }

    @CInline @CInject(method = "nSDL_SetWindowFullscreenMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowFullscreenMode(long window, long mode, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_SetWindowFullscreenMode, window, mode)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowFullscreenMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowFullscreenMode(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(SdlStateCache.forWindow(window).windowFullscreenMode.get());
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowICCProfile", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowICCProfile(long window, long size, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowICCProfile, window, size)));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowPixelFormat", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowPixelFormat(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowPixelFormat, window)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindows", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindows(long count, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindows, count)));
        }
    }

    @CInline @CInject(method = "nSDL_CreateWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_CreateWindow(long title, int w, int h, long flags, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_CreateWindow, title, w, h, flags)));
        }
    }

    @CInline @CInject(method = "SDL_CreatePopupWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_CreatePopupWindow(long parent, int offset_x, int offset_y, int w, int h, long flags, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_CreatePopupWindow, parent, offset_x, offset_y, w, h, flags)));
        }
    }

    @CInline @CInject(method = "SDL_CreateWindowWithProperties", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_CreateWindowWithProperties(int props, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_CreateWindowWithProperties, props)));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowID", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowID(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowID, window)));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowFromID", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowFromID(int id, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(SdlStateCache.windowFromId(id));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowParent", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowParent(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowParent, window)));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowProperties", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowProperties(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowProperties, window)));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowFlags", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowFlags(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowFlags, window)));
        }
    }

    @CInline @CInject(method = "nSDL_SetWindowTitle", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowTitle(long window, long title, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            if (Ixeris.getConfig().isFullyBlockingMode()) {
                cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_SetWindowTitle, window, title)));
            }
            else {
                SdlHelper.setWindowTitleLater(window, title);
                cir.setReturnValue(true);
            }
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowTitle", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowTitle(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowTitle, window)));
        }
    }

    @CInline @CInject(method = "nSDL_SetWindowIcon", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowIcon(long window, long icon, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_SetWindowIcon, window, icon)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowPosition", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowPosition(long window, int x, int y, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowPosition, window, x, y)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowPosition", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowPosition(long window, long x, long y, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowPosition, window, x, y)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowSize", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowSize(long window, int w, int h, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowSize, window, w, h)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowSize", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowSize(long window, long w, long h, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowSize, window, w, h)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowSafeArea", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowSafeArea(long window, long rect, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowSafeArea, window, rect)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowAspectRatio", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowAspectRatio(long window, float min_aspect, float max_aspect, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowAspectRatio, window, min_aspect, max_aspect)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowAspectRatio", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowAspectRatio(long window, long min_aspect, long max_aspect, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowAspectRatio, window, min_aspect, max_aspect)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowBordersSize", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowBordersSize(long window, long top, long left, long bottom, long right, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowBordersSize, window, top, left, bottom, right)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowSizeInPixels", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowSizeInPixels(long window, long w, long h, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowSizeInPixels, window, w, h)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowMinimumSize", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowMinimumSize(long window, int min_w, int min_h, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowMinimumSize, window, min_w, min_h)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowMinimumSize", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowMinimumSize(long window, long w, long h, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowMinimumSize, window, w, h)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowMaximumSize", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowMaximumSize(long window, int max_w, int max_h, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowMaximumSize, window, max_w, max_h)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowMaximumSize", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowMaximumSize(long window, long w, long h, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowMaximumSize, window, w, h)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowBordered", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowBordered(long window, boolean bordered, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowBordered, window, bordered)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowResizable", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowResizable(long window, boolean resizable, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowResizable, window, resizable)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowAlwaysOnTop", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowAlwaysOnTop(long window, boolean on_top, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowAlwaysOnTop, window, on_top)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowFillDocument", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowFillDocument(long window, boolean fill, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowFillDocument, window, fill)));
        }
    }

    @CInline @CInject(method = "SDL_ShowWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_ShowWindow(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_ShowWindow, window)));
        }
    }

    @CInline @CInject(method = "SDL_HideWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_HideWindow(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_HideWindow, window)));
        }
    }

    @CInline @CInject(method = "SDL_RaiseWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_RaiseWindow(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_RaiseWindow, window)));
        }
    }

    @CInline @CInject(method = "SDL_MaximizeWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_MaximizeWindow(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_MaximizeWindow, window)));
        }
    }

    @CInline @CInject(method = "SDL_MinimizeWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_MinimizeWindow(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_MinimizeWindow, window)));
        }
    }

    @CInline @CInject(method = "SDL_RestoreWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_RestoreWindow(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_RestoreWindow, window)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowFullscreen", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowFullscreen(long window, boolean fullscreen, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowFullscreen, window, fullscreen)));
        }
    }

    @CInline @CInject(method = "SDL_SyncWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SyncWindow(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SyncWindow, window)));
        }
    }

    @CInline @CInject(method = "SDL_WindowHasSurface", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_WindowHasSurface(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_WindowHasSurface, window)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowSurface", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowSurface(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowSurface, window)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowSurfaceVSync", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowSurfaceVSync(long window, int vsync, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowSurfaceVSync, window, vsync)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowSurfaceVSync", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowSurfaceVSync(long window, long vsync, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowSurfaceVSync, window, vsync)));
        }
    }

    @CInline @CInject(method = "SDL_UpdateWindowSurface", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_UpdateWindowSurface(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_UpdateWindowSurface, window)));
        }
    }

    @CInline @CInject(method = "nSDL_UpdateWindowSurfaceRects", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_UpdateWindowSurfaceRects(long window, long rects, int numrects, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_UpdateWindowSurfaceRects, window, rects, numrects)));
        }
    }

    @CInline @CInject(method = "SDL_DestroyWindowSurface", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_DestroyWindowSurface(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_DestroyWindowSurface, window)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowKeyboardGrab", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowKeyboardGrab(long window, boolean grabbed, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowKeyboardGrab, window, grabbed)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowMouseGrab", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowMouseGrab(long window, boolean grabbed, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowMouseGrab, window, grabbed)));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowKeyboardGrab", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowKeyboardGrab(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowKeyboardGrab, window)));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowMouseGrab", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowMouseGrab(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowMouseGrab, window)));
        }
    }

    @CInline @CInject(method = "SDL_GetGrabbedWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetGrabbedWindow(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetGrabbedWindow)));
        }
    }

    @CInline @CInject(method = "nSDL_SetWindowMouseRect", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowMouseRect(long window, long rect, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_SetWindowMouseRect, window, rect)));
        }
    }

    @CInline @CInject(method = "nSDL_GetWindowMouseRect", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GetWindowMouseRect(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GetWindowMouseRect, window)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowOpacity", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowOpacity(long window, float opacity, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowOpacity, window, opacity)));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowOpacity", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowOpacity(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowOpacity, window)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowParent", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowParent(long window, long parent, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowParent, window, parent)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowModal", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowModal(long window, boolean modal, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowModal, window, modal)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowFocusable", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowFocusable(long window, boolean focusable, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowFocusable, window, focusable)));
        }
    }

    @CInline @CInject(method = "SDL_ShowWindowSystemMenu", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_ShowWindowSystemMenu(long window, int x, int y, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_ShowWindowSystemMenu, window, x, y)));
        }
    }

    @CInline @CInject(method = "nSDL_SetWindowHitTest", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowHitTest(long window, long callback, long callback_data, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_SetWindowHitTest, window, callback, callback_data)));
        }
    }

    @CInline @CInject(method = "nSDL_SetWindowShape", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_SetWindowShape(long window, long shape, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_SetWindowShape, window, shape)));
        }
    }

    @CInline @CInject(method = "SDL_FlashWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_FlashWindow(long window, int operation, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_FlashWindow, window, operation)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowProgressState", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowProgressState(long window, int state, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowProgressState, window, state)));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowProgressState", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowProgressState(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowProgressState, window)));
        }
    }

    @CInline @CInject(method = "SDL_SetWindowProgressValue", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_SetWindowProgressValue(long window, float value, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_SetWindowProgressValue, window, value)));
        }
    }

    @CInline @CInject(method = "SDL_GetWindowProgressValue", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GetWindowProgressValue(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GetWindowProgressValue, window)));
        }
    }

    @CInline @CInject(method = "SDL_DestroyWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_DestroyWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true); MainThreadDispatcher.run(makeRunnable(SDLVideo::SDL_DestroyWindow, window));
        }
    }

    @CInline @CInject(method = "SDL_ScreenSaverEnabled", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_ScreenSaverEnabled(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_ScreenSaverEnabled)));
        }
    }

    @CInline @CInject(method = "SDL_EnableScreenSaver", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_EnableScreenSaver(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_EnableScreenSaver)));
        }
    }

    @CInline @CInject(method = "SDL_DisableScreenSaver", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_DisableScreenSaver(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_DisableScreenSaver)));
        }
    }

    @CInline @CInject(method = "nSDL_GL_LoadLibrary", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GL_LoadLibrary(long path, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GL_LoadLibrary, path)));
        }
    }

    @CInline @CInject(method = "nSDL_EGL_GetProcAddress", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_EGL_GetProcAddress(long proc, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_EGL_GetProcAddress, proc)));
        }
    }

    @CInline @CInject(method = "SDL_GL_UnloadLibrary", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_UnloadLibrary(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true); MainThreadDispatcher.run(makeRunnable(SDLVideo::SDL_GL_UnloadLibrary));
        }
    }

    @CInline @CInject(method = "nSDL_GL_ExtensionSupported", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GL_ExtensionSupported(long extension, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GL_ExtensionSupported, extension)));
        }
    }

    @CInline @CInject(method = "SDL_GL_ResetAttributes", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_ResetAttributes(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true); MainThreadDispatcher.run(makeRunnable(SDLVideo::SDL_GL_ResetAttributes));
        }
    }

    @CInline @CInject(method = "SDL_GL_SetAttribute", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_SetAttribute(int attr, int value, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GL_SetAttribute, attr, value)));
        }
    }

    @CInline @CInject(method = "nSDL_GL_GetAttribute", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GL_GetAttribute(int attr, long value, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GL_GetAttribute, attr, value)));
        }
    }

    @CInline @CInject(method = "SDL_GL_CreateContext", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_CreateContext(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GL_CreateContext, window)));
            return;
        }
    }

    @CInline @CInject(method = "SDL_GL_MakeCurrent", target = @CTarget("HEAD"))
    private static void ixeris$SDL_GL_MakeCurrent(long window, long context, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GL_MakeCurrent, window, 0L));
        }
    }

    @CInline @CInject(method = "SDL_GL_GetCurrentWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_GetCurrentWindow(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GL_GetCurrentWindow)));
        }
    }

    @CInline @CInject(method = "SDL_EGL_GetCurrentDisplay", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_EGL_GetCurrentDisplay(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_EGL_GetCurrentDisplay)));
        }
    }

    @CInline @CInject(method = "SDL_EGL_GetCurrentConfig", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_EGL_GetCurrentConfig(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_EGL_GetCurrentConfig)));
        }
    }

    @CInline @CInject(method = "SDL_EGL_GetWindowSurface", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_EGL_GetWindowSurface(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_EGL_GetWindowSurface, window)));
        }
    }

    @CInline @CInject(method = "nSDL_EGL_SetAttributeCallbacks", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_EGL_SetAttributeCallbacks(long platformAttribCallback, long surfaceAttribCallback, long contextAttribCallback, long userdata, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true); MainThreadDispatcher.run(makeRunnable(SDLVideo::nSDL_EGL_SetAttributeCallbacks, platformAttribCallback, surfaceAttribCallback, contextAttribCallback, userdata));
        }
    }

    @CInline @CInject(method = "SDL_GL_SetSwapInterval", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$SDL_GL_SetSwapInterval(int interval, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::SDL_GL_SetSwapInterval, interval)));
        }
    }

    @CInline @CInject(method = "nSDL_GL_GetSwapInterval", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$nSDL_GL_GetSwapInterval(long interval, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(SDLVideo::nSDL_GL_GetSwapInterval, interval)));
        }
    }
}

*///? }