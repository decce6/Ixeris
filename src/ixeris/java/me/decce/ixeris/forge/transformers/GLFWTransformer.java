//? if forge { 
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.PollingException;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import java.util.concurrent.locks.LockSupport;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInline @CInject(method = "glfwWaitEventsTimeout", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$waitEventsTimeout(double timeout, InjectionCallback ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        ci.setCancelled(true);
        if (!Ixeris.inEarlyDisplay && Ixeris.getConfig().shouldLogPollingCalls()) {
            Ixeris.LOGGER.warn("", new PollingException());
        }
        if (Ixeris.accessor.isOnRenderThread() && timeout <= 1d) {
            LockSupport.parkNanos((long) (timeout * 1_000_000_000));
        }
    }

    @CInline @CInject(method = { "glfwPollEvents", "glfwWaitEvents" }, target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$pollOrWaitEvents(InjectionCallback ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        ci.setCancelled(true);
        MainThreadDispatcher.requestPollEvents();
        if (!Ixeris.inEarlyDisplay && Ixeris.getConfig().shouldLogPollingCalls()) {
            Ixeris.LOGGER.warn("", new PollingException());
        }
    }

    @CInline @CInject(method = "glfwInit", target = @CTarget("TAIL"))
    private static void ixeris$glfwInit(InjectionCallback cir) {
        Ixeris.glfwInitialized = true;
    }

    @CInline @CInject(method = "glfwTerminate", target = @CTarget("TAIL"))
    private static void ixeris$glfwTerminate(InjectionCallback ci) {
        Ixeris.glfwInitialized = false;
    }

    @CInline @CInject(method = "glfwDestroyWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwDestroyWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);

            // Release the context if it is on the current thread
            if (window == GLFW.glfwGetCurrentContext()) {
                GLFW.glfwMakeContextCurrent(0L);
            }

            GlfwCacheManager.destroyWindowCache(window);

            MainThreadDispatcher.run(makeRunnable(GLFW::glfwDestroyWindow, window));
        }
    }

    @CInline @CInject(method = "glfwSetInputMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetInputMode, window, mode, value));
            return;
        }
        if (Ixeris.getConfig().isBufferedRawMouse()) {
            if (mode == GLFW.GLFW_CURSOR) {
                if (value == GLFW.GLFW_CURSOR_DISABLED) {
                    Ixeris.input().grab(window);
                }
                else {
                    Ixeris.input().release(window);
                }
            }
            if (mode == GLFW.GLFW_RAW_MOUSE_MOTION && Ixeris.input().shouldHijackSettingRawInput()) {
                Ixeris.input().setRawInput(value == GLFW.GLFW_TRUE);
                ci.setCancelled(true);
            }
        }
    }
}

*///? }