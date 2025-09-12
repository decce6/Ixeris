/*
Auto-translated from Mixin. See the generator directory in project root.
*/

package me.decce.ixeris.neoforge.core.transformers.glfw_state_caching;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static me.decce.ixeris.core.glfw.state_caching.util.BufferHelper.check;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInject(method = "glfwSetInputMode", target = @CTarget("TAIL"))
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, InjectionCallback ci) {
        GlfwCacheManager.getWindowCache(window).inputMode().set(mode, value);
    }

    @CInject(method = "glfwGetInputMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetInputMode(long window, int mode, InjectionCallback cir) {
        var cache = GlfwCacheManager.getWindowCache(window).inputMode();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.get(mode));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetInputMode(window, mode)));
        }
    }

    @CInject(method = "glfwGetKey", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKey(long window, int key, InjectionCallback cir) {
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

    @CInject(method = "glfwGetKeyName", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKeyName(int key, int scancode, InjectionCallback cir) {
        var cache = GlfwCacheManager.getGlobalCache().keyNames();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.get(key, scancode));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetKeyName(key, scancode)));
        }
    }

    @CInject(method = "glfwGetMouseButton", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMouseButton(long window, int button, InjectionCallback cir) {
        var cache =GlfwCacheManager.getWindowCache(window).mouseButtons();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.get(button));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetMouseButton(window, button)));
        }
    }

    @CInject(method = "glfwGetPrimaryMonitor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetPrimaryMonitor(InjectionCallback cir) {
        var cache = GlfwCacheManager.getGlobalCache().monitors();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.getPrimaryMonitor());
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(GLFW::glfwGetPrimaryMonitor));
        }
    }

    @CInject(method = "glfwGetWindowMonitor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowMonitor(long window, InjectionCallback cir) {
        var cache = GlfwCacheManager.getWindowCache(window).monitor();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.get());
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetWindowMonitor(window)));
        }
    }

    @CInject(method = "glfwSetWindowMonitor", target = @CTarget("TAIL"))
    private static void ixeris$glfwSetWindowMonitor(long window, long monitor, int xpos, int ypos, int width, int height, int refreshRate, InjectionCallback ci) {
        GlfwCacheManager.getWindowCache(window).monitor().set(monitor);
    }

    @CInject(method = "glfwCreateStandardCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateStandardCursor(int shape, InjectionCallback cir) {
        var cache = GlfwCacheManager.getGlobalCache().standardCursors();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.create(shape));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateStandardCursor(shape)));
        }
    }

    @CInject(method = "glfwDestroyCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwDestroyCursor(long cursor, InjectionCallback ci) {
        var cache = GlfwCacheManager.getGlobalCache().standardCursors();
        if (cache.isCacheEnabled() && cache.isCached(cursor)) {
            ci.setCancelled(true);
            cache.destroy(cursor);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwDestroyCursor(cursor));
        }
    }

    @CInject(method = "glfwGetWindowSize(J[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowSize(long window, int[] width, int[] height, InjectionCallback ci) {
        var cache = GlfwCacheManager.getWindowCache(window).windowSize();
        if (cache.isCacheEnabled() && check(width) && check(height)) {
            ci.setCancelled(true);
            cache.get(width, height);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowSize(window, width, height));
        }
    }

    @CInject(method = "glfwGetWindowSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowSize(long window, IntBuffer width, IntBuffer height, InjectionCallback ci) {
        var cache = GlfwCacheManager.getWindowCache(window).windowSize();
        if (cache.isCacheEnabled() && check(width) && check(height)) {
            ci.setCancelled(true);
            cache.get(width, height);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowSize(window, width, height));
        }
    }

    @CInject(method = "glfwGetFramebufferSize(J[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetFramebufferSize(long window, int[] width, int[] height, InjectionCallback ci) {
        var cache = GlfwCacheManager.getWindowCache(window).framebufferSize();
        if (cache.isCacheEnabled() && check(width) && check(height)) {
            ci.setCancelled(true);
            cache.get(width, height);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetFramebufferSize(window, width, height));
        }
    }

    @CInject(method = "glfwGetFramebufferSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetFramebufferSize(long window, IntBuffer width, IntBuffer height, InjectionCallback ci) {
        var cache = GlfwCacheManager.getWindowCache(window).framebufferSize();
        if (cache.isCacheEnabled() && check(width) && check(height)) {
            ci.setCancelled(true);
            cache.get(width, height);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetFramebufferSize(window, width, height));
        }
    }

    @CInject(method = "glfwGetWindowContentScale(J[F[F)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowContentScale(long window, float[] xscale, float[] yscale, InjectionCallback ci) {
        var cache = GlfwCacheManager.getWindowCache(window).contentScale();
        if (cache.isCacheEnabled() && check(xscale) && check(yscale)) {
            ci.setCancelled(true);
            cache.get(xscale, yscale);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowContentScale(window, xscale, yscale));
        }
    }

    @CInject(method = "glfwGetWindowContentScale(JLjava/nio/FloatBuffer;Ljava/nio/FloatBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowContentScale(long window, FloatBuffer xscale, FloatBuffer yscale, InjectionCallback ci) {
        var cache = GlfwCacheManager.getWindowCache(window).contentScale();
        if (cache.isCacheEnabled() && check(xscale) && check(yscale)) {
            ci.setCancelled(true);
            cache.get(xscale, yscale);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowContentScale(window, xscale, yscale));
        }
    }

    @CInject(method = "glfwGetWindowAttrib", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowAttrib(long window, int attrib, InjectionCallback cir) {
        var cache = GlfwCacheManager.getWindowCache(window).attrib();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.get(attrib));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetWindowAttrib(window, attrib)));
        }
    }
}
