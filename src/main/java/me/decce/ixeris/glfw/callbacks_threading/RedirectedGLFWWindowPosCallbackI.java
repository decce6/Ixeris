/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;

public interface RedirectedGLFWWindowPosCallbackI extends GLFWWindowPosCallbackI {
    static RedirectedGLFWWindowPosCallbackI wrap(GLFWWindowPosCallbackI i) {
        return (window, xpos, ypos) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, xpos, ypos));
    }
}
