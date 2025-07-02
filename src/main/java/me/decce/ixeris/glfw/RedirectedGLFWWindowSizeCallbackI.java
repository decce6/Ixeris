/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

public interface RedirectedGLFWWindowSizeCallbackI extends GLFWWindowSizeCallbackI {
    static RedirectedGLFWWindowSizeCallbackI wrap(GLFWWindowSizeCallbackI i) {
        return (window, width, height) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, width, height));
    }
}
