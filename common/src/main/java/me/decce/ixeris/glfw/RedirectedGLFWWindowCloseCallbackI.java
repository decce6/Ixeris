/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

public interface RedirectedGLFWWindowCloseCallbackI extends GLFWWindowCloseCallbackI {
    static RedirectedGLFWWindowCloseCallbackI wrap(GLFWWindowCloseCallbackI i) {
        return (window) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window));
    }
}
