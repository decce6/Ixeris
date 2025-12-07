//? if forge {
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import me.decce.ixeris.core.util.PlatformHelper;
import me.decce.ixeris.core.workarounds.RetinaWorkaround;
import org.lwjgl.glfw.GLFW;
import net.lenni0451.classtransform.annotations.CTransformer;

import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import java.nio.ByteBuffer;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    
    private static int ixeris$cocoaRetinaFramebuffer = GLFW.GLFW_TRUE;

    @CInline @CInject(method = "glfwInit", target = @CTarget("TAIL"))
    private static void ixeris$glfwInit(InjectionCallback cir) {
        Ixeris.glfwInitialized = true;
    }

    @CInline @CInject(method = "glfwTerminate", target = @CTarget("TAIL"))
    private static void ixeris$glfwTerminate(InjectionCallback ci) {
        Ixeris.glfwInitialized = false;
    }

    @CInline @CInject(method = { "glfwPollEvents", "glfwWaitEvents", "glfwWaitEventsTimeout" }, target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$cancelDangerousEventPolling(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            if (Ixeris.inEarlyDisplay) {
                // Allow resizing window, etc. in early display window
                Ixeris.accessor.replayRenderThreadQueue();
            }
            else if (!Ixeris.suppressEventPollingWarning) {
                Ixeris.LOGGER.warn("One of the GLFW event polling functions has been called on non-main thread. Consider reporting this to the issue tracker of Ixeris.");
                Thread.dumpStack();
                Ixeris.suppressEventPollingWarning = true;
            }
        }
    }

    @CInline @CInject(method = "glfwSetCursorPos", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursorPos(long window, double xpos, double ypos, InjectionCallback ci) {
        // Supposed to be in the glfw_threading mixin, but merged here since ClassTransform does not support setting order for injectors
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetCursorPos, window, xpos, ypos));
            return;
        }
        if (window == Ixeris.accessor.getMinecraftWindow()) {
            RenderThreadDispatcher.suppressCursorPosCallbacks(true);
            RenderThreadDispatcher.clearQueuedCursorPosCallbacks();
        }
    }

    @CInline @CInject(method = "glfwSetCursorPos", target = @CTarget("TAIL"))
    private static void ixeris$glfwSetCursorPos$tail(long window, double xpos, double ypos, InjectionCallback ci) {
        if (window == Ixeris.accessor.getMinecraftWindow()) {
            Ixeris.accessor.setIgnoreFirstMouseMove();
            RenderThreadDispatcher.suppressCursorPosCallbacks(false);
        }
    }

    @CInline @CInject(method = "glfwDefaultWindowHints", target = @CTarget("TAIL"), cancellable = true)
    private static void ixeris$glfwDefaultWindowHints(InjectionCallback ci) {
        ixeris$cocoaRetinaFramebuffer = GLFW.GLFW_TRUE;
    }

    @CInline @CInject(method = "glfwWindowHint", target = @CTarget("TAIL"), cancellable = true)
    private static void ixeris$glfwWindowHint(int hint, int value, InjectionCallback ci) {
        if (hint == GLFW.GLFW_COCOA_RETINA_FRAMEBUFFER) {
            ixeris$cocoaRetinaFramebuffer = value;
        }
    }

    @CInline @CInject(method = "glfwCreateWindow(IILjava/lang/CharSequence;JJ)J", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateWindow(int width, int height, CharSequence title, long monitor, long share, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwCreateWindow, width, height, title, monitor, share)));
        }
        if (PlatformHelper.isMacOs()) {
            RetinaWorkaround.set((Long) cir.getReturnValue(), ixeris$cocoaRetinaFramebuffer);
        }
    }

    @CInline @CInject(method = "glfwCreateWindow(IILjava/nio/ByteBuffer;JJ)J", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateWindow(int width, int height, ByteBuffer title, long monitor, long share, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwCreateWindow, width, height, title, monitor, share)));
        }
        if (PlatformHelper.isMacOs()) {
            RetinaWorkaround.set((Long) cir.getReturnValue(), ixeris$cocoaRetinaFramebuffer);
        }
    }
}

*///?}