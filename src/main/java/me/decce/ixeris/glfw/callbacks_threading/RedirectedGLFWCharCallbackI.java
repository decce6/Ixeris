/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWCharCallbackI;

public interface RedirectedGLFWCharCallbackI extends GLFWCharCallbackI {
    static RedirectedGLFWCharCallbackI wrap(GLFWCharCallbackI i) {
        return (window, codepoint) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, codepoint));
    }
}
