/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWDropCallbackI;

public interface RedirectedGLFWDropCallbackI extends GLFWDropCallbackI {
    static RedirectedGLFWDropCallbackI wrap(GLFWDropCallbackI i) {
        return (window, count, names) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, count, names));
    }
}
