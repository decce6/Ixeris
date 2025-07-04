/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWDropCallbackI;

public interface RedirectedGLFWDropCallbackI extends GLFWDropCallbackI {
    static RedirectedGLFWDropCallbackI wrap(GLFWDropCallbackI i) {
        return (window, count, names) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, count, names));
    }
}
