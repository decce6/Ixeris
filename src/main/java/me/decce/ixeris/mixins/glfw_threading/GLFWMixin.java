/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.mixins.glfw_threading;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.threading.MainThreadDispatcher;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWAllocator;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.glfw.GLFWGammaRamp;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwCreateCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateCursor(GLFWImage image, int xhot, int yhot, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateCursor(image, xhot, yhot)));
        }
    }

    @Inject(method = "glfwCreateStandardCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateStandardCursor(int shape, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateStandardCursor(shape)));
        }
    }

    @Inject(method = "glfwCreateWindow(IILjava/lang/CharSequence;JJ)J", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateWindow(int width, int height, CharSequence title, long monitor, long share, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateWindow(width, height, title, monitor, share)));
        }
    }

    @Inject(method = "glfwCreateWindow(IILjava/nio/ByteBuffer;JJ)J", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateWindow(int width, int height, ByteBuffer title, long monitor, long share, CallbackInfoReturnable<Long> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateWindow(width, height, title, monitor, share)));
        }
    }

    @Inject(method = "glfwDefaultWindowHints", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwDefaultWindowHints(CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwDefaultWindowHints());
        }
    }

    @Inject(method = "glfwDestroyCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwDestroyCursor(long cursor, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwDestroyCursor(cursor));
        }
    }

    @Inject(method = "glfwDestroyWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwDestroyWindow(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwDestroyWindow(window));
        }
    }

    @Inject(method = "glfwFocusWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwFocusWindow(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwFocusWindow(window));
        }
    }

    @Inject(method = "glfwGetClipboardString", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetClipboardString(long window, CallbackInfoReturnable<String> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetClipboardString(window)));
        }
    }

    @Inject(method = "glfwGetCursorPos(J[D[D)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetCursorPos(long window, double[] xpos, double[] ypos, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetCursorPos(window, xpos, ypos));
        }
    }

    @Inject(method = "glfwGetCursorPos(JLjava/nio/DoubleBuffer;Ljava/nio/DoubleBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetCursorPos(long window, DoubleBuffer xpos, DoubleBuffer ypos, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetCursorPos(window, xpos, ypos));
        }
    }

    @Inject(method = "glfwGetFramebufferSize(J[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetFramebufferSize(long window, int[] width, int[] height, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetFramebufferSize(window, width, height));
        }
    }

    @Inject(method = "glfwGetFramebufferSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetFramebufferSize(long window, IntBuffer width, IntBuffer height, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetFramebufferSize(window, width, height));
        }
    }

    @Inject(method = "glfwGetGamepadName", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetGamepadName(int jid, CallbackInfoReturnable<String> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetGamepadName(jid)));
        }
    }

    @Inject(method = "glfwGetGamepadState", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetGamepadState(int jid, GLFWGamepadState state, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetGamepadState(jid, state)));
        }
    }

    @Inject(method = "glfwGetGammaRamp", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetGammaRamp(long monitor, CallbackInfoReturnable<GLFWGammaRamp> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetGammaRamp(monitor)));
        }
    }

    @Inject(method = "glfwGetJoystickAxes", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickAxes(int jid, CallbackInfoReturnable<FloatBuffer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetJoystickAxes(jid)));
        }
    }

    @Inject(method = "glfwGetJoystickButtons", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickButtons(int jid, CallbackInfoReturnable<ByteBuffer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetJoystickButtons(jid)));
        }
    }

    @Inject(method = "glfwGetJoystickGUID", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickGUID(int jid, CallbackInfoReturnable<String> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetJoystickGUID(jid)));
        }
    }

    @Inject(method = "glfwGetJoystickHats", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickHats(int jid, CallbackInfoReturnable<ByteBuffer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetJoystickHats(jid)));
        }
    }

    @Inject(method = "glfwGetJoystickName", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickName(int jid, CallbackInfoReturnable<String> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetJoystickName(jid)));
        }
    }

    @Inject(method = "glfwGetMonitorContentScale(J[F[F)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorContentScale(long monitor, float[] xscale, float[] yscale, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorContentScale(monitor, xscale, yscale));
        }
    }

    @Inject(method = "glfwGetMonitorContentScale(JLjava/nio/FloatBuffer;Ljava/nio/FloatBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorContentScale(long monitor, FloatBuffer xscale, FloatBuffer yscale, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorContentScale(monitor, xscale, yscale));
        }
    }

    @Inject(method = "glfwGetMonitorName", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorName(long monitor, CallbackInfoReturnable<String> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetMonitorName(monitor)));
        }
    }

    @Inject(method = "glfwGetMonitorPhysicalSize(J[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPhysicalSize(long monitor, int[] widthMM, int[] heightMM, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorPhysicalSize(monitor, widthMM, heightMM));
        }
    }

    @Inject(method = "glfwGetMonitorPhysicalSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPhysicalSize(long monitor, IntBuffer widthMM, IntBuffer heightMM, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorPhysicalSize(monitor, widthMM, heightMM));
        }
    }

    @Inject(method = "glfwGetMonitorPos(J[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPos(long monitor, int[] xpos, int[] ypos, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorPos(monitor, xpos, ypos));
        }
    }

    @Inject(method = "glfwGetMonitorPos(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPos(long monitor, IntBuffer xpos, IntBuffer ypos, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorPos(monitor, xpos, ypos));
        }
    }

    @Inject(method = "glfwGetMonitors", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitors(CallbackInfoReturnable<PointerBuffer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetMonitors()));
        }
    }

    @Inject(method = "glfwGetMonitorWorkarea(J[I[I[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorWorkarea(long monitor, int[] xpos, int[] ypos, int[] width, int[] height, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorWorkarea(monitor, xpos, ypos, width, height));
        }
    }

    @Inject(method = "glfwGetMonitorWorkarea(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorWorkarea(long monitor, IntBuffer xpos, IntBuffer ypos, IntBuffer width, IntBuffer height, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorWorkarea(monitor, xpos, ypos, width, height));
        }
    }

    @Inject(method = "glfwGetVideoMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetVideoMode(long monitor, CallbackInfoReturnable<GLFWVidMode> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetVideoMode(monitor)));
        }
    }

    @Inject(method = "glfwGetVideoModes", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetVideoModes(long monitor, CallbackInfoReturnable<GLFWVidMode.Buffer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetVideoModes(monitor)));
        }
    }

    @Inject(method = "glfwGetWindowAttrib", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowAttrib(long window, int attrib, CallbackInfoReturnable<Integer> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetWindowAttrib(window, attrib)));
        }
    }

    @Inject(method = "glfwGetWindowContentScale(J[F[F)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowContentScale(long window, float[] xscale, float[] yscale, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowContentScale(window, xscale, yscale));
        }
    }

    @Inject(method = "glfwGetWindowContentScale(JLjava/nio/FloatBuffer;Ljava/nio/FloatBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowContentScale(long window, FloatBuffer xscale, FloatBuffer yscale, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowContentScale(window, xscale, yscale));
        }
    }

    @Inject(method = "glfwGetWindowFrameSize(J[I[I[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowFrameSize(long window, int[] left, int[] top, int[] right, int[] bottom, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowFrameSize(window, left, top, right, bottom));
        }
    }

    @Inject(method = "glfwGetWindowFrameSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowFrameSize(long window, IntBuffer left, IntBuffer top, IntBuffer right, IntBuffer bottom, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowFrameSize(window, left, top, right, bottom));
        }
    }

    @Inject(method = "glfwGetWindowOpacity", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowOpacity(long window, CallbackInfoReturnable<Float> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetWindowOpacity(window)));
        }
    }

    @Inject(method = "glfwGetWindowPos(J[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowPos(long window, int[] xpos, int[] ypos, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowPos(window, xpos, ypos));
        }
    }

    @Inject(method = "glfwGetWindowPos(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowPos(long window, IntBuffer xpos, IntBuffer ypos, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowPos(window, xpos, ypos));
        }
    }

    @Inject(method = "glfwGetWindowSize(J[I[I)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowSize(long window, int[] width, int[] height, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowSize(window, width, height));
        }
    }

    @Inject(method = "glfwGetWindowSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowSize(long window, IntBuffer width, IntBuffer height, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowSize(window, width, height));
        }
    }

    @Inject(method = "glfwHideWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwHideWindow(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwHideWindow(window));
        }
    }

    @Inject(method = "glfwIconifyWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwIconifyWindow(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwIconifyWindow(window));
        }
    }

    @Inject(method = "glfwInit", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwInit(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwInit()));
        }
    }

    @Inject(method = "glfwInitAllocator", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwInitAllocator(GLFWAllocator allocator, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwInitAllocator(allocator));
        }
    }

    @Inject(method = "glfwInitHint", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwInitHint(int hint, int value, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwInitHint(hint, value));
        }
    }

    @Inject(method = "glfwJoystickIsGamepad", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwJoystickIsGamepad(int jid, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwJoystickIsGamepad(jid)));
        }
    }

    @Inject(method = "glfwJoystickPresent", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwJoystickPresent(int jid, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwJoystickPresent(jid)));
        }
    }

    @Inject(method = "glfwMaximizeWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwMaximizeWindow(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwMaximizeWindow(window));
        }
    }

    @Inject(method = "glfwRawMouseMotionSupported", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwRawMouseMotionSupported(CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwRawMouseMotionSupported()));
        }
    }

    @Inject(method = "glfwRequestWindowAttention", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwRequestWindowAttention(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwRequestWindowAttention(window));
        }
    }

    @Inject(method = "glfwRestoreWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwRestoreWindow(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwRestoreWindow(window));
        }
    }

    @Inject(method = "glfwSetClipboardString(JLjava/lang/CharSequence;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetClipboardString(long window, CharSequence string, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetClipboardString(window, string));
        }
    }

    @Inject(method = "glfwSetClipboardString(JLjava/nio/ByteBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetClipboardString(long window, ByteBuffer string, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetClipboardString(window, string));
        }
    }

    @Inject(method = "glfwSetCursor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursor(long window, long cursor, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetCursor(window, cursor));
        }
    }

    @Inject(method = "glfwSetCursorPos", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursorPos(long window, double xpos, double ypos, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetCursorPos(window, xpos, ypos));
        }
    }

    @Inject(method = "glfwSetGamma", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetGamma(long monitor, float gamma, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetGamma(monitor, gamma));
        }
    }

    @Inject(method = "glfwSetGammaRamp", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetGammaRamp(long monitor, GLFWGammaRamp ramp, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetGammaRamp(monitor, ramp));
        }
    }

    @Inject(method = "glfwSetInputMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetInputMode(window, mode, value));
        }
    }

    @Inject(method = "glfwSetWindowAspectRatio", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowAspectRatio(long window, int numer, int denom, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowAspectRatio(window, numer, denom));
        }
    }

    @Inject(method = "glfwSetWindowAttrib", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowAttrib(long window, int attrib, int value, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowAttrib(window, attrib, value));
        }
    }

    @Inject(method = "glfwSetWindowIcon", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowIcon(long window, GLFWImage.Buffer images, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwSetWindowIcon(window, images));
        }
    }

    @Inject(method = "glfwSetWindowMonitor", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowMonitor(long window, long monitor, int xpos, int ypos, int width, int height, int refreshRate, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowMonitor(window, monitor, xpos, ypos, width, height, refreshRate));
        }
    }

    @Inject(method = "glfwSetWindowOpacity", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowOpacity(long window, float opacity, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowOpacity(window, opacity));
        }
    }

    @Inject(method = "glfwSetWindowPos", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowPos(long window, int xpos, int ypos, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowPos(window, xpos, ypos));
        }
    }

    @Inject(method = "glfwSetWindowSize", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowSize(long window, int width, int height, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowSize(window, width, height));
        }
    }

    @Inject(method = "glfwSetWindowSizeLimits", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowSizeLimits(long window, int minwidth, int minheight, int maxwidth, int maxheight, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowSizeLimits(window, minwidth, minheight, maxwidth, maxheight));
        }
    }

    @Inject(method = "glfwSetWindowTitle(JLjava/lang/CharSequence;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowTitle(long window, CharSequence title, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowTitle(window, title));
        }
    }

    @Inject(method = "glfwSetWindowTitle(JLjava/nio/ByteBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowTitle(long window, ByteBuffer title, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowTitle(window, title));
        }
    }

    @Inject(method = "glfwShowWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwShowWindow(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwShowWindow(window));
        }
    }

    @Inject(method = "glfwTerminate", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwTerminate(CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwTerminate());
        }
    }

    @Inject(method = "glfwUpdateGamepadMappings", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwUpdateGamepadMappings(ByteBuffer string, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwUpdateGamepadMappings(string)));
        }
    }

    @Inject(method = "glfwWindowHint", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwWindowHint(int hint, int value, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwWindowHint(hint, value));
        }
    }

    @Inject(method = "glfwWindowHintString(ILjava/lang/CharSequence;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwWindowHintString(int hint, CharSequence value, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwWindowHintString(hint, value));
        }
    }

    @Inject(method = "glfwWindowHintString(ILjava/nio/ByteBuffer;)V", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwWindowHintString(int hint, ByteBuffer value, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwWindowHintString(hint, value));
        }
    }
}
