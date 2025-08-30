/*
Auto-translated from Mixin. See the generator directory in project root.
*/

package me.decce.ixeris.neoforge.core.transformers.glfw_threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWAllocator;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.glfw.GLFWGammaRamp;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.InjectionCallback;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInject(method = "glfwCreateCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateCursor(GLFWImage image, int xhot, int yhot, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateCursor(image, xhot, yhot)));
        }
    }

    @CInject(method = "glfwCreateWindow(IILjava/lang/CharSequence;JJ)J", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateWindow(int width, int height, CharSequence title, long monitor, long share, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateWindow(width, height, title, monitor, share)));
        }
    }

    @CInject(method = "glfwCreateWindow(IILjava/nio/ByteBuffer;JJ)J", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateWindow(int width, int height, ByteBuffer title, long monitor, long share, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwCreateWindow(width, height, title, monitor, share)));
        }
    }

    @CInject(method = "glfwDefaultWindowHints", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwDefaultWindowHints(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwDefaultWindowHints());
        }
    }

    @CInject(method = "glfwDestroyWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwDestroyWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwDestroyWindow(window));
        }
    }

    @CInject(method = "glfwFocusWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwFocusWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwFocusWindow(window));
        }
    }

    @CInject(method = "glfwGetClipboardString", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetClipboardString(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetClipboardString(window)));
        }
    }

    @CInject(method = "glfwGetCursorPos(J[D[D)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetCursorPos(long window, double[] xpos, double[] ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetCursorPos(window, xpos, ypos));
        }
    }

    @CInject(method = "glfwGetCursorPos(JLjava/nio/DoubleBuffer;Ljava/nio/DoubleBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetCursorPos(long window, DoubleBuffer xpos, DoubleBuffer ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetCursorPos(window, xpos, ypos));
        }
    }

    @CInject(method = "glfwGetGamepadName", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetGamepadName(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetGamepadName(jid)));
        }
    }

    @CInject(method = "glfwGetGamepadState", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetGamepadState(int jid, GLFWGamepadState state, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetGamepadState(jid, state)));
        }
    }

    @CInject(method = "glfwGetGammaRamp", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetGammaRamp(long monitor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetGammaRamp(monitor)));
        }
    }

    @CInject(method = "glfwGetJoystickAxes", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickAxes(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetJoystickAxes(jid)));
        }
    }

    @CInject(method = "glfwGetJoystickButtons", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickButtons(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetJoystickButtons(jid)));
        }
    }

    @CInject(method = "glfwGetJoystickGUID", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickGUID(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetJoystickGUID(jid)));
        }
    }

    @CInject(method = "glfwGetJoystickHats", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickHats(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetJoystickHats(jid)));
        }
    }

    @CInject(method = "glfwGetJoystickName", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickName(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetJoystickName(jid)));
        }
    }

    @CInject(method = "glfwGetMonitorContentScale(J[F[F)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorContentScale(long monitor, float[] xscale, float[] yscale, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorContentScale(monitor, xscale, yscale));
        }
    }

    @CInject(method = "glfwGetMonitorContentScale(JLjava/nio/FloatBuffer;Ljava/nio/FloatBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorContentScale(long monitor, FloatBuffer xscale, FloatBuffer yscale, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorContentScale(monitor, xscale, yscale));
        }
    }

    @CInject(method = "glfwGetMonitorName", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorName(long monitor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetMonitorName(monitor)));
        }
    }

    @CInject(method = "glfwGetMonitorPhysicalSize(J[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPhysicalSize(long monitor, int[] widthMM, int[] heightMM, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorPhysicalSize(monitor, widthMM, heightMM));
        }
    }

    @CInject(method = "glfwGetMonitorPhysicalSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPhysicalSize(long monitor, IntBuffer widthMM, IntBuffer heightMM, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorPhysicalSize(monitor, widthMM, heightMM));
        }
    }

    @CInject(method = "glfwGetMonitorPos(J[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPos(long monitor, int[] xpos, int[] ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorPos(monitor, xpos, ypos));
        }
    }

    @CInject(method = "glfwGetMonitorPos(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPos(long monitor, IntBuffer xpos, IntBuffer ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorPos(monitor, xpos, ypos));
        }
    }

    @CInject(method = "glfwGetMonitors", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitors(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetMonitors()));
        }
    }

    @CInject(method = "glfwGetMonitorWorkarea(J[I[I[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorWorkarea(long monitor, int[] xpos, int[] ypos, int[] width, int[] height, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorWorkarea(monitor, xpos, ypos, width, height));
        }
    }

    @CInject(method = "glfwGetMonitorWorkarea(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorWorkarea(long monitor, IntBuffer xpos, IntBuffer ypos, IntBuffer width, IntBuffer height, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetMonitorWorkarea(monitor, xpos, ypos, width, height));
        }
    }

    @CInject(method = "glfwGetVideoMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetVideoMode(long monitor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetVideoMode(monitor)));
        }
    }

    @CInject(method = "glfwGetVideoModes", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetVideoModes(long monitor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetVideoModes(monitor)));
        }
    }

    @CInject(method = "glfwGetWindowFrameSize(J[I[I[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowFrameSize(long window, int[] left, int[] top, int[] right, int[] bottom, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowFrameSize(window, left, top, right, bottom));
        }
    }

    @CInject(method = "glfwGetWindowFrameSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowFrameSize(long window, IntBuffer left, IntBuffer top, IntBuffer right, IntBuffer bottom, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowFrameSize(window, left, top, right, bottom));
        }
    }

    @CInject(method = "glfwGetWindowOpacity", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowOpacity(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetWindowOpacity(window)));
        }
    }

    @CInject(method = "glfwGetWindowPos(J[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowPos(long window, int[] xpos, int[] ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowPos(window, xpos, ypos));
        }
    }

    @CInject(method = "glfwGetWindowPos(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowPos(long window, IntBuffer xpos, IntBuffer ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwGetWindowPos(window, xpos, ypos));
        }
    }

    @CInject(method = "glfwHideWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwHideWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwHideWindow(window));
        }
    }

    @CInject(method = "glfwIconifyWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwIconifyWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwIconifyWindow(window));
        }
    }

    @CInject(method = "glfwInit", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwInit(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwInit()));
        }
    }

    @CInject(method = "glfwInitAllocator", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwInitAllocator(GLFWAllocator allocator, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwInitAllocator(allocator));
        }
    }

    @CInject(method = "glfwInitHint", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwInitHint(int hint, int value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwInitHint(hint, value));
        }
    }

    @CInject(method = "glfwJoystickIsGamepad", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwJoystickIsGamepad(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwJoystickIsGamepad(jid)));
        }
    }

    @CInject(method = "glfwJoystickPresent", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwJoystickPresent(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwJoystickPresent(jid)));
        }
    }

    @CInject(method = "glfwMaximizeWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwMaximizeWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwMaximizeWindow(window));
        }
    }

    @CInject(method = "glfwRawMouseMotionSupported", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwRawMouseMotionSupported(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwRawMouseMotionSupported()));
        }
    }

    @CInject(method = "glfwRequestWindowAttention", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwRequestWindowAttention(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwRequestWindowAttention(window));
        }
    }

    @CInject(method = "glfwRestoreWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwRestoreWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwRestoreWindow(window));
        }
    }

    @CInject(method = "glfwSetClipboardString(JLjava/lang/CharSequence;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetClipboardString(long window, CharSequence string, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetClipboardString(window, string));
        }
    }

    @CInject(method = "glfwSetClipboardString(JLjava/nio/ByteBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetClipboardString(long window, ByteBuffer string, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetClipboardString(window, string));
        }
    }

    @CInject(method = "glfwSetCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursor(long window, long cursor, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetCursor(window, cursor));
        }
    }

    @CInject(method = "glfwSetGamma", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetGamma(long monitor, float gamma, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetGamma(monitor, gamma));
        }
    }

    @CInject(method = "glfwSetGammaRamp", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetGammaRamp(long monitor, GLFWGammaRamp ramp, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetGammaRamp(monitor, ramp));
        }
    }

    @CInject(method = "glfwSetInputMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetInputMode(window, mode, value));
        }
    }

    @CInject(method = "glfwSetWindowAspectRatio", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowAspectRatio(long window, int numer, int denom, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowAspectRatio(window, numer, denom));
        }
    }

    @CInject(method = "glfwSetWindowAttrib", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowAttrib(long window, int attrib, int value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowAttrib(window, attrib, value));
        }
    }

    @CInject(method = "glfwSetWindowIcon", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowIcon(long window, GLFWImage.Buffer images, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(() -> GLFW.glfwSetWindowIcon(window, images));
        }
    }

    @CInject(method = "glfwSetWindowMonitor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowMonitor(long window, long monitor, int xpos, int ypos, int width, int height, int refreshRate, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowMonitor(window, monitor, xpos, ypos, width, height, refreshRate));
        }
    }

    @CInject(method = "glfwSetWindowOpacity", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowOpacity(long window, float opacity, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowOpacity(window, opacity));
        }
    }

    @CInject(method = "glfwSetWindowPos", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowPos(long window, int xpos, int ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowPos(window, xpos, ypos));
        }
    }

    @CInject(method = "glfwSetWindowSize", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowSize(long window, int width, int height, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowSize(window, width, height));
        }
    }

    @CInject(method = "glfwSetWindowSizeLimits", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowSizeLimits(long window, int minwidth, int minheight, int maxwidth, int maxheight, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowSizeLimits(window, minwidth, minheight, maxwidth, maxheight));
        }
    }

    @CInject(method = "glfwSetWindowTitle(JLjava/lang/CharSequence;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowTitle(long window, CharSequence title, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowTitle(window, title));
        }
    }

    @CInject(method = "glfwSetWindowTitle(JLjava/nio/ByteBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowTitle(long window, ByteBuffer title, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwSetWindowTitle(window, title));
        }
    }

    @CInject(method = "glfwShowWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwShowWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwShowWindow(window));
        }
    }

    @CInject(method = "glfwTerminate", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwTerminate(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwTerminate());
        }
    }

    @CInject(method = "glfwUpdateGamepadMappings", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwUpdateGamepadMappings(ByteBuffer string, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwUpdateGamepadMappings(string)));
        }
    }

    @CInject(method = "glfwWindowHint", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwWindowHint(int hint, int value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwWindowHint(hint, value));
        }
    }

    @CInject(method = "glfwWindowHintString(ILjava/lang/CharSequence;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwWindowHintString(int hint, CharSequence value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwWindowHintString(hint, value));
        }
    }

    @CInject(method = "glfwWindowHintString(ILjava/nio/ByteBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwWindowHintString(int hint, ByteBuffer value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(() -> GLFW.glfwWindowHintString(hint, value));
        }
    }
}
