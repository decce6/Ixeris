/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;

public interface RedirectedGLFWWindowFocusCallbackI extends GLFWWindowFocusCallbackI {
    static RedirectedGLFWWindowFocusCallbackI wrap(GLFWWindowFocusCallbackI i) {
        return (window, focused) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, focused));
    }
}
