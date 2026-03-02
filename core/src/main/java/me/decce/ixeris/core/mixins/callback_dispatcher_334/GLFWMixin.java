/*
Auto-generated. See the generator directory in project root.
*/

package me.decce.ixeris.core.mixins.callback_dispatcher_334;

import me.decce.ixeris.core.glfw.callback_dispatcher.*;
import org.lwjgl.glfw.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
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
}