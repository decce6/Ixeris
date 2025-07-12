package me.decce.ixeris.mixins;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.glfw.PlatformHelper;
import me.decce.ixeris.threading.MainThreadDispatcher;
import net.minecraft.client.Minecraft;
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
        // Polling events on non-main threads *is* legal on Linux, either X11 or Wayland
        if (!Ixeris.isOnMainThread() && !PlatformHelper.isLinux()) {
            ci.cancel();
            if (!ixeris$suppressLogging) {
                Ixeris.LOGGER.warn("One of the GLFW event polling functions has been called on non-main thread. Consider reporting this to the issue tracker of Ixeris.");
                Thread.dumpStack();
                ixeris$suppressLogging = true;
            }
        }
    }

    @Inject(method = "glfwSetCursorPos", at = @At("TAIL"))
    private static void ixeris$glfwSetCursorPos(long window, double xpos, double ypos, CallbackInfo ci) {
        if (window == Minecraft.getInstance().getWindow().getWindow()) {
            MainThreadDispatcher.clearQueuedCursorPosCallbacks();
            Minecraft.getInstance().mouseHandler.setIgnoreFirstMove();
        }
    }

    @Inject(method = "glfwSetInputMode", at = @At("TAIL"))
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, CallbackInfo ci) {
        if (window == Minecraft.getInstance().getWindow().getWindow() && mode == GLFW.GLFW_CURSOR) {
            if (value == GLFW.GLFW_CURSOR_NORMAL) { // release mouse
                Ixeris.mouseGrabbed = false;
            }
            else if (value == GLFW.GLFW_CURSOR_DISABLED) { // grab mouse
                Ixeris.mouseGrabbed = true;
            }
        }
    }
}
