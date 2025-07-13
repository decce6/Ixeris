/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import me.decce.ixeris.util.MemoryHelper;
import org.lwjgl.glfw.GLFWErrorCallbackI;

public interface RedirectedGLFWErrorCallbackI extends GLFWErrorCallbackI {
    static RedirectedGLFWErrorCallbackI wrap(GLFWErrorCallbackI i) {
        return (error, description) -> {
            long descriptionCopy = MemoryHelper.deepCopy(description);
            RenderThreadDispatcher.runLater(() -> {
                i.invoke(error, descriptionCopy);
                MemoryHelper.free(descriptionCopy);
            });
        };
    }
}
