/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public interface RedirectedGLFWScrollCallbackI extends GLFWScrollCallbackI {
    static RedirectedGLFWScrollCallbackI wrap(GLFWScrollCallbackI i) {
        return (window, xoffset, yoffset) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, xoffset, yoffset));
    }
}
