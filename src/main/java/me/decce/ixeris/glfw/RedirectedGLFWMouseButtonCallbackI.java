/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public interface RedirectedGLFWMouseButtonCallbackI extends GLFWMouseButtonCallbackI {
    static RedirectedGLFWMouseButtonCallbackI wrap(GLFWMouseButtonCallbackI i) {
        return (window, button, action, mods) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, button, action, mods));
    }
}
