/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;

public interface RedirectedGLFWWindowMaximizeCallbackI extends GLFWWindowMaximizeCallbackI {
    static RedirectedGLFWWindowMaximizeCallbackI wrap(GLFWWindowMaximizeCallbackI i) {
        return (window, maximized) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, maximized));
    }
}
