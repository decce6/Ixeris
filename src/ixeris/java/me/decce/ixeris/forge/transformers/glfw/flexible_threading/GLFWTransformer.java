//? if forge { 
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.glfw.flexible_threading;

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
    // require=0 and expect=0 are needed for BlazeSDL mod compatibility
    @CInline @CRedirect(method = "nglfwGetClipboardString", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePP(JJ)J"))
    private static long ixeris$nglfwGetClipboardString(long window, long functionAddress) {
        if (FlexibleThreadingManager.canUseFlexibleClipboard()) {
            synchronized (FlexibleThreadingManager.CLIPBOARD_LOCK) {
                return JNI.invokePP(window, functionAddress);
            }
        }
        return JNI.invokePP(window, functionAddress);
    }

    @CInline @CRedirect(method = "nglfwSetClipboardString", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPV(JJJ)V"))
    private static void ixeris$nglfwSetClipboardString(long window, long string, long functionAddress) {
        synchronized (FlexibleThreadingManager.CLIPBOARD_LOCK) {
            JNI.invokePPV(window, string, functionAddress);
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

    @CInline @CRedirect(method = "nglfwGetCursorPos", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetCursorPos(long window, long xpos, long ypos, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePPPV(window, xpos, ypos, functionAddress);
        }
    }

    @CInline @CRedirect(method = "nglfwGetFramebufferSize", target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/system/JNI;invokePPPV(JJJJ)V"))
    private static void ixeris$nglfwGetFramebufferSize(long window, long width, long height, long functionAddress) {
        synchronized (FlexibleThreadingManager.WINDOW_LOCK) {
            JNI.invokePPPV(window, width, height, functionAddress);
        }
    }
}

*///? }