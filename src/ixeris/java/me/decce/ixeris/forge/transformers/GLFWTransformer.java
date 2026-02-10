//? if forge {
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInline @CInject(method = { "glfwPollEvents", "glfwWaitEvents", "glfwWaitEventsTimeout" }, target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$cancelDangerousEventPolling(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            if (!Ixeris.inEarlyDisplay && !Ixeris.suppressEventPollingWarning) {
                Ixeris.LOGGER.warn("One of the GLFW event polling functions has been called on non-main thread. Consider reporting this to the issue tracker of Ixeris.");
                Thread.dumpStack();
                Ixeris.suppressEventPollingWarning = true;
            }
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
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwDestroyWindow, window));
        }
    }
}

*///?}