/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;

public interface RedirectedGLFWCursorEnterCallbackI extends GLFWCursorEnterCallbackI {
    static RedirectedGLFWCursorEnterCallbackI wrap(GLFWCursorEnterCallbackI i) {
        return (window, entered) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, entered));
    }
}
