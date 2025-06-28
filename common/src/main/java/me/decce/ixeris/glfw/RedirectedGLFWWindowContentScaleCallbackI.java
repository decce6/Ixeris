/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;

public interface RedirectedGLFWWindowContentScaleCallbackI extends GLFWWindowContentScaleCallbackI {
    static RedirectedGLFWWindowContentScaleCallbackI wrap(GLFWWindowContentScaleCallbackI i) {
        return (window, xscale, yscale) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, xscale, yscale));
    }
}
