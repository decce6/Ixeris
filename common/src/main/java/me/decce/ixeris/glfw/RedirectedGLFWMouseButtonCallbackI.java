/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public interface RedirectedGLFWMouseButtonCallbackI extends GLFWMouseButtonCallbackI {
    static RedirectedGLFWMouseButtonCallbackI wrap(GLFWMouseButtonCallbackI i) {
        return (window, button, action, mods) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, button, action, mods));
    }
}
