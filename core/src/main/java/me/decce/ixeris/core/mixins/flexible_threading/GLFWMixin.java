package me.decce.ixeris.core.mixins.flexible_threading;

import me.decce.ixeris.core.FlexibleThreadingManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.JNI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Redirect(method = "nglfwGetClipboardString", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwGetClipboardString(long window, long functionAddress) {
        synchronized (FlexibleThreadingManager.CLIPBOARD_LOCK) {
            return JNI.invokePP(window, functionAddress);
        }
    }

    @Redirect(method = "nglfwSetClipboardString", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPV(JJJ)V"))
    private static void ixeris$nglfwSetClipboardString(long window, long string, long functionAddress) {
        synchronized (FlexibleThreadingManager.CLIPBOARD_LOCK) {
            JNI.invokePPV(window, string, functionAddress);
        }
    }

    @Redirect(method = "nglfwCreateCursor", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JIIJ)J"))
    private static long ixeris$glfwCreateCursor(long image, int xhot, int yhot, long functionAddress) {
        synchronized (FlexibleThreadingManager.CURSOR_LOCK) {
            return JNI.invokePP(image, xhot, yhot, functionAddress);
        }
    }

    @Redirect(method = "glfwCreateStandardCursor", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokeP(IJ)J"))
    private static long ixeris$glfwCreateStandardCursor(int shape, long functionAddress) {
        synchronized (FlexibleThreadingManager.CURSOR_LOCK) {
            return JNI.invokeP(shape, functionAddress);
        }
    }

    @Redirect(method = "glfwDestroyCursor", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePV(JJ)V"))
    private static void ixeris$glfwDestroyCursor(long cursor, long functionAddress) {
        synchronized (FlexibleThreadingManager.CURSOR_LOCK) {
            JNI.invokePV(cursor, functionAddress);
        }
    }

    @Redirect(method = "glfwSetCursor", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPV(JJJ)V"))
    private static void ixeris$glfwSetCursor(long window, long cursor, long functionAddress) {
        synchronized (FlexibleThreadingManager.CURSOR_LOCK) {
            JNI.invokePPV(window, cursor, functionAddress);
        }
    }

    @Redirect(method = "glfwGetPrimaryMonitor", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokeP(J)J"))
    private static long ixeris$glfwGetPrimaryMonitors(long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokeP(functionAddress);
        }
    }

    @Redirect(method = "nglfwGetMonitors", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwGetMonitors(long count, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokePP(count, functionAddress);
        }
    }

    @Redirect(method = "nglfwGetMonitorName", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwGetMonitorName(long monitor, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokePP(monitor, functionAddress);
        }
    }

    @Redirect(method = "nglfwGetMonitorContentScale", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetMonitorName(long monitor, long xscale, long yscale, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePPPV(monitor, xscale, yscale, functionAddress);
        }
    }

    @Redirect(method = "nglfwGetMonitorPhysicalSize", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetMonitorPhysicalSize(long monitor, long widthMM, long heightMM, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePPPV(monitor, widthMM, heightMM, functionAddress);
        }
    }

    @Redirect(method = "nglfwGetMonitorPos", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetMonitorPos(long monitor, long xpos, long ypos, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePPPV(monitor, xpos, ypos, functionAddress);
        }
    }

    @Redirect(method = "nglfwGetMonitorWorkarea", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPPPV(JJJJJJ)V"))
    private static void ixeris$nglfwGetWorkarea(long monitor, long xpos, long ypos, long width, long height, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePPPPPV(monitor, xpos, ypos, width, height, functionAddress);
        }
    }

    @Redirect(method = "nglfwGetVideoMode", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwGetVideoMode(long monitor, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokePP(monitor, functionAddress);
        }
    }

    @Redirect(method = "nglfwGetVideoModes", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPP(JJJ)J"))
    private static long ixeris$nglfwGetVideoModes(long monitor, long count, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokePPP(monitor, count, functionAddress);
        }
    }

    @Redirect(method = "glfwSetGamma", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePV(JFJ)V"))
    private static void ixeris$nglfwSetGammaRamp(long function, float gamma, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePV(function, gamma, functionAddress);
        }
    }

    @Redirect(method = "nglfwSetGammaRamp", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPV(JJJ)V"))
    private static void ixeris$nglfwSetGammaRamp(long monitor, long ramp, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            JNI.invokePPV(monitor, ramp, functionAddress);
        }
    }

    @Redirect(method = "nglfwGetGammaRamp", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwSetGammaRamp(long monitor, long functionAddress) {
        synchronized (FlexibleThreadingManager.MONITOR_LOCK) {
            return JNI.invokePP(monitor, functionAddress);
        }
    }

    @Redirect(method = "nglfwGetCursorPos", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetCursorPos(long window, long xpos, long ypos, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePPPV(window, xpos, ypos, functionAddress);
        }
    }

    @Redirect(method = "nglfwGetFramebufferSize", at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetFramebufferSize(long window, long width, long height, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePPPV(window, width, height, functionAddress);
        }
    }
}
