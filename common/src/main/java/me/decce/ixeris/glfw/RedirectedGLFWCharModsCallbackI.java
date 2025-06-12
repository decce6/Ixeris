/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWCharModsCallbackI;

public interface RedirectedGLFWCharModsCallbackI extends GLFWCharModsCallbackI {
    static RedirectedGLFWCharModsCallbackI wrap(GLFWCharModsCallbackI i) {
        return (window, codepoint, mods) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, codepoint, mods));
    }
}
