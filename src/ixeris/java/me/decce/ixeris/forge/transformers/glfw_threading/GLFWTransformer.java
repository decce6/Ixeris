//? if forge {
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.glfw_threading;

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

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInline @CInject(method = "glfwCreateCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateCursor(GLFWImage image, int xhot, int yhot, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwCreateCursor, image, xhot, yhot)));
        }
    }

    @CInline @CInject(method = "glfwCreateWindow(IILjava/lang/CharSequence;JJ)J", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateWindow(int width, int height, CharSequence title, long monitor, long share, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwCreateWindow, width, height, title, monitor, share)));
        }
    }

    @CInline @CInject(method = "glfwCreateWindow(IILjava/nio/ByteBuffer;JJ)J", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwCreateWindow(int width, int height, ByteBuffer title, long monitor, long share, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwCreateWindow, width, height, title, monitor, share)));
        }
    }

    @CInline @CInject(method = "glfwDefaultWindowHints", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwDefaultWindowHints(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwDefaultWindowHints));
        }
    }

    @CInline @CInject(method = "glfwDestroyWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwDestroyWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwDestroyWindow, window));
        }
    }

    @CInline @CInject(method = "glfwFocusWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwFocusWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwFocusWindow, window));
        }
    }

    @CInline @CInject(method = "glfwGetClipboardString", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetClipboardString(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetClipboardString, window)));
        }
    }

    @CInline @CInject(method = "glfwGetCursorPos(J[D[D)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetCursorPos(long window, double[] xpos, double[] ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetCursorPos, window, xpos, ypos));
        }
    }

    @CInline @CInject(method = "glfwGetCursorPos(JLjava/nio/DoubleBuffer;Ljava/nio/DoubleBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetCursorPos(long window, DoubleBuffer xpos, DoubleBuffer ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetCursorPos, window, xpos, ypos));
        }
    }

    @CInline @CInject(method = "glfwGetGamepadName", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetGamepadName(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetGamepadName, jid)));
        }
    }

    @CInline @CInject(method = "glfwGetGamepadState", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetGamepadState(int jid, GLFWGamepadState state, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetGamepadState, jid, state)));
        }
    }

    @CInline @CInject(method = "glfwGetGammaRamp", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetGammaRamp(long monitor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetGammaRamp, monitor)));
        }
    }

    @CInline @CInject(method = "glfwGetJoystickAxes", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickAxes(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetJoystickAxes, jid)));
        }
    }

    @CInline @CInject(method = "glfwGetJoystickButtons", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickButtons(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetJoystickButtons, jid)));
        }
    }

    @CInline @CInject(method = "glfwGetJoystickGUID", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickGUID(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetJoystickGUID, jid)));
        }
    }

    @CInline @CInject(method = "glfwGetJoystickHats", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickHats(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetJoystickHats, jid)));
        }
    }

    @CInline @CInject(method = "glfwGetJoystickName", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetJoystickName(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetJoystickName, jid)));
        }
    }

    @CInline @CInject(method = "glfwGetMonitorContentScale(J[F[F)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorContentScale(long monitor, float[] xscale, float[] yscale, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetMonitorContentScale, monitor, xscale, yscale));
        }
    }

    @CInline @CInject(method = "glfwGetMonitorContentScale(JLjava/nio/FloatBuffer;Ljava/nio/FloatBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorContentScale(long monitor, FloatBuffer xscale, FloatBuffer yscale, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetMonitorContentScale, monitor, xscale, yscale));
        }
    }

    @CInline @CInject(method = "glfwGetMonitorName", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorName(long monitor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetMonitorName, monitor)));
        }
    }

    @CInline @CInject(method = "glfwGetMonitorPhysicalSize(J[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPhysicalSize(long monitor, int[] widthMM, int[] heightMM, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetMonitorPhysicalSize, monitor, widthMM, heightMM));
        }
    }

    @CInline @CInject(method = "glfwGetMonitorPhysicalSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPhysicalSize(long monitor, IntBuffer widthMM, IntBuffer heightMM, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetMonitorPhysicalSize, monitor, widthMM, heightMM));
        }
    }

    @CInline @CInject(method = "glfwGetMonitorPos(J[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPos(long monitor, int[] xpos, int[] ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetMonitorPos, monitor, xpos, ypos));
        }
    }

    @CInline @CInject(method = "glfwGetMonitorPos(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorPos(long monitor, IntBuffer xpos, IntBuffer ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetMonitorPos, monitor, xpos, ypos));
        }
    }

    @CInline @CInject(method = "glfwGetMonitors", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitors(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetMonitors)));
        }
    }

    @CInline @CInject(method = "glfwGetMonitorWorkarea(J[I[I[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorWorkarea(long monitor, int[] xpos, int[] ypos, int[] width, int[] height, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetMonitorWorkarea, monitor, xpos, ypos, width, height));
        }
    }

    @CInline @CInject(method = "glfwGetMonitorWorkarea(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMonitorWorkarea(long monitor, IntBuffer xpos, IntBuffer ypos, IntBuffer width, IntBuffer height, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetMonitorWorkarea, monitor, xpos, ypos, width, height));
        }
    }

    @CInline @CInject(method = "glfwGetVideoMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetVideoMode(long monitor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetVideoMode, monitor)));
        }
    }

    @CInline @CInject(method = "glfwGetVideoModes", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetVideoModes(long monitor, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetVideoModes, monitor)));
        }
    }

    @CInline @CInject(method = "glfwGetWindowFrameSize(J[I[I[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowFrameSize(long window, int[] left, int[] top, int[] right, int[] bottom, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetWindowFrameSize, window, left, top, right, bottom));
        }
    }

    @CInline @CInject(method = "glfwGetWindowFrameSize(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowFrameSize(long window, IntBuffer left, IntBuffer top, IntBuffer right, IntBuffer bottom, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetWindowFrameSize, window, left, top, right, bottom));
        }
    }

    @CInline @CInject(method = "glfwGetWindowOpacity", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowOpacity(long window, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwGetWindowOpacity, window)));
        }
    }

    @CInline @CInject(method = "glfwGetWindowPos(J[I[I)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowPos(long window, int[] xpos, int[] ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetWindowPos, window, xpos, ypos));
        }
    }

    @CInline @CInject(method = "glfwGetWindowPos(JLjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwGetWindowPos(long window, IntBuffer xpos, IntBuffer ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwGetWindowPos, window, xpos, ypos));
        }
    }

    @CInline @CInject(method = "glfwHideWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwHideWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwHideWindow, window));
        }
    }

    @CInline @CInject(method = "glfwIconifyWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwIconifyWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwIconifyWindow, window));
        }
    }

    @CInline @CInject(method = "glfwInit", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwInit(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwInit)));
        }
    }

    @CInline @CInject(method = "glfwInitAllocator", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwInitAllocator(GLFWAllocator allocator, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwInitAllocator, allocator));
        }
    }

    @CInline @CInject(method = "glfwInitHint", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwInitHint(int hint, int value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwInitHint, hint, value));
        }
    }

    @CInline @CInject(method = "glfwJoystickIsGamepad", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwJoystickIsGamepad(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwJoystickIsGamepad, jid)));
        }
    }

    @CInline @CInject(method = "glfwJoystickPresent", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwJoystickPresent(int jid, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwJoystickPresent, jid)));
        }
    }

    @CInline @CInject(method = "glfwMaximizeWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwMaximizeWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwMaximizeWindow, window));
        }
    }

    @CInline @CInject(method = "glfwRawMouseMotionSupported", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwRawMouseMotionSupported(InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwRawMouseMotionSupported)));
        }
    }

    @CInline @CInject(method = "glfwRequestWindowAttention", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwRequestWindowAttention(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwRequestWindowAttention, window));
        }
    }

    @CInline @CInject(method = "glfwRestoreWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwRestoreWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwRestoreWindow, window));
        }
    }

    @CInline @CInject(method = "glfwSetClipboardString(JLjava/lang/CharSequence;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetClipboardString(long window, CharSequence string, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetClipboardString, window, string));
        }
    }

    @CInline @CInject(method = "glfwSetClipboardString(JLjava/nio/ByteBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetClipboardString(long window, ByteBuffer string, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetClipboardString, window, string));
        }
    }

    @CInline @CInject(method = "glfwSetCursor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursor(long window, long cursor, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetCursor, window, cursor));
        }
    }

    @CInline @CInject(method = "glfwSetGamma", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetGamma(long monitor, float gamma, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetGamma, monitor, gamma));
        }
    }

    @CInline @CInject(method = "glfwSetGammaRamp", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetGammaRamp(long monitor, GLFWGammaRamp ramp, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetGammaRamp, monitor, ramp));
        }
    }

    @CInline @CInject(method = "glfwSetInputMode", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetInputMode, window, mode, value));
        }
    }

    @CInline @CInject(method = "glfwSetWindowAspectRatio", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowAspectRatio(long window, int numer, int denom, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetWindowAspectRatio, window, numer, denom));
        }
    }

    @CInline @CInject(method = "glfwSetWindowAttrib", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowAttrib(long window, int attrib, int value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetWindowAttrib, window, attrib, value));
        }
    }

    @CInline @CInject(method = "glfwSetWindowIcon", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowIcon(long window, GLFWImage.Buffer images, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwSetWindowIcon, window, images));
        }
    }

    @CInline @CInject(method = "glfwSetWindowMonitor", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowMonitor(long window, long monitor, int xpos, int ypos, int width, int height, int refreshRate, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetWindowMonitor, window, monitor, xpos, ypos, width, height, refreshRate));
        }
    }

    @CInline @CInject(method = "glfwSetWindowOpacity", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowOpacity(long window, float opacity, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetWindowOpacity, window, opacity));
        }
    }

    @CInline @CInject(method = "glfwSetWindowPos", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowPos(long window, int xpos, int ypos, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetWindowPos, window, xpos, ypos));
        }
    }

    @CInline @CInject(method = "glfwSetWindowSize", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowSize(long window, int width, int height, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetWindowSize, window, width, height));
        }
    }

    @CInline @CInject(method = "glfwSetWindowSizeLimits", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowSizeLimits(long window, int minwidth, int minheight, int maxwidth, int maxheight, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetWindowSizeLimits, window, minwidth, minheight, maxwidth, maxheight));
        }
    }

    @CInline @CInject(method = "glfwSetWindowTitle(JLjava/lang/CharSequence;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowTitle(long window, CharSequence title, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetWindowTitle, window, title));
        }
    }

    @CInline @CInject(method = "glfwSetWindowTitle(JLjava/nio/ByteBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowTitle(long window, ByteBuffer title, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwSetWindowTitle, window, title));
        }
    }

    @CInline @CInject(method = "glfwShowWindow", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwShowWindow(long window, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwShowWindow, window));
        }
    }

    @CInline @CInject(method = "glfwTerminate", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwTerminate(InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwTerminate));
        }
    }

    @CInline @CInject(method = "glfwUpdateGamepadMappings", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwUpdateGamepadMappings(ByteBuffer string, InjectionCallback cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(makeSupplier(GLFW::glfwUpdateGamepadMappings, string)));
        }
    }

    @CInline @CInject(method = "glfwWindowHint", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwWindowHint(int hint, int value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwWindowHint, hint, value));
        }
    }

    @CInline @CInject(method = "glfwWindowHintString(ILjava/lang/CharSequence;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwWindowHintString(int hint, CharSequence value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwWindowHintString, hint, value));
        }
    }

    @CInline @CInject(method = "glfwWindowHintString(ILjava/nio/ByteBuffer;)V", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwWindowHintString(int hint, ByteBuffer value, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.run(makeRunnable(GLFW::glfwWindowHintString, hint, value));
        }
    }
}

*///?}