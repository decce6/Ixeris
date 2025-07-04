/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWMonitorCallbackI;

public interface RedirectedGLFWMonitorCallbackI extends GLFWMonitorCallbackI {
    static RedirectedGLFWMonitorCallbackI wrap(GLFWMonitorCallbackI i) {
        return (monitor, event) ->
                RenderThreadDispatcher.runLater(() -> i.invoke(monitor, event));
    }
}
