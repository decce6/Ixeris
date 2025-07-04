/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

public interface RedirectedGLFWWindowCloseCallbackI extends GLFWWindowCloseCallbackI {
    static RedirectedGLFWWindowCloseCallbackI wrap(GLFWWindowCloseCallbackI i) {
        return (window) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window));
    }
}
