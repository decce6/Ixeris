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
import me.decce.ixeris.glfw.RedirectedGLFWErrorCallback;
import me.decce.ixeris.glfw.RedirectedGLFWWindowPosCallback;
import me.decce.ixeris.glfw.RedirectedGLFWWindowSizeCallback;
import me.decce.ixeris.glfw.RedirectedGLFWWindowCloseCallback;
import me.decce.ixeris.glfw.RedirectedGLFWWindowRefreshCallback;
import me.decce.ixeris.glfw.RedirectedGLFWWindowFocusCallback;
import me.decce.ixeris.glfw.RedirectedGLFWWindowIconifyCallback;
import me.decce.ixeris.glfw.RedirectedGLFWWindowMaximizeCallback;
import me.decce.ixeris.glfw.RedirectedGLFWFramebufferSizeCallback;
import me.decce.ixeris.glfw.RedirectedGLFWWindowContentScaleCallback;
import me.decce.ixeris.glfw.RedirectedGLFWKeyCallback;
import me.decce.ixeris.glfw.RedirectedGLFWCharCallback;
import me.decce.ixeris.glfw.RedirectedGLFWCharModsCallback;
import me.decce.ixeris.glfw.RedirectedGLFWMouseButtonCallback;
import me.decce.ixeris.glfw.RedirectedGLFWCursorPosCallback;
import me.decce.ixeris.glfw.RedirectedGLFWCursorEnterCallback;
import me.decce.ixeris.glfw.RedirectedGLFWScrollCallback;
import me.decce.ixeris.glfw.RedirectedGLFWDropCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwSetErrorCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetErrorCallback(GLFWErrorCallbackI cbfun, CallbackInfoReturnable<GLFWErrorCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWErrorCallback)) {
            cir.setReturnValue(GLFW.glfwSetErrorCallback(RedirectedGLFWErrorCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowPosCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowPosCallback(long window, GLFWWindowPosCallbackI cbfun, CallbackInfoReturnable<GLFWWindowPosCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowPosCallback)) {
            cir.setReturnValue(GLFW.glfwSetWindowPosCallback(window, RedirectedGLFWWindowPosCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowSizeCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowSizeCallback(long window, GLFWWindowSizeCallbackI cbfun, CallbackInfoReturnable<GLFWWindowSizeCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowSizeCallback)) {
            cir.setReturnValue(GLFW.glfwSetWindowSizeCallback(window, RedirectedGLFWWindowSizeCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowCloseCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowCloseCallback(long window, GLFWWindowCloseCallbackI cbfun, CallbackInfoReturnable<GLFWWindowCloseCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowCloseCallback)) {
            cir.setReturnValue(GLFW.glfwSetWindowCloseCallback(window, RedirectedGLFWWindowCloseCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowRefreshCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI cbfun, CallbackInfoReturnable<GLFWWindowRefreshCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowRefreshCallback)) {
            cir.setReturnValue(GLFW.glfwSetWindowRefreshCallback(window, RedirectedGLFWWindowRefreshCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowFocusCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowFocusCallback(long window, GLFWWindowFocusCallbackI cbfun, CallbackInfoReturnable<GLFWWindowFocusCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowFocusCallback)) {
            cir.setReturnValue(GLFW.glfwSetWindowFocusCallback(window, RedirectedGLFWWindowFocusCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowIconifyCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI cbfun, CallbackInfoReturnable<GLFWWindowIconifyCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowIconifyCallback)) {
            cir.setReturnValue(GLFW.glfwSetWindowIconifyCallback(window, RedirectedGLFWWindowIconifyCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowMaximizeCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI cbfun, CallbackInfoReturnable<GLFWWindowMaximizeCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowMaximizeCallback)) {
            cir.setReturnValue(GLFW.glfwSetWindowMaximizeCallback(window, RedirectedGLFWWindowMaximizeCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetFramebufferSizeCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI cbfun, CallbackInfoReturnable<GLFWFramebufferSizeCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWFramebufferSizeCallback)) {
            cir.setReturnValue(GLFW.glfwSetFramebufferSizeCallback(window, RedirectedGLFWFramebufferSizeCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetWindowContentScaleCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI cbfun, CallbackInfoReturnable<GLFWWindowContentScaleCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWWindowContentScaleCallback)) {
            cir.setReturnValue(GLFW.glfwSetWindowContentScaleCallback(window, RedirectedGLFWWindowContentScaleCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetKeyCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetKeyCallback(long window, GLFWKeyCallbackI cbfun, CallbackInfoReturnable<GLFWKeyCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWKeyCallback)) {
            cir.setReturnValue(GLFW.glfwSetKeyCallback(window, RedirectedGLFWKeyCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetCharCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCharCallback(long window, GLFWCharCallbackI cbfun, CallbackInfoReturnable<GLFWCharCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWCharCallback)) {
            cir.setReturnValue(GLFW.glfwSetCharCallback(window, RedirectedGLFWCharCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetCharModsCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCharModsCallback(long window, GLFWCharModsCallbackI cbfun, CallbackInfoReturnable<GLFWCharModsCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWCharModsCallback)) {
            cir.setReturnValue(GLFW.glfwSetCharModsCallback(window, RedirectedGLFWCharModsCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetMouseButtonCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetMouseButtonCallback(long window, GLFWMouseButtonCallbackI cbfun, CallbackInfoReturnable<GLFWMouseButtonCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWMouseButtonCallback)) {
            cir.setReturnValue(GLFW.glfwSetMouseButtonCallback(window, RedirectedGLFWMouseButtonCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetCursorPosCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursorPosCallback(long window, GLFWCursorPosCallbackI cbfun, CallbackInfoReturnable<GLFWCursorPosCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWCursorPosCallback)) {
            cir.setReturnValue(GLFW.glfwSetCursorPosCallback(window, RedirectedGLFWCursorPosCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetCursorEnterCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursorEnterCallback(long window, GLFWCursorEnterCallbackI cbfun, CallbackInfoReturnable<GLFWCursorEnterCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWCursorEnterCallback)) {
            cir.setReturnValue(GLFW.glfwSetCursorEnterCallback(window, RedirectedGLFWCursorEnterCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetScrollCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetScrollCallback(long window, GLFWScrollCallbackI cbfun, CallbackInfoReturnable<GLFWScrollCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWScrollCallback)) {
            cir.setReturnValue(GLFW.glfwSetScrollCallback(window, RedirectedGLFWScrollCallback.wrap(cbfun)));
        }
    }

    @Inject(method = "glfwSetDropCallback", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetDropCallback(long window, GLFWDropCallbackI cbfun, CallbackInfoReturnable<GLFWDropCallbackI> cir) {
        if (!(cbfun instanceof RedirectedGLFWDropCallback)) {
            cir.setReturnValue(GLFW.glfwSetDropCallback(window, RedirectedGLFWDropCallback.wrap(cbfun)));
        }
    }
}
