/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;

public interface RedirectedGLFWWindowPosCallbackI extends GLFWWindowPosCallbackI {
    static RedirectedGLFWWindowPosCallbackI wrap(GLFWWindowPosCallbackI i) {
        return (window, xpos, ypos) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, xpos, ypos));
    }
}
