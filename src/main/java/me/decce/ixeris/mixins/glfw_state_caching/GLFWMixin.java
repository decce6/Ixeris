package me.decce.ixeris.mixins.glfw_state_caching;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.glfw.state_caching.GlfwCacheManager;
import me.decce.ixeris.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwSetInputMode", at = @At("TAIL"))
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, CallbackInfo ci) {
        GlfwCacheManager.getWindowCache(window).inputMode().set(mode, value);
    }

    @Inject(method = "glfwGetInputMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetInputMode(long window, int mode, CallbackInfoReturnable<Integer> cir) {
        var cache = GlfwCacheManager.getWindowCache(window).inputMode();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.get(mode));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetInputMode(window, mode)));
        }
    }

    @Inject(method = "glfwGetKey", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKey(long window, int key, CallbackInfoReturnable<Integer> cir) {
        var cache = GlfwCacheManager.getWindowCache(window).keys();
        if (cache.isCacheEnabled()) {
            var ret = cache.get(key);
            if (ret == GLFW.GLFW_REPEAT) {
                ret = GLFW.GLFW_PRESS;
            }
            cir.setReturnValue(ret);
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetKey(window, key)));
        }
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
        var cache =GlfwCacheManager.getWindowCache(window).mouseButtons();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.get(button));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetMouseButton(window, button)));
        }
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
        var cache = GlfwCacheManager.getWindowCache(window).monitor();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.get());
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetWindowMonitor(window)));
        }
    }

    @Inject(method = "glfwSetWindowMonitor", at = @At("TAIL"))
    private static void ixeris$glfwSetWindowMonitor(long window, long monitor, int xpos, int ypos, int width, int height, int refreshRate, CallbackInfo ci) {
        GlfwCacheManager.getWindowCache(window).monitor().set(monitor);
    }

    @Inject(method = "glfwSetCursor", at = @At("TAIL"))
    private static void ixeris$glfwSetCursor(long window, long cursor, CallbackInfo ci) {
        GlfwCacheManager.getGlobalCache().standardCursors().onSet(window, cursor);
    }

    @Inject(method = "glfwCreateStandardCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateStandardCursor(int shape, CallbackInfoReturnable<Long> cir) {
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
        var cache = GlfwCacheManager.getWindowCache(window).windowSize();
        if (cache.isCacheEnabled()) {
            ci.cancel();
            cache.get(width, height);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowSize(window, width, height));
        }
    }

    @Inject(method = "glfwGetWindowSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowSize(long window, IntBuffer width, IntBuffer height, CallbackInfo ci) {
        var cache = GlfwCacheManager.getWindowCache(window).windowSize();
        if (cache.isCacheEnabled()) {
            ci.cancel();
            cache.get(width, height);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowSize(window, width, height));
        }
    }

    @Inject(method = "glfwGetFramebufferSize(J[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetFramebufferSize(long window, int[] width, int[] height, CallbackInfo ci) {
        var cache = GlfwCacheManager.getWindowCache(window).framebufferSize();
        if (cache.isCacheEnabled()) {
            ci.cancel();
            cache.get(width, height);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetFramebufferSize(window, width, height));
        }
    }

    @Inject(method = "glfwGetFramebufferSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetFramebufferSize(long window, IntBuffer width, IntBuffer height, CallbackInfo ci) {
        var cache = GlfwCacheManager.getWindowCache(window).framebufferSize();
        if (cache.isCacheEnabled()) {
            ci.cancel();
            cache.get(width, height);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetFramebufferSize(window, width, height));
        }
    }

    @Inject(method = "glfwGetWindowContentScale(J[F[F)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowContentScale(long window, float[] xscale, float[] yscale, CallbackInfo ci) {
        var cache = GlfwCacheManager.getWindowCache(window).contentScale();
        if (cache.isCacheEnabled()) {
            ci.cancel();
            cache.get(xscale, yscale);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowContentScale(window, xscale, yscale));
        }
    }

    @Inject(method = "glfwGetWindowContentScale(JLjava/nio/FloatBuffer;Ljava/nio/FloatBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowContentScale(long window, FloatBuffer xscale, FloatBuffer yscale, CallbackInfo ci) {
        var cache = GlfwCacheManager.getWindowCache(window).contentScale();
        if (cache.isCacheEnabled()) {
            ci.cancel();
            cache.get(xscale, yscale);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowContentScale(window, xscale, yscale));
        }
    }
}
