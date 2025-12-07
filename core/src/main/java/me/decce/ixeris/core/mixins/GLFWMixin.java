package me.decce.ixeris.core.mixins;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import me.decce.ixeris.core.workarounds.RetinaWorkaround;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.ByteBuffer;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Unique
    private static int ixeris$cocoaRetinaFramebuffer = GLFW.GLFW_TRUE;

    @Inject(method = "glfwInit", at = @At("TAIL"))
    private static void ixeris$glfwInit(CallbackInfoReturnable<Boolean> cir) {
        Ixeris.glfwInitialized = true;
    }

    @Inject(method = "glfwTerminate", at = @At("TAIL"))
    private static void ixeris$glfwTerminate(CallbackInfo ci) {
        Ixeris.glfwInitialized = false;
    }

    @Inject(method = { "glfwPollEvents", "glfwWaitEvents", "glfwWaitEventsTimeout" }, at = @At("HEAD"), cancellable = true)
    private static void ixeris$cancelDangerousEventPolling(CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
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

    @Inject(method = "glfwSetCursorPos", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursorPos(long window, double xpos, double ypos, CallbackInfo ci) {
        // Supposed to be in the glfw_threading mixin, but merged here since ClassTransform does not support setting order for injectors
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetCursorPos(window, xpos, ypos));
            return;
        }
        if (window == Ixeris.accessor.getMinecraftWindow()) {
            RenderThreadDispatcher.suppressCursorPosCallbacks(true);
            RenderThreadDispatcher.clearQueuedCursorPosCallbacks();
        }
    }

    @Inject(method = "glfwSetCursorPos", at = @At("TAIL"))
    private static void ixeris$glfwSetCursorPos$tail(long window, double xpos, double ypos, CallbackInfo ci) {
        if (window == Ixeris.accessor.getMinecraftWindow()) {
            Ixeris.accessor.setIgnoreFirstMouseMove();
            RenderThreadDispatcher.suppressCursorPosCallbacks(false);
        }
    }

    @Inject(method = "glfwDefaultWindowHints", at = @At("TAIL"), cancellable = true)
    private static void ixeris$glfwDefaultWindowHints(CallbackInfo ci) {
        ixeris$cocoaRetinaFramebuffer = GLFW.GLFW_TRUE;
    }

    @Inject(method = "glfwWindowHint", at = @At("TAIL"), cancellable = true)
    private static void ixeris$glfwWindowHint(int hint, int value, CallbackInfo ci) {
        if (hint == GLFW.GLFW_COCOA_RETINA_FRAMEBUFFER) {
            ixeris$cocoaRetinaFramebuffer = value;
        }
    }

    @Inject(method = "glfwCreateWindow(IILjava/lang/CharSequence;JJ)J", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateWindow(int width, int height, CharSequence title, long monitor, long share, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateWindow(width, height, title, monitor, share)));
        }
        RetinaWorkaround.set(cir.getReturnValue(), ixeris$cocoaRetinaFramebuffer);
    }

    @Inject(method = "glfwCreateWindow(IILjava/nio/ByteBuffer;JJ)J", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateWindow(int width, int height, ByteBuffer title, long monitor, long share, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateWindow(width, height, title, monitor, share)));
        }
        RetinaWorkaround.set(cir.getReturnValue(), ixeris$cocoaRetinaFramebuffer);
    }
}
