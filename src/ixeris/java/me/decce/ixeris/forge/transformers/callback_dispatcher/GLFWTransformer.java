//? if forge {
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

/^
Auto-generated. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.callback_dispatcher;

import me.decce.ixeris.core.glfw.callback_dispatcher.*;
import org.lwjgl.glfw.*;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInline @CInject(method = "glfwSetCharCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetCharCallback(long window, GLFWCharCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = CharCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetCharCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCharCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = CharCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.charCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    // GENERATED CODE BELOW

    @CInline @CInject(method = "glfwSetCharModsCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetCharModsCallback(long window, GLFWCharModsCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = CharModsCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetCharModsCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCharModsCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = CharModsCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.charModsCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetCursorEnterCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetCursorEnterCallback(long window, GLFWCursorEnterCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = CursorEnterCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetCursorEnterCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCursorEnterCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = CursorEnterCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.cursorEnterCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetCursorPosCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetCursorPosCallback(long window, GLFWCursorPosCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = CursorPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetCursorPosCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCursorPosCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = CursorPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.cursorPosCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetDropCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetDropCallback(long window, GLFWDropCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = DropCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetDropCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetDropCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = DropCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.dropCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetErrorCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetErrorCallback(GLFWErrorCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = ErrorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetErrorCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetErrorCallback(long cbfun, InjectionCallback cir) {
        var dispatcher = ErrorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.errorCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetFramebufferSizeCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = FramebufferSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetFramebufferSizeCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetFramebufferSizeCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = FramebufferSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.framebufferSizeCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetKeyCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetKeyCallback(long window, GLFWKeyCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = KeyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetKeyCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetKeyCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = KeyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.keyCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetMonitorCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetMonitorCallback(GLFWMonitorCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = MonitorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetMonitorCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetMonitorCallback(long cbfun, InjectionCallback cir) {
        var dispatcher = MonitorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.monitorCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetMouseButtonCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetMouseButtonCallback(long window, GLFWMouseButtonCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = MouseButtonCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetMouseButtonCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetMouseButtonCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = MouseButtonCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.mouseButtonCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetScrollCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetScrollCallback(long window, GLFWScrollCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = ScrollCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetScrollCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetScrollCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = ScrollCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.scrollCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetWindowCloseCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowCloseCallback(long window, GLFWWindowCloseCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowCloseCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetWindowCloseCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowCloseCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowCloseCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowCloseCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetWindowContentScaleCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowContentScaleCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetWindowContentScaleCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowContentScaleCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowContentScaleCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowContentScaleCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetWindowFocusCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowFocusCallback(long window, GLFWWindowFocusCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowFocusCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetWindowFocusCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowFocusCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowFocusCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowFocusCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetWindowIconifyCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowIconifyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetWindowIconifyCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowIconifyCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowIconifyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowIconifyCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetWindowMaximizeCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowMaximizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetWindowMaximizeCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowMaximizeCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowMaximizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowMaximizeCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetWindowPosCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowPosCallback(long window, GLFWWindowPosCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetWindowPosCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowPosCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowPosCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetWindowRefreshCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowRefreshCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetWindowRefreshCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowRefreshCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowRefreshCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowRefreshCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetWindowSizeCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowSizeCallback(long window, GLFWWindowSizeCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetWindowSizeCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowSizeCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowSizeCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }
}
*///?}