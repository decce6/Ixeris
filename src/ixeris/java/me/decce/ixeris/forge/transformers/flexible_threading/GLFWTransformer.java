//? if forge {
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.flexible_threading;

import me.decce.ixeris.core.FlexibleThreadingManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.JNI;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CRedirect;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInline @CRedirect(method = "nglfwGetClipboardString", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwGetClipboardString(long window, long functionAddress) {
        synchronized (FlexibleThreadingManager.CLIPBOARD_LOCK) {
            return JNI.invokePP(window, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwSetClipboardString", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPV(JJJ)V"))
    private static void ixeris$nglfwSetClipboardString(long window, long string, long functionAddress) {
        synchronized (FlexibleThreadingManager.CLIPBOARD_LOCK) {
            JNI.invokePPV(window, string, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwCreateCursor", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JIIJ)J"))
    private static long ixeris$glfwCreateCursor(long image, int xhot, int yhot, long functionAddress) {
        synchronized (FlexibleThreadingManager.CURSOR_LOCK) {
            return JNI.invokePP(image, xhot, yhot, functionAddress);
        }
    }

    @CInline @CRedirect(method = "glfwCreateStandardCursor", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokeP(IJ)J"))
    private static long ixeris$glfwCreateStandardCursor(int shape, long functionAddress) {
        synchronized (FlexibleThreadingManager.CURSOR_LOCK) {
            return JNI.invokeP(shape, functionAddress);
        }
    }

    @CInline @CRedirect(method = "glfwDestroyCursor", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePV(JJ)V"))
    private static void ixeris$glfwDestroyCursor(long cursor, long functionAddress) {
        synchronized (FlexibleThreadingManager.CURSOR_LOCK) {
            JNI.invokePV(cursor, functionAddress);
        }
    }

    @CInline @CRedirect(method = "glfwSetCursor", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPV(JJJ)V"))
    private static void ixeris$glfwSetCursor(long window, long cursor, long functionAddress) {
        synchronized (FlexibleThreadingManager.CURSOR_LOCK) {
            JNI.invokePPV(window, cursor, functionAddress);
        }
    }

    @CInline @CRedirect(method = "glfwGetPrimaryMonitor", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokeP(J)J"))
    private static long ixeris$glfwGetPrimaryMonitors(long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokeP(functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetMonitors", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwGetMonitors(long count, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokePP(count, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetMonitorName", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwGetMonitorName(long monitor, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokePP(monitor, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetMonitorContentScale", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetMonitorName(long monitor, long xscale, long yscale, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePPPV(monitor, xscale, yscale, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetMonitorPhysicalSize", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetMonitorPhysicalSize(long monitor, long widthMM, long heightMM, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePPPV(monitor, widthMM, heightMM, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetMonitorPos", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetMonitorPos(long monitor, long xpos, long ypos, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePPPV(monitor, xpos, ypos, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetMonitorWorkarea", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPPPV(JJJJJJ)V"))
    private static void ixeris$nglfwGetWorkarea(long monitor, long xpos, long ypos, long width, long height, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePPPPPV(monitor, xpos, ypos, width, height, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetVideoMode", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwGetVideoMode(long monitor, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokePP(monitor, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetVideoModes", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPP(JJJ)J"))
    private static long ixeris$nglfwGetVideoModes(long monitor, long count, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokePPP(monitor, count, functionAddress);
        }
    }

    @CInline @CRedirect(method = "glfwSetGamma", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePV(JFJ)V"))
    private static void ixeris$nglfwSetGammaRamp(long function, float gamma, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePV(function, gamma, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwSetGammaRamp", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPV(JJJ)V"))
    private static void ixeris$nglfwSetGammaRamp(long monitor, long ramp, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePPV(monitor, ramp, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetGammaRamp", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwSetGammaRamp(long monitor, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokePP(monitor, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetFramebufferSize", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetFramebufferSize(long window, long width, long height, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePPPV(window, width, height, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetWindowSize", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetWindowSize(long window, long width, long height, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePPPV(window, width, height, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetWindowFrameSize", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPPPV(JJJJJJ)V"))
    private static void ixeris$nglfwGetWindowFrameSize(long window, long left, long top, long right, long bottom, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePPPPPV(window, left, top, right, bottom, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetWindowContentScale", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetWindowContentScale(long window, long xscale, long yscale, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePPPV(window, xscale, yscale, functionAddress);
        }
    }

    @CInline @CRedirect(method = "glfwGetWindowMonitor", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$glfwGetWindowMonitor(long window, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            return JNI.invokePP(window, functionAddress);
        }
    }

    @CInline @CRedirect(method = "glfwSetWindowMonitor", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPV(JJIIIIIJ)V"))
    private static void ixeris$glfwGetWindowMonitor(long window, long monitor, int xpos, int ypos, int width, int height, int refreshRate, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePPV(window, monitor, xpos, ypos, width, height, refreshRate, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetCursorPos", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetCursorPos(long window, long xpos, long ypos, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePPPV(window, xpos, ypos, functionAddress);
        }
    }

    @CInline @CRedirect(method = "glfwSetCursorPos", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePV(JDDJ)V"))
    private static void ixeris$glfwSetCursorPos(long window, double xpos, double ypos, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePV(window, xpos, ypos, functionAddress);
        }
    }

    @CInline @CRedirect(method = "glfwSetInputMode", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePV(JIIJ)V"))
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePV(window, mode, value, functionAddress);
        }
    }

    @CInline @CRedirect(method = "glfwGetInputMode", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePI(JIJ)I"))
    private static int ixeris$glfwGetInputMode(long window, int mode, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            return JNI.invokePI(window, mode, functionAddress);
        }
    }
}

*///?}