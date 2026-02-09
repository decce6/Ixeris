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
    @Inject(method = { "glfwPollEvents", "glfwWaitEvents", "glfwWaitEventsTimeout" }, at = @At("HEAD"), cancellable = true)
    private static void ixeris$cancelDangerousEventPolling(CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            if (!Ixeris.inEarlyDisplay && !Ixeris.suppressEventPollingWarning) {
                Ixeris.LOGGER.warn("One of the GLFW event polling functions has been called on non-main thread. Consider reporting this to the issue tracker of Ixeris.");
                Thread.dumpStack();
                Ixeris.suppressEventPollingWarning = true;
            }
        }
    }

    @Inject(method = "glfwInit", at = @At("TAIL"))
    private static void ixeris$glfwInit(CallbackInfoReturnable<Boolean> cir) {
        Ixeris.glfwInitialized = true;
    }

    @Inject(method = "glfwTerminate", at = @At("TAIL"))
    private static void ixeris$glfwTerminate(CallbackInfo ci) {
        Ixeris.glfwInitialized = false;
    }
}
