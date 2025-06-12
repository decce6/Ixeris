/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;

public interface RedirectedGLFWWindowFocusCallbackI extends GLFWWindowFocusCallbackI {
    static RedirectedGLFWWindowFocusCallbackI wrap(GLFWWindowFocusCallbackI i) {
        return (window, focused) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window, focused));
    }
}
