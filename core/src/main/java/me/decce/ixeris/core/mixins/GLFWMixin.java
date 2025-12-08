package me.decce.ixeris.core.mixins;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
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
}
