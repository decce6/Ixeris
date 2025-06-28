/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;

public interface RedirectedGLFWWindowRefreshCallbackI extends GLFWWindowRefreshCallbackI {
    static RedirectedGLFWWindowRefreshCallbackI wrap(GLFWWindowRefreshCallbackI i) {
        return (window) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window));
    }
}
