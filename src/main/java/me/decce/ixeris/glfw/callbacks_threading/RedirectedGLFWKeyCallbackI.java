/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public interface RedirectedGLFWKeyCallbackI extends GLFWKeyCallbackI {
    static RedirectedGLFWKeyCallbackI wrap(GLFWKeyCallbackI i) {
        return (window, key, scancode, action, mods) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, key, scancode, action, mods));
    }
}
