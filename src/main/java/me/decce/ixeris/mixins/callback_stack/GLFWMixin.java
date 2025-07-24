/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.mixins.callback_stack;

import me.decce.ixeris.glfw.callback_stack.CommonCallbacks;
import me.decce.ixeris.glfw.callback_stack.CharCallbackStack;
import me.decce.ixeris.glfw.callback_stack.CharModsCallbackStack;
import me.decce.ixeris.glfw.callback_stack.CursorEnterCallbackStack;
import me.decce.ixeris.glfw.callback_stack.CursorPosCallbackStack;
import me.decce.ixeris.glfw.callback_stack.DropCallbackStack;
import me.decce.ixeris.glfw.callback_stack.ErrorCallbackStack;
import me.decce.ixeris.glfw.callback_stack.FramebufferSizeCallbackStack;
import me.decce.ixeris.glfw.callback_stack.KeyCallbackStack;
import me.decce.ixeris.glfw.callback_stack.MonitorCallbackStack;
import me.decce.ixeris.glfw.callback_stack.MouseButtonCallbackStack;
import me.decce.ixeris.glfw.callback_stack.ScrollCallbackStack;
import me.decce.ixeris.glfw.callback_stack.WindowCloseCallbackStack;
import me.decce.ixeris.glfw.callback_stack.WindowContentScaleCallbackStack;
import me.decce.ixeris.glfw.callback_stack.WindowFocusCallbackStack;
import me.decce.ixeris.glfw.callback_stack.WindowIconifyCallbackStack;
import me.decce.ixeris.glfw.callback_stack.WindowMaximizeCallbackStack;
import me.decce.ixeris.glfw.callback_stack.WindowPosCallbackStack;
import me.decce.ixeris.glfw.callback_stack.WindowRefreshCallbackStack;
import me.decce.ixeris.glfw.callback_stack.WindowSizeCallbackStack;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwSetCharCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetCharCallback(long window, GLFWCharCallbackI cbfun, CallbackInfoReturnable<GLFWCharCallbackI> cir) {
        var stack = CharCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.charCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetCharCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCharCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = CharCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.charCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetCharModsCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetCharModsCallback(long window, GLFWCharModsCallbackI cbfun, CallbackInfoReturnable<GLFWCharModsCallbackI> cir) {
        var stack = CharModsCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.charModsCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetCharModsCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCharModsCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = CharModsCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.charModsCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetCursorEnterCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetCursorEnterCallback(long window, GLFWCursorEnterCallbackI cbfun, CallbackInfoReturnable<GLFWCursorEnterCallbackI> cir) {
        var stack = CursorEnterCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.cursorEnterCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetCursorEnterCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCursorEnterCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = CursorEnterCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.cursorEnterCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetCursorPosCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetCursorPosCallback(long window, GLFWCursorPosCallbackI cbfun, CallbackInfoReturnable<GLFWCursorPosCallbackI> cir) {
        var stack = CursorPosCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.cursorPosCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetCursorPosCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetCursorPosCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = CursorPosCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.cursorPosCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetDropCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetDropCallback(long window, GLFWDropCallbackI cbfun, CallbackInfoReturnable<GLFWDropCallbackI> cir) {
        var stack = DropCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.dropCallback) {
            stack.topCallback = cbfun;
        }
    }
    
    @Inject(method = "nglfwSetDropCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetDropCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = DropCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.dropCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetErrorCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetErrorCallback(GLFWErrorCallbackI cbfun, CallbackInfoReturnable<GLFWErrorCallbackI> cir) {
        var stack = ErrorCallbackStack.get();
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.errorCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetErrorCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetErrorCallback(long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = ErrorCallbackStack.get();
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.errorCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetFramebufferSizeCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI cbfun, CallbackInfoReturnable<GLFWFramebufferSizeCallbackI> cir) {
        var stack = FramebufferSizeCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.framebufferSizeCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetFramebufferSizeCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetFramebufferSizeCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = FramebufferSizeCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.framebufferSizeCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetKeyCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetKeyCallback(long window, GLFWKeyCallbackI cbfun, CallbackInfoReturnable<GLFWKeyCallbackI> cir) {
        var stack = KeyCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.keyCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetKeyCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetKeyCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = KeyCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.keyCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetMonitorCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetMonitorCallback(GLFWMonitorCallbackI cbfun, CallbackInfoReturnable<GLFWMonitorCallbackI> cir) {
        var stack = MonitorCallbackStack.get();
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.monitorCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetMonitorCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetMonitorCallback(long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = MonitorCallbackStack.get();
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.monitorCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetMouseButtonCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetMouseButtonCallback(long window, GLFWMouseButtonCallbackI cbfun, CallbackInfoReturnable<GLFWMouseButtonCallbackI> cir) {
        var stack = MouseButtonCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.mouseButtonCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetMouseButtonCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetMouseButtonCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = MouseButtonCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.mouseButtonCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetScrollCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetScrollCallback(long window, GLFWScrollCallbackI cbfun, CallbackInfoReturnable<GLFWScrollCallbackI> cir) {
        var stack = ScrollCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.scrollCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetScrollCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetScrollCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = ScrollCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.scrollCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetWindowCloseCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetWindowCloseCallback(long window, GLFWWindowCloseCallbackI cbfun, CallbackInfoReturnable<GLFWWindowCloseCallbackI> cir) {
        var stack = WindowCloseCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowCloseCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetWindowCloseCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowCloseCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = WindowCloseCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowCloseCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetWindowContentScaleCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI cbfun, CallbackInfoReturnable<GLFWWindowContentScaleCallbackI> cir) {
        var stack = WindowContentScaleCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowContentScaleCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetWindowContentScaleCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowContentScaleCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = WindowContentScaleCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowContentScaleCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetWindowFocusCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetWindowFocusCallback(long window, GLFWWindowFocusCallbackI cbfun, CallbackInfoReturnable<GLFWWindowFocusCallbackI> cir) {
        var stack = WindowFocusCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowFocusCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetWindowFocusCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowFocusCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = WindowFocusCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowFocusCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetWindowIconifyCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI cbfun, CallbackInfoReturnable<GLFWWindowIconifyCallbackI> cir) {
        var stack = WindowIconifyCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowIconifyCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetWindowIconifyCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowIconifyCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = WindowIconifyCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowIconifyCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetWindowMaximizeCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI cbfun, CallbackInfoReturnable<GLFWWindowMaximizeCallbackI> cir) {
        var stack = WindowMaximizeCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowMaximizeCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetWindowMaximizeCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowMaximizeCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = WindowMaximizeCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowMaximizeCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetWindowPosCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetWindowPosCallback(long window, GLFWWindowPosCallbackI cbfun, CallbackInfoReturnable<GLFWWindowPosCallbackI> cir) {
        var stack = WindowPosCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowPosCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetWindowPosCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowPosCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = WindowPosCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowPosCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetWindowRefreshCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI cbfun, CallbackInfoReturnable<GLFWWindowRefreshCallbackI> cir) {
        var stack = WindowRefreshCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowRefreshCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetWindowRefreshCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowRefreshCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = WindowRefreshCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowRefreshCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }

    @Inject(method = "glfwSetWindowSizeCallback", at = @At("RETURN"))
    private static void ixeris$glfwSetWindowSizeCallback(long window, GLFWWindowSizeCallbackI cbfun, CallbackInfoReturnable<GLFWWindowSizeCallbackI> cir) {
        var stack = WindowSizeCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowSizeCallback) {
            stack.topCallback = cbfun;
        }
    } 
    
    @Inject(method = "nglfwSetWindowSizeCallback", at = @At("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetWindowSizeCallback(long window, long cbfun, CallbackInfoReturnable<Long> cir) {
        var stack = WindowSizeCallbackStack.get(window);
        if (stack.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks.windowSizeCallback.address()) {
            stack.push(cbfun);
            cir.setReturnValue(stack.update());
        }
    }}
