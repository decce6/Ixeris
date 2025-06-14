/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWCharModsCallbackI;

public interface RedirectedGLFWCharModsCallbackI extends GLFWCharModsCallbackI {
    static RedirectedGLFWCharModsCallbackI wrap(GLFWCharModsCallbackI i) {
        return (window, codepoint, mods) ->
            Ixeris.runLaterOnRenderThread(() -> i.invoke(window, codepoint, mods));
    }
}
