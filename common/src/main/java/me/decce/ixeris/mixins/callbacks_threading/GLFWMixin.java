/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.mixins.callbacks_threading;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWDropCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWErrorCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWWindowPosCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWWindowSizeCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWWindowCloseCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWWindowRefreshCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWWindowFocusCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWWindowIconifyCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWWindowMaximizeCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWFramebufferSizeCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWWindowContentScaleCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWKeyCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWCharCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWCharModsCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWMouseButtonCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWCursorPosCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWCursorEnterCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWScrollCallbackI;
import me.decce.ixeris.glfw.RedirectedGLFWDropCallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwSetErrorCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetErrorCallback(GLFWErrorCallbackI cbfun, CallbackInfoReturnable<GLFWErrorCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWErrorCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetErrorCallback(RedirectedGLFWErrorCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowPosCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowPosCallback(long window, GLFWWindowPosCallbackI cbfun, CallbackInfoReturnable<GLFWWindowPosCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowPosCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetWindowPosCallback(window, RedirectedGLFWWindowPosCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowSizeCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowSizeCallback(long window, GLFWWindowSizeCallbackI cbfun, CallbackInfoReturnable<GLFWWindowSizeCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowSizeCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetWindowSizeCallback(window, RedirectedGLFWWindowSizeCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowCloseCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowCloseCallback(long window, GLFWWindowCloseCallbackI cbfun, CallbackInfoReturnable<GLFWWindowCloseCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowCloseCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetWindowCloseCallback(window, RedirectedGLFWWindowCloseCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowRefreshCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI cbfun, CallbackInfoReturnable<GLFWWindowRefreshCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowRefreshCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetWindowRefreshCallback(window, RedirectedGLFWWindowRefreshCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowFocusCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowFocusCallback(long window, GLFWWindowFocusCallbackI cbfun, CallbackInfoReturnable<GLFWWindowFocusCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowFocusCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetWindowFocusCallback(window, RedirectedGLFWWindowFocusCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowIconifyCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI cbfun, CallbackInfoReturnable<GLFWWindowIconifyCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowIconifyCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetWindowIconifyCallback(window, RedirectedGLFWWindowIconifyCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowMaximizeCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI cbfun, CallbackInfoReturnable<GLFWWindowMaximizeCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowMaximizeCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetWindowMaximizeCallback(window, RedirectedGLFWWindowMaximizeCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetFramebufferSizeCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI cbfun, CallbackInfoReturnable<GLFWFramebufferSizeCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWFramebufferSizeCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetFramebufferSizeCallback(window, RedirectedGLFWFramebufferSizeCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowContentScaleCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI cbfun, CallbackInfoReturnable<GLFWWindowContentScaleCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowContentScaleCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetWindowContentScaleCallback(window, RedirectedGLFWWindowContentScaleCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetKeyCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetKeyCallback(long window, GLFWKeyCallbackI cbfun, CallbackInfoReturnable<GLFWKeyCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWKeyCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetKeyCallback(window, RedirectedGLFWKeyCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetCharCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCharCallback(long window, GLFWCharCallbackI cbfun, CallbackInfoReturnable<GLFWCharCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWCharCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetCharCallback(window, RedirectedGLFWCharCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetCharModsCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCharModsCallback(long window, GLFWCharModsCallbackI cbfun, CallbackInfoReturnable<GLFWCharModsCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWCharModsCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetCharModsCallback(window, RedirectedGLFWCharModsCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetMouseButtonCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetMouseButtonCallback(long window, GLFWMouseButtonCallbackI cbfun, CallbackInfoReturnable<GLFWMouseButtonCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWMouseButtonCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetMouseButtonCallback(window, RedirectedGLFWMouseButtonCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetCursorPosCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursorPosCallback(long window, GLFWCursorPosCallbackI cbfun, CallbackInfoReturnable<GLFWCursorPosCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWCursorPosCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetCursorPosCallback(window, RedirectedGLFWCursorPosCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetCursorEnterCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursorEnterCallback(long window, GLFWCursorEnterCallbackI cbfun, CallbackInfoReturnable<GLFWCursorEnterCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWCursorEnterCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetCursorEnterCallback(window, RedirectedGLFWCursorEnterCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetScrollCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetScrollCallback(long window, GLFWScrollCallbackI cbfun, CallbackInfoReturnable<GLFWScrollCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWScrollCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetScrollCallback(window, RedirectedGLFWScrollCallbackI.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetDropCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetDropCallback(long window, GLFWDropCallbackI cbfun, CallbackInfoReturnable<GLFWDropCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWDropCallbackI)) {
            cir.setReturnValue(GLFW.glfwSetDropCallback(window, RedirectedGLFWDropCallbackI.wrap(cbfun)));
        }
    }
}
