/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public interface RedirectedGLFWMouseButtonCallbackI extends GLFWMouseButtonCallbackI {
    static RedirectedGLFWMouseButtonCallbackI wrap(GLFWMouseButtonCallbackI i) {
        return (window, button, action, mods) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, button, action, mods));
    }
}
