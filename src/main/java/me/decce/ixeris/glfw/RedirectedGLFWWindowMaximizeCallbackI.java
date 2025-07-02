/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;

public interface RedirectedGLFWWindowMaximizeCallbackI extends GLFWWindowMaximizeCallbackI {
    static RedirectedGLFWWindowMaximizeCallbackI wrap(GLFWWindowMaximizeCallbackI i) {
        return (window, maximized) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, maximized));
    }
}
