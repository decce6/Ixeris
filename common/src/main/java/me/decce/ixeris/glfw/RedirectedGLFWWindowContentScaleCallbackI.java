/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;

public interface RedirectedGLFWWindowContentScaleCallbackI extends GLFWWindowContentScaleCallbackI {
    static RedirectedGLFWWindowContentScaleCallbackI wrap(GLFWWindowContentScaleCallbackI i) {
        return (window, xscale, yscale) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, xscale, yscale));
    }
}
