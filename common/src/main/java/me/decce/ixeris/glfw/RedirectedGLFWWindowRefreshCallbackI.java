/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;

public interface RedirectedGLFWWindowRefreshCallbackI extends GLFWWindowRefreshCallbackI {
    static RedirectedGLFWWindowRefreshCallbackI wrap(GLFWWindowRefreshCallbackI i) {
        return (window) ->
            RenderSystem.recordRenderCall(() -> i.invoke(window));
    }
}
