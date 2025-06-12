/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

public interface RedirectedGLFWWindowCloseCallbackI extends GLFWWindowCloseCallbackI {
    static RedirectedGLFWWindowCloseCallbackI wrap(GLFWWindowCloseCallbackI i) {
        return (window) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window));
    }
}
