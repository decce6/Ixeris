/*
Auto-translated from Mixin. See the generator directory in project root.
*/

package me.decce.ixeris.forge.core.transformers;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInject(method = "glfwInit", target = @CTarget("TAIL"))
    private static void ixeris$glfwInit(InjectionCallback cir) {
        Ixeris.glfwInitialized = true;
    }

    @CInject(method = "glfwTerminate", target = @CTarget("TAIL"))
    private static void ixeris$glfwTerminate(InjectionCallback ci) {
        Ixeris.glfwInitialized = false;
    }

    @CInject(method = { "glfwPollEvents", "glfwWaitEvents", "glfwWaitEventsTimeout" }, target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$cancelDangerousEventPolling(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            if (!Ixeris.suppressEventPollingWarning) {
                Ixeris.LOGGER.warn("One of the GLFW event polling functions has been called on non-main thread. Consider reporting this to the issue tracker of Ixeris.");
                Thread.dumpStack();
                Ixeris.suppressEventPollingWarning = true;
            }
        }
    }



    @CInject(method = "glfwSetCursorPos", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursorPos(long window, double xpos, double ypos, InjectionCallback ci) {
        // Supposed to be in the glfw_threading mixin, but merged here since ClassTransform does not support setting order for injectors
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetCursorPos(window, xpos, ypos));
            return;
        }
        if (window == Ixeris.accessor.getMinecraftWindow()) {
            RenderThreadDispatcher.suppressCursorPosCallbacks(true);
            RenderThreadDispatcher.clearQueuedCursorPosCallbacks();
        }
    }

    @CInject(method = "glfwSetCursorPos", target = @CTarget("TAIL"))
    private static void ixeris$glfwSetCursorPos$tail(long window, double xpos, double ypos, InjectionCallback ci) {
        if (window == Ixeris.accessor.getMinecraftWindow()) {
            Ixeris.accessor.setIgnoreFirstMouseMove();
            RenderThreadDispatcher.suppressCursorPosCallbacks(false);
        }
    }

    @CInject(method = "glfwSetInputMode", target = @CTarget("TAIL"))
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, InjectionCallback ci) {
        if (window == Ixeris.accessor.getMinecraftWindow() && mode == GLFW.GLFW_CURSOR) {
            if (value == GLFW.GLFW_CURSOR_NORMAL) { // release mouse
                Ixeris.mouseGrabbed = false;
            }
            else if (value == GLFW.GLFW_CURSOR_DISABLED) { // grab mouse
                Ixeris.mouseGrabbed = true;
            }
        }
    }
}
