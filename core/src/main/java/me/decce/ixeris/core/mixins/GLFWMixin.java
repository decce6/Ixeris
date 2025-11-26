package me.decce.ixeris.core.mixins;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
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

    @Inject(method = "glfwSetInputMode", at = @At("TAIL"))
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, CallbackInfo ci) {
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
