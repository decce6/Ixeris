/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWCharCallbackI;

public interface RedirectedGLFWCharCallbackI extends GLFWCharCallbackI {
    static RedirectedGLFWCharCallbackI wrap(GLFWCharCallbackI i) {
        return (window, codepoint) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, codepoint));
    }
}
