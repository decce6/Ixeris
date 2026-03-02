//? if forge {
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

/^
Auto-generated. See the generator directory in project root.
^/

package me.decce.ixeris.forge.transformers.callback_dispatcher_334;

import me.decce.ixeris.core.glfw.callback_dispatcher.*;
import me.decce.ixeris.core.glfw.callback_dispatcher._334.CommonCallbacks_334;
import org.lwjgl.glfw.*;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;

import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInline @CInject(method = "glfwSetIMEStatusCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetIMEStatusCallback(long window, GLFWIMEStatusCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = IMEStatusCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetIMEStatusCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetIMEStatusCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = IMEStatusCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks_334.iMEStatusCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetPreeditCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetPreeditCallback(long window, GLFWPreeditCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = PreeditCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetPreeditCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetPreeditCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = PreeditCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks_334.preeditCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }

    @CInline @CInject(method = "glfwSetPreeditCandidateCallback", target = @CTarget("HEAD"))
    private static void ixeris$glfwSetPreeditCandidateCallback(long window, GLFWPreeditCandidateCallbackI cbfun, InjectionCallback cir) {
        var dispatcher = PreeditCandidateCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        dispatcher.update(cbfun);
    }

    @CInline @CInject(method = "nglfwSetPreeditCandidateCallback", target = @CTarget("RETURN"), cancellable = true)
    private static void ixeris$nglfwSetPreeditCandidateCallback(long window, long cbfun, InjectionCallback cir) {
        var dispatcher = PreeditCandidateCallbackDispatcher.get(window);
        if (dispatcher.suppressChecks) {
            return;
        }
        if (cbfun != CommonCallbacks_334.preeditCandidateCallback.address()) {
            cir.setReturnValue(dispatcher.update(cbfun));
        }
    }
}
*///?}