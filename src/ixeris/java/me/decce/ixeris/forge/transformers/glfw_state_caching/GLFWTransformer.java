//? if forge {
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.glfw_state_caching;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.util.PlatformHelper;
import me.decce.ixeris.core.workarounds.RetinaWorkaround;
import org.lwjgl.glfw.GLFW;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static me.decce.ixeris.core.glfw.state_caching.util.BufferHelper.check;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInline @CInject(method = "glfwSetInputMode", target = @CTarget("TAIL"))
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, InjectionCallback ci) {
        if (GlfwCacheManager.hasWindowCache(window)) {
            GlfwCacheManager.getWindowCache(window).inputMode().set(mode, value);
        }
    }

    @CInline @CInject(method = "glfwGetInputMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetInputMode(long window, int mode, InjectionCallback cir) {
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
        cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetInputMode, window, mode)));
    }

    @CInline @CInject(method = "glfwGetKey", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKey(long window, int key, InjectionCallback cir) {
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
        cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetKey, window, key)));
    }

    @CInline @CInject(method = "glfwGetKeyName", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKeyName(int key, int scancode, InjectionCallback cir) {
        var cache = GlfwCacheManager.getGlobalCache().keyNames();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.get(key, scancode));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetKeyName, key, scancode)));
        }
    }

    @CInline @CInject(method = "glfwGetMouseButton", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMouseButton(long window, int button, InjectionCallback cir) {
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
        cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetMouseButton, window, button)));
    }

    @CInline @CInject(method = "glfwGetPrimaryMonitor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetPrimaryMonitor(InjectionCallback cir) {
        if (Ixeris.getConfig().useFlexibleThreading()) {
            return;
        }
        var cache = GlfwCacheManager.getGlobalCache().monitors();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.getPrimaryMonitor());
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(GLFW::glfwGetPrimaryMonitor));
        }
    }

    @CInline @CInject(method = "glfwGetWindowMonitor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowMonitor(long window, InjectionCallback cir) {
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
        cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetWindowMonitor, window)));
    }

    @CInline @CInject(method = "glfwSetWindowMonitor", target = @CTarget("TAIL"))
    private static void ixeris$glfwSetWindowMonitor(long window, long monitor, int xpos, int ypos, int width, int height, int refreshRate, InjectionCallback ci) {
        if (GlfwCacheManager.hasWindowCache(window)) {
            GlfwCacheManager.getWindowCache(window).monitor().set(monitor);
        }
    }

    @CInline @CInject(method = "glfwCreateStandardCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateStandardCursor(int shape, InjectionCallback cir) {
        if (Ixeris.getConfig().useFlexibleThreading()) {
            return;
        }
        var cache = GlfwCacheManager.getGlobalCache().standardCursors();
        if (cache.isCacheEnabled()) {
            cir.setReturnValue(cache.create(shape));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwCreateStandardCursor, shape)));
        }
    }

    @CInline @CInject(method = "glfwDestroyCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwDestroyCursor(long cursor, InjectionCallback ci) {
        if (Ixeris.getConfig().useFlexibleThreading()) {
            return;
        }
        var cache = GlfwCacheManager.getGlobalCache().standardCursors();
        if (cache.isCacheEnabled() && cache.isCached(cursor)) {
            ci.setCancelled(true);
            cache.destroy(cursor);
        }
        else if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwDestroyCursor, cursor));
        }
    }

    @CInline @CInject(method = "glfwGetWindowSize(J[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowSize(long window, int[] width, int[] height, InjectionCallback ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).windowSize();
            if (cache.isCacheEnabled() && check(width) && check(height)) {
                ci.setCancelled(true);
                cache.get(width, height);
                return;
            }
        }
        ci.setCancelled(true);
        MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetWindowSize, window, width, height));
    }

    @CInline @CInject(method = "glfwGetWindowSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowSize(long window, IntBuffer width, IntBuffer height, InjectionCallback ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).windowSize();
            if (cache.isCacheEnabled() && check(width) && check(height)) {
                ci.setCancelled(true);
                cache.get(width, height);
                return;
            }
        }
        ci.setCancelled(true);
        MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetWindowSize, window, width, height));
    }

    @CInline @CInject(method = "glfwGetFramebufferSize(J[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetFramebufferSize(long window, int[] width, int[] height, InjectionCallback ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).framebufferSize();
            if (cache.isCacheEnabled() && check(width) && check(height)) {
                ci.setCancelled(true);
                cache.get(width, height);
                ixeris$applyCocoaFramebufferSizeWorkaround(window, width, height);
                return;
            }
        }
        ci.setCancelled(true);
        MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetFramebufferSize, window, width, height));
        ixeris$applyCocoaFramebufferSizeWorkaround(window, width, height);
    }

    @CInline @CInject(method = "glfwGetFramebufferSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetFramebufferSize(long window, IntBuffer width, IntBuffer height, InjectionCallback ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).framebufferSize();
            if (cache.isCacheEnabled() && check(width) && check(height)) {
                ci.setCancelled(true);
                cache.get(width, height);
                ixeris$applyCocoaFramebufferSizeWorkaround(window, width, height);
            }
            return;
        }
        ci.setCancelled(true);
        MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetFramebufferSize, window, width, height));
        ixeris$applyCocoaFramebufferSizeWorkaround(window, width, height);
    }

    @CInline
    private static void ixeris$applyCocoaFramebufferSizeWorkaround(long window, IntBuffer width, IntBuffer height) {
        if (PlatformHelper.isMacOs()) {
            var retina = RetinaWorkaround.get(window);
            if (retina.isPresent() && retina.get() == GLFW.GLFW_FALSE) {
                width.put(0, width.get(0) * 2);
                height.put(0, height.get(0) * 2);
            }
        }
    }

    @CInline
    private static void ixeris$applyCocoaFramebufferSizeWorkaround(long window, int[] width, int[] height) {
        if (PlatformHelper.isMacOs()) {
            var retina = RetinaWorkaround.get(window);
            if (retina.isPresent() && retina.get() == GLFW.GLFW_FALSE) {
                width[0] *= 2;
                height[0] *= 2;
            }
        }
    }

    @CInline @CInject(method = "glfwGetWindowContentScale(J[F[F)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowContentScale(long window, float[] xscale, float[] yscale, InjectionCallback ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).contentScale();
            if (cache.isCacheEnabled() && check(xscale) && check(yscale)) {
                ci.setCancelled(true);
                cache.get(xscale, yscale);
                return;
            }
        }
        ci.setCancelled(true);
        MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetWindowContentScale, window, xscale, yscale));
    }

    @CInline @CInject(method = "glfwGetWindowContentScale(JLjava/nio/FloatBuffer;Ljava/nio/FloatBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowContentScale(long window, FloatBuffer xscale, FloatBuffer yscale, InjectionCallback ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).contentScale();
            if (cache.isCacheEnabled() && check(xscale) && check(yscale)) {
                ci.setCancelled(true);
                cache.get(xscale, yscale);
                return;
            }
        }
        ci.setCancelled(true);
        MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetWindowContentScale, window, xscale, yscale));
    }

    @CInline @CInject(method = "glfwGetWindowAttrib", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowAttrib(long window, int attrib, InjectionCallback cir) {
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
        cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetWindowAttrib, window, attrib)));
    }

    @CInline @CInject(method = "glfwGetCursorPos(J[D[D)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetCursorPos(long window, double[] xpos, double[] ypos, InjectionCallback ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).cursorPos();
            if (cache.isCacheEnabled() && check(xpos) && check(ypos)) {
                ci.setCancelled(true);
                cache.get(xpos, ypos);
                return;
            }
        }
        ci.setCancelled(true);
        MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetCursorPos, window, xpos, ypos));
    }

    @CInline @CInject(method = "glfwGetCursorPos(JLjava/nio/DoubleBuffer;Ljava/nio/DoubleBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetCursorPos(long window, DoubleBuffer xpos, DoubleBuffer ypos, InjectionCallback ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        if (GlfwCacheManager.hasWindowCache(window)) {
            var cache = GlfwCacheManager.getWindowCache(window).cursorPos();
            if (cache.isCacheEnabled() && check(xpos) && check(ypos)) {
                ci.setCancelled(true);
                cache.get(xpos, ypos);
                return;
            }
        }
        ci.setCancelled(true);
        MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetCursorPos, window, xpos, ypos));
    }
}

*///?}