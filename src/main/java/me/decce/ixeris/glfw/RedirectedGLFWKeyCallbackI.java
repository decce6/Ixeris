/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public interface RedirectedGLFWKeyCallbackI extends GLFWKeyCallbackI {
    static RedirectedGLFWKeyCallbackI wrap(GLFWKeyCallbackI i) {
        return (window, key, scancode, action, mods) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, key, scancode, action, mods));
    }
}
