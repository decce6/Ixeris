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
    private static long ixeris$glfwGetClipboardString(long window, long functionAddress) {
        synchronized (FlexibleThreadingManager.CLIPBOARD_LOCK) {
            return JNI.invokePP(window, functionAddress);
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

}
