/*
Auto-translated from Mixin. See the generator directory in project root.
*/

/*
Auto-generated. See the generator directory in project root.
*/

package me.decce.ixeris.forge.core.core.transformers.callback_dispatcher;

import me.decce.ixeris.core.glfw.callback_dispatcher.CommonCallbacks;
import me.decce.ixeris.core.glfw.callback_dispatcher.CharCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.CharModsCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.CursorEnterCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.CursorPosCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.DropCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.ErrorCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.FramebufferSizeCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.KeyCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.MonitorCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.MouseButtonCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.ScrollCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowCloseCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowContentScaleCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowFocusCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowIconifyCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowMaximizeCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowPosCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowRefreshCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowSizeCallbackDispatcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMonitorCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInject(method = "glfwSetCharCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetCharCallback(long window, GLFWCharCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = CharCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetCharCallback", target = @CTarget("RETURN"), cancellable = true)
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

    @CInject(method = "glfwSetCharModsCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetCharModsCallback(long window, GLFWCharModsCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = CharModsCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetCharModsCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCharModsCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = CharModsCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.charModsCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetCursorEnterCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetCursorEnterCallback(long window, GLFWCursorEnterCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = CursorEnterCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetCursorEnterCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCursorEnterCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = CursorEnterCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.cursorEnterCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetCursorPosCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetCursorPosCallback(long window, GLFWCursorPosCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = CursorPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetCursorPosCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCursorPosCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = CursorPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.cursorPosCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetDropCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetDropCallback(long window, GLFWDropCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = DropCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetDropCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetDropCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = DropCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.dropCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetErrorCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetErrorCallback(GLFWErrorCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = ErrorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetErrorCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetErrorCallback(long cbfun, InjectionCallback cir) {
        var dispatcher = ErrorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.errorCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetFramebufferSizeCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = FramebufferSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetFramebufferSizeCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetFramebufferSizeCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = FramebufferSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.framebufferSizeCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetKeyCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetKeyCallback(long window, GLFWKeyCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = KeyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetKeyCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetKeyCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = KeyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.keyCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetMonitorCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetMonitorCallback(GLFWMonitorCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = MonitorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetMonitorCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetMonitorCallback(long cbfun, InjectionCallback cir) {
        var dispatcher = MonitorCallbackDispatcher.get();
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.monitorCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetMouseButtonCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetMouseButtonCallback(long window, GLFWMouseButtonCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = MouseButtonCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetMouseButtonCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetMouseButtonCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = MouseButtonCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.mouseButtonCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetScrollCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetScrollCallback(long window, GLFWScrollCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = ScrollCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetScrollCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetScrollCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = ScrollCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.scrollCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetWindowCloseCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowCloseCallback(long window, GLFWWindowCloseCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowCloseCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetWindowCloseCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowCloseCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowCloseCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowCloseCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetWindowContentScaleCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowContentScaleCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetWindowContentScaleCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowContentScaleCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowContentScaleCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowContentScaleCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetWindowFocusCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowFocusCallback(long window, GLFWWindowFocusCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowFocusCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetWindowFocusCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowFocusCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowFocusCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowFocusCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetWindowIconifyCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowIconifyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetWindowIconifyCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowIconifyCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowIconifyCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowIconifyCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetWindowMaximizeCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowMaximizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetWindowMaximizeCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowMaximizeCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowMaximizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowMaximizeCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetWindowPosCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowPosCallback(long window, GLFWWindowPosCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetWindowPosCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowPosCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowPosCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowPosCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetWindowRefreshCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowRefreshCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetWindowRefreshCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowRefreshCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = WindowRefreshCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowRefreshCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInject(method = "glfwSetWindowSizeCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetWindowSizeCallback(long window, GLFWWindowSizeCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = WindowSizeCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInject(method = "nglfwSetWindowSizeCallback", target = @CTarget("RETURN"), cancellable = true)
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