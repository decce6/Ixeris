/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;

public interface RedirectedGLFWWindowRefreshCallbackI extends GLFWWindowRefreshCallbackI {
    static RedirectedGLFWWindowRefreshCallbackI wrap(GLFWWindowRefreshCallbackI i) {
        return (window) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window));
    }
}
