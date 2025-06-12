/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;

public interface RedirectedGLFWFramebufferSizeCallbackI extends GLFWFramebufferSizeCallbackI {
    static RedirectedGLFWFramebufferSizeCallbackI wrap(GLFWFramebufferSizeCallbackI i) {
        return (window, width, height) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, width, height));
    }
}
