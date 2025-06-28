/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;

public interface RedirectedGLFWWindowFocusCallbackI extends GLFWWindowFocusCallbackI {
    static RedirectedGLFWWindowFocusCallbackI wrap(GLFWWindowFocusCallbackI i) {
        return (window, focused) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, focused));
    }
}
