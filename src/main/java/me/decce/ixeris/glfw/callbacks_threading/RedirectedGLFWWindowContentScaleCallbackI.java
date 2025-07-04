/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;

public interface RedirectedGLFWWindowContentScaleCallbackI extends GLFWWindowContentScaleCallbackI {
    static RedirectedGLFWWindowContentScaleCallbackI wrap(GLFWWindowContentScaleCallbackI i) {
        return (window, xscale, yscale) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, xscale, yscale));
    }
}
