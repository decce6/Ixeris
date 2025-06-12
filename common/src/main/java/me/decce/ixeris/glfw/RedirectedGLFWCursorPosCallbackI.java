/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public interface RedirectedGLFWCursorPosCallbackI extends GLFWCursorPosCallbackI {
    static RedirectedGLFWCursorPosCallbackI wrap(GLFWCursorPosCallbackI i) {
        return (window, xpos, ypos) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, xpos, ypos));
    }
}
