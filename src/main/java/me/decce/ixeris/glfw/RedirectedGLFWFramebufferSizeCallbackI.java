/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;

public interface RedirectedGLFWFramebufferSizeCallbackI extends GLFWFramebufferSizeCallbackI {
    static RedirectedGLFWFramebufferSizeCallbackI wrap(GLFWFramebufferSizeCallbackI i) {
        return (window, width, height) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, width, height));
    }
}
