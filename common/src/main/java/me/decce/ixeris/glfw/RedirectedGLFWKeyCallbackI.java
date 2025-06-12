/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public interface RedirectedGLFWKeyCallbackI extends GLFWKeyCallbackI {
    static RedirectedGLFWKeyCallbackI wrap(GLFWKeyCallbackI i) {
        return (window, key, scancode, action, mods) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, key, scancode, action, mods));
    }
}
