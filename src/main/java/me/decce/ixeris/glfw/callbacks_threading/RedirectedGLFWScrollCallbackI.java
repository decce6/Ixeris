/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public interface RedirectedGLFWScrollCallbackI extends GLFWScrollCallbackI {
    static RedirectedGLFWScrollCallbackI wrap(GLFWScrollCallbackI i) {
        return (window, xoffset, yoffset) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, xoffset, yoffset));
    }
}
