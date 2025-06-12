/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWCharCallbackI;

public interface RedirectedGLFWCharCallbackI extends GLFWCharCallbackI {
    static RedirectedGLFWCharCallbackI wrap(GLFWCharCallbackI i) {
        return (window, codepoint) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, codepoint));
    }
}
