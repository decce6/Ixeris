/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWErrorCallbackI;

public interface RedirectedGLFWErrorCallbackI extends GLFWErrorCallbackI {
    static RedirectedGLFWErrorCallbackI wrap(GLFWErrorCallbackI i) {
        return (error, description) ->
                Ixeris.runLaterOnRenderThread(() -> i.invoke(error, description));
    }
}
