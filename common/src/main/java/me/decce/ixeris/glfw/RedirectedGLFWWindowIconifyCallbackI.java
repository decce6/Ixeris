/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;

public interface RedirectedGLFWWindowIconifyCallbackI extends GLFWWindowIconifyCallbackI {
    static RedirectedGLFWWindowIconifyCallbackI wrap(GLFWWindowIconifyCallbackI i) {
        return (window, iconified) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, iconified));
    }
}
