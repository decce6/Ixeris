/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import me.decce.ixeris.util.MemoryHelper;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWDropCallbackI;

public interface RedirectedGLFWDropCallbackI extends GLFWDropCallbackI {
    static RedirectedGLFWDropCallbackI wrap(GLFWDropCallbackI i) {
        return (window, count, names) -> {
            long namesCopy = MemoryHelper.deepCopy(names, count, GLFWDropCallback::getName);
            RenderThreadDispatcher.runLater(() -> {
                i.invoke(window, count, namesCopy);
                MemoryHelper.free(namesCopy, count);
            });
        };
    }
}
