package me.decce.ixeris.mixins;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Unique
    private static boolean ixeris$suppressLogging;

    @Inject(method = "glfwInit", at = @At("TAIL"))
    private static void ixeris$glfwInit(CallbackInfoReturnable<Boolean> cir) {
        Ixeris.glfwInitialized = true;
    }

    @Inject(method = "glfwTerminate", at = @At("TAIL"))
    private static void ixeris$glfwTerminate(CallbackInfo ci) {
        Ixeris.glfwInitialized = false;
    }

    @Inject(method = { "glfwPollEvents", "glfwWaitEvents", "glfwWaitEventsTimeout" }, at = @At("HEAD"), cancellable = true, order = 2000)
    private static void ixeris$cancelDangerousEventPolling(CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            if (!ixeris$suppressLogging) {
                Ixeris.LOGGER.warn("One of the GLFW event polling functions has been called on non-main thread.");
                Thread.dumpStack();
                ixeris$suppressLogging = true;
            }
        }
    }
}
