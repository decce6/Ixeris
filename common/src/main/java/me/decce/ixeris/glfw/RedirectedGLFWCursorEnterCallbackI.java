/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;

public interface RedirectedGLFWCursorEnterCallbackI extends GLFWCursorEnterCallbackI {
    static RedirectedGLFWCursorEnterCallbackI wrap(GLFWCursorEnterCallbackI i) {
        return (window, entered) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, entered));
    }
}
