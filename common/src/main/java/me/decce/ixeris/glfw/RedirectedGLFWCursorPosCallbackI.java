/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public interface RedirectedGLFWCursorPosCallbackI extends GLFWCursorPosCallbackI {
    static RedirectedGLFWCursorPosCallbackI wrap(GLFWCursorPosCallbackI i) {
        return (window, xpos, ypos) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, xpos, ypos));
    }
}
