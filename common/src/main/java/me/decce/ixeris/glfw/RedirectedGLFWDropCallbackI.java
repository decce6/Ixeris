/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWDropCallbackI;

public interface RedirectedGLFWDropCallbackI extends GLFWDropCallbackI {
    static RedirectedGLFWDropCallbackI wrap(GLFWDropCallbackI i) {
        return (window, count, names) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, count, names));
    }
}
