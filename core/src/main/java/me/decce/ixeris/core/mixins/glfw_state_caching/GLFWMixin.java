package me.decce.ixeris.core.mixins.glfw_state_caching;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.util.PlatformHelper;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static me.decce.ixeris.core.glfw.state_caching.util.BufferHelper.check;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwSetInputMode", at = @At("TAIL"))
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, CallbackInfo ci) {
        if (GlfwCacheManager.hasWindowCache(window)) {
            GlfwCacheManager.getWindowCache(window).inputMode().set(mode, value);
        }
    }

    @Inject(method = "glfwGetInputMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetInputMode(long window, int mode, CallbackInfoReturnable<Integer> cir) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).inputMode();
            if (cache.isCacheEnabled()) {
                cir.setReturnValue(cache.get(mode));
                return;
            }
        }
        cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetInputMode(window, mode)));
    }

    @Inject(method = "glfwGetKey", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKey(long window, int key, CallbackInfoReturnable<Integer> cir) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).keys();
            if (cache.isCacheEnabled()) {
                var ret = cache.get(key);
                if (ret == GLFW.GLFW_REPEAT) {
                    ret = GLFW.GLFW_PRESS;
                }
                cir.setReturnValue(ret);
                return;
            }
        }
        cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetKey(window, key)));
    }

    @Inject(method = "glfwGetKeyName", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKeyName(int key, int scancode, CallbackInfoReturnable<String> cir) {
        var cache = GlfwCacheManager.getGlobalCache().keyNames();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.get(key, scancode));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetKeyName(key, scancode)));
        }
    }

    @Inject(method = "glfwGetMouseButton", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMouseButton(long window, int button, CallbackInfoReturnable<Integer> cir) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).mouseButtons();
            if (cache.isCacheEnabled()) {
                cir.setReturnValue(cache.get(button));
                return;
            }
        }
        cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetMouseButton(window, button)));
    }

    @Inject(method = "glfwGetPrimaryMonitor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetPrimaryMonitor(CallbackInfoReturnable<Long> cir) {
        var cache = GlfwCacheManager.getGlobalCache().monitors();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.getPrimaryMonitor());
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(GLFW::glfwGetPrimaryMonitor));
        }
    }

    @Inject(method = "glfwGetWindowMonitor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowMonitor(long window, CallbackInfoReturnable<Long> cir) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).monitor();
            if (cache.isCacheEnabled()) {
                cir.setReturnValue(cache.get());
                return;
            }
        }
        cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetWindowMonitor(window)));
    }

    @Inject(method = "glfwSetWindowMonitor", at = @At("TAIL"))
    private static void ixeris$glfwSetWindowMonitor(long window, long monitor, int xpos, int ypos, int width, int height, int refreshRate, CallbackInfo ci) {
        if (GlfwCacheManager.hasWindowCache(window)) {
            GlfwCacheManager.getWindowCache(window).monitor().set(monitor);
        }
    }

    @Inject(method = "glfwCreateStandardCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateStandardCursor(int shape, CallbackInfoReturnable<Long> cir) {
        if (Ixeris.getConfig().useFlexibleThreading()) {
            return;
        }
        var cache = GlfwCacheManager.getGlobalCache().standardCursors();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.create(shape));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateStandardCursor(shape)));
        }
    }

    @Inject(method = "glfwDestroyCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwDestroyCursor(long cursor, CallbackInfo ci) {
        if (Ixeris.getConfig().useFlexibleThreading()) {
            return;
        }
        var cache = GlfwCacheManager.getGlobalCache().standardCursors();
        if (cache.isCacheEnabled() && cache.isCached(cursor)) {
            ci.cancel();
            cache.destroy(cursor);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwDestroyCursor(cursor));
        }
    }

    @Inject(method = "glfwGetWindowSize(J[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowSize(long window, int[] width, int[] height, CallbackInfo ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).windowSize();
            if (cache.isCacheEnabled() && check(width) && check(height)) {
                ci.cancel();
                cache.get(width, height);
                return;
            }
        }
        ci.cancel();
        MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowSize(window, width, height));
    }

    @Inject(method = "glfwGetWindowSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowSize(long window, IntBuffer width, IntBuffer height, CallbackInfo ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).windowSize();
            if (cache.isCacheEnabled() && check(width) && check(height)) {
                ci.cancel();
                cache.get(width, height);
                return;
            }
        }
        ci.cancel();
        MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowSize(window, width, height));
    }

    @Inject(method = "glfwGetFramebufferSize(J[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetFramebufferSize(long window, int[] width, int[] height, CallbackInfo ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var windowCache = GlfwCacheManager.getWindowCache(window);
            // https://github.com/decce6/Ixeris/issues/40
            var cache = PlatformHelper.isMacOs() ? windowCache.windowSize() : windowCache.framebufferSize();
            if (cache.isCacheEnabled() && check(width) && check(height)) {
                ci.cancel();
                cache.get(width, height);
                return;
            }
        }
        ci.cancel();
        if (PlatformHelper.isMacOs()) {
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowSize(window, width, height));
        }
        else {
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetFramebufferSize(window, width, height));
        }
    }

    @Inject(method = "glfwGetFramebufferSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetFramebufferSize(long window, IntBuffer width, IntBuffer height, CallbackInfo ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var windowCache = GlfwCacheManager.getWindowCache(window);
            // https://github.com/decce6/Ixeris/issues/40
            var cache = PlatformHelper.isMacOs() ? windowCache.windowSize() : windowCache.framebufferSize();
            if (cache.isCacheEnabled() && check(width) && check(height)) {
                ci.cancel();
                cache.get(width, height);
            }
            return;
        }
        ci.cancel();
        if (PlatformHelper.isMacOs()) {
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowSize(window, width, height));
        }
        else {
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetFramebufferSize(window, width, height));
        }
    }

    @Inject(method = "glfwGetWindowContentScale(J[F[F)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowContentScale(long window, float[] xscale, float[] yscale, CallbackInfo ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).contentScale();
            if (cache.isCacheEnabled() && check(xscale) && check(yscale)) {
                ci.cancel();
                cache.get(xscale, yscale);
                return;
            }
        }
        ci.cancel();
        MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowContentScale(window, xscale, yscale));
    }

    @Inject(method = "glfwGetWindowContentScale(JLjava/nio/FloatBuffer;Ljava/nio/FloatBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowContentScale(long window, FloatBuffer xscale, FloatBuffer yscale, CallbackInfo ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).contentScale();
            if (cache.isCacheEnabled() && check(xscale) && check(yscale)) {
                ci.cancel();
                cache.get(xscale, yscale);
                return;
            }
        }
        ci.cancel();
        MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowContentScale(window, xscale, yscale));
    }

    @Inject(method = "glfwGetWindowAttrib", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowAttrib(long window, int attrib, CallbackInfoReturnable<Integer> cir) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).attrib();
            if (cache.isCacheEnabled()) {
                cir.setReturnValue(cache.get(attrib));
                return;
            }
        }
        cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetWindowAttrib(window, attrib)));
    }

    @Inject(method = "glfwGetCursorPos(J[D[D)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetCursorPos(long window, double[] xpos, double[] ypos, CallbackInfo ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).cursorPos();
            if (cache.isCacheEnabled() && check(xpos) && check(ypos)) {
                ci.cancel();
                cache.get(xpos, ypos);
                return;
            }
        }
        ci.cancel();
        MainThreadDispatcher.runNow(() -> GLFW.glfwGetCursorPos(window, xpos, ypos));
    }

    @Inject(method = "glfwGetCursorPos(JLjava/nio/DoubleBuffer;Ljava/nio/DoubleBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetCursorPos(long window, DoubleBuffer xpos, DoubleBuffer ypos, CallbackInfo ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).cursorPos();
            if (cache.isCacheEnabled() && check(xpos) && check(ypos)) {
                ci.cancel();
                cache.get(xpos, ypos);
                return;
            }
        }
        ci.cancel();
        MainThreadDispatcher.runNow(() -> GLFW.glfwGetCursorPos(window, xpos, ypos));
    }
}
