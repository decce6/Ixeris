//? if forge {
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.glfw_threading_334;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import java.nio.IntBuffer;

// This mixins houses functions that are added in LWJGL 3.3.4.
import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInline @CInject(method = "glfwGetPreeditCursorRectangle(J[I[I[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetPreeditCursorRectangle(long window, int[] x, int[] y, int[] w, int[] h, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetPreeditCursorRectangle, window, x, y, w, h));
        }
    }

    @CInline @CInject(method = "glfwGetPreeditCursorRectangle(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetPreeditCursorRectangle(long window, IntBuffer x, IntBuffer y, IntBuffer w, IntBuffer h, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetPreeditCursorRectangle, window, x, y, w, h));
        }
    }

    @CInline @CInject(method = "glfwSetPreeditCursorRectangle", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetPreeditCursorRectangle(long window, int x, int y, int w, int h, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetPreeditCursorRectangle, window, x, y, w, h));
        }
    }

    @CInline @CInject(method = "glfwResetPreeditText", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwResetPreeditText(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwResetPreeditText, window));
        }
    }

    @CInline @CInject(method = "glfwGetPreeditCandidate", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetPreeditCandidate(long window, int index, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetPreeditCandidate, window, index)));
        }
    }
}
*///?}