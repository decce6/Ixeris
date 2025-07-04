/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;

public interface RedirectedGLFWWindowIconifyCallbackI extends GLFWWindowIconifyCallbackI {
    static RedirectedGLFWWindowIconifyCallbackI wrap(GLFWWindowIconifyCallbackI i) {
        return (window, iconified) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, iconified));
    }
}
