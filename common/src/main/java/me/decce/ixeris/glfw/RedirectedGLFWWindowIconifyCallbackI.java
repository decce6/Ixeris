/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;

public interface RedirectedGLFWWindowIconifyCallbackI extends GLFWWindowIconifyCallbackI {
    static RedirectedGLFWWindowIconifyCallbackI wrap(GLFWWindowIconifyCallbackI i) {
        return (window, iconified) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, iconified));
    }
}
