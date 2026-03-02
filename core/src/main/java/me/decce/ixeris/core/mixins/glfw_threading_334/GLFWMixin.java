package me.decce.ixeris.core.mixins.glfw_threading_334;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.IntBuffer;

// This mixins houses functions that are added in LWJGL 3.3.4.
@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwGetPreeditCursorRectangle(J[I[I[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetPreeditCursorRectangle(long window, int[] x, int[] y, int[] w, int[] h, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetPreeditCursorRectangle(window, x, y, w, h));
        }
    }

    @Inject(method = "glfwGetPreeditCursorRectangle(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetPreeditCursorRectangle(long window, IntBuffer x, IntBuffer y, IntBuffer w, IntBuffer h, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetPreeditCursorRectangle(window, x, y, w, h));
        }
    }

    @Inject(method = "glfwSetPreeditCursorRectangle", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetPreeditCursorRectangle(long window, int x, int y, int w, int h, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetPreeditCursorRectangle(window, x, y, w, h));
        }
    }

    @Inject(method = "glfwResetPreeditText", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwResetPreeditText(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwResetPreeditText(window));
        }
    }

    @Inject(method = "glfwGetPreeditCandidate", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetPreeditCandidate(long window, int index, CallbackInfoReturnable<IntBuffer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetPreeditCandidate(window, index)));
        }
    }
}