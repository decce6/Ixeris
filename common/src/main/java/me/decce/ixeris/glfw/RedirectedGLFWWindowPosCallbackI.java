/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;

public interface RedirectedGLFWWindowPosCallbackI extends GLFWWindowPosCallbackI {
    static RedirectedGLFWWindowPosCallbackI wrap(GLFWWindowPosCallbackI i) {
        return (window, xpos, ypos) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, xpos, ypos));
    }
}
