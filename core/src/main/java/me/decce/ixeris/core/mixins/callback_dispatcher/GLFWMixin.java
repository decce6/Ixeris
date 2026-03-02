/*
Auto-generated. See the generator directory in project root.
*/

package me.decce.ixeris.core.mixins.callback_dispatcher;

import me.decce.ixeris.core.glfw.callback_dispatcher.*;
import org.lwjgl.glfw.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwSetCharCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetCharCallback(long window, GLFWCharCallbackI cbfun, CallbackInfoReturnable<GLFWCharCallbackI> cir) {
        var dispatcher = CharCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetCharCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCharCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = CharCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.charCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    // GENERATED CODE BELOW

    @Inject(method = "glfwSetCharModsCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetCharModsCallback(long window, GLFWCharModsCallbackI cbfun, CallbackInfoReturnable<GLFWCharModsCallbackI> cir) {
        var dispatcher = CharModsCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetCharModsCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCharModsCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = CharModsCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.charModsCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetCursorEnterCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetCursorEnterCallback(long window, GLFWCursorEnterCallbackI cbfun, CallbackInfoReturnable<GLFWCursorEnterCallbackI> cir) {
        var dispatcher = CursorEnterCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetCursorEnterCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCursorEnterCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = CursorEnterCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.cursorEnterCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetCursorPosCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetCursorPosCallback(long window, GLFWCursorPosCallbackI cbfun, CallbackInfoReturnable<GLFWCursorPosCallbackI> cir) {
        var dispatcher = CursorPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetCursorPosCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCursorPosCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = CursorPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.cursorPosCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetDropCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetDropCallback(long window, GLFWDropCallbackI cbfun, CallbackInfoReturnable<GLFWDropCallbackI> cir) {
        var dispatcher = DropCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetDropCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetDropCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = DropCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.dropCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetErrorCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetErrorCallback(GLFWErrorCallbackI cbfun, CallbackInfoReturnable<GLFWErrorCallbackI> cir) {
        var dispatcher = ErrorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetErrorCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetErrorCallback(long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = ErrorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.errorCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetFramebufferSizeCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI cbfun, CallbackInfoReturnable<GLFWFramebufferSizeCallbackI> cir) {
        var dispatcher = FramebufferSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetFramebufferSizeCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetFramebufferSizeCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = FramebufferSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.framebufferSizeCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetIMEStatusCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetIMEStatusCallback(long window, GLFWIMEStatusCallbackI cbfun, CallbackInfoReturnable<GLFWIMEStatusCallbackI> cir) {
        var dispatcher = IMEStatusCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetIMEStatusCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetIMEStatusCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = IMEStatusCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.iMEStatusCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetKeyCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetKeyCallback(long window, GLFWKeyCallbackI cbfun, CallbackInfoReturnable<GLFWKeyCallbackI> cir) {
        var dispatcher = KeyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetKeyCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetKeyCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = KeyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.keyCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetMonitorCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetMonitorCallback(GLFWMonitorCallbackI cbfun, CallbackInfoReturnable<GLFWMonitorCallbackI> cir) {
        var dispatcher = MonitorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetMonitorCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetMonitorCallback(long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = MonitorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.monitorCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetMouseButtonCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetMouseButtonCallback(long window, GLFWMouseButtonCallbackI cbfun, CallbackInfoReturnable<GLFWMouseButtonCallbackI> cir) {
        var dispatcher = MouseButtonCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetMouseButtonCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetMouseButtonCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = MouseButtonCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.mouseButtonCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetPreeditCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetPreeditCallback(long window, GLFWPreeditCallbackI cbfun, CallbackInfoReturnable<GLFWPreeditCallbackI> cir) {
        var dispatcher = PreeditCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetPreeditCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetPreeditCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = PreeditCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.preeditCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetPreeditCandidateCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetPreeditCandidateCallback(long window, GLFWPreeditCandidateCallbackI cbfun, CallbackInfoReturnable<GLFWPreeditCandidateCallbackI> cir) {
        var dispatcher = PreeditCandidateCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetPreeditCandidateCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetPreeditCandidateCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = PreeditCandidateCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.preeditCandidateCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetScrollCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetScrollCallback(long window, GLFWScrollCallbackI cbfun, CallbackInfoReturnable<GLFWScrollCallbackI> cir) {
        var dispatcher = ScrollCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetScrollCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetScrollCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = ScrollCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.scrollCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetWindowCloseCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetWindowCloseCallback(long window, GLFWWindowCloseCallbackI cbfun, CallbackInfoReturnable<GLFWWindowCloseCallbackI> cir) {
        var dispatcher = WindowCloseCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetWindowCloseCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowCloseCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = WindowCloseCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowCloseCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetWindowContentScaleCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI cbfun, CallbackInfoReturnable<GLFWWindowContentScaleCallbackI> cir) {
        var dispatcher = WindowContentScaleCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetWindowContentScaleCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowContentScaleCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = WindowContentScaleCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowContentScaleCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetWindowFocusCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetWindowFocusCallback(long window, GLFWWindowFocusCallbackI cbfun, CallbackInfoReturnable<GLFWWindowFocusCallbackI> cir) {
        var dispatcher = WindowFocusCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetWindowFocusCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowFocusCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = WindowFocusCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowFocusCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetWindowIconifyCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI cbfun, CallbackInfoReturnable<GLFWWindowIconifyCallbackI> cir) {
        var dispatcher = WindowIconifyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetWindowIconifyCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowIconifyCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = WindowIconifyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowIconifyCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetWindowMaximizeCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI cbfun, CallbackInfoReturnable<GLFWWindowMaximizeCallbackI> cir) {
        var dispatcher = WindowMaximizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetWindowMaximizeCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowMaximizeCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = WindowMaximizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowMaximizeCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetWindowPosCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetWindowPosCallback(long window, GLFWWindowPosCallbackI cbfun, CallbackInfoReturnable<GLFWWindowPosCallbackI> cir) {
        var dispatcher = WindowPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetWindowPosCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowPosCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = WindowPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowPosCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetWindowRefreshCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI cbfun, CallbackInfoReturnable<GLFWWindowRefreshCallbackI> cir) {
        var dispatcher = WindowRefreshCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetWindowRefreshCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowRefreshCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = WindowRefreshCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowRefreshCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @Inject(method = "glfwSetWindowSizeCallback", at = @At("HEAD"))
    private static void ixeris$glfwSetWindowSizeCallback(long window, GLFWWindowSizeCallbackI cbfun, CallbackInfoReturnable<GLFWWindowSizeCallbackI> cir) {
        var dispatcher = WindowSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @Inject(method = "nglfwSetWindowSizeCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowSizeCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var dispatcher = WindowSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowSizeCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }
}