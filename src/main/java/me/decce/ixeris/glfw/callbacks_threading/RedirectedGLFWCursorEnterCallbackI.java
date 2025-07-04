/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;

public interface RedirectedGLFWCursorEnterCallbackI extends GLFWCursorEnterCallbackI {
    static RedirectedGLFWCursorEnterCallbackI wrap(GLFWCursorEnterCallbackI i) {
        return (window, entered) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, entered));
    }
}
