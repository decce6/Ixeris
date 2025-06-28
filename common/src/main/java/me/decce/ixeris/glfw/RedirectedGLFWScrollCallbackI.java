/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public interface RedirectedGLFWScrollCallbackI extends GLFWScrollCallbackI {
    static RedirectedGLFWScrollCallbackI wrap(GLFWScrollCallbackI i) {
        return (window, xoffset, yoffset) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, xoffset, yoffset));
    }
}
