/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWCharModsCallbackI;

public interface RedirectedGLFWCharModsCallbackI extends GLFWCharModsCallbackI {
    static RedirectedGLFWCharModsCallbackI wrap(GLFWCharModsCallbackI i) {
        return (window, codepoint, mods) ->
            RenderThreadDispatcher.runLater(() -> i.invoke(window, codepoint, mods));
    }
}
