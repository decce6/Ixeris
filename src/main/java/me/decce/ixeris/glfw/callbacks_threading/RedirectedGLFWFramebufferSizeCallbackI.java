/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;

public interface RedirectedGLFWFramebufferSizeCallbackI extends GLFWFramebufferSizeCallbackI {
    static RedirectedGLFWFramebufferSizeCallbackI wrap(GLFWFramebufferSizeCallbackI i) {
        return (window, width, height) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, width, height));
    }
}
