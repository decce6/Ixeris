/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.system.MemoryUtil;

public interface RedirectedGLFWErrorCallbackI extends GLFWErrorCallbackI {
    static RedirectedGLFWErrorCallbackI wrap(GLFWErrorCallbackI i) {
        return (error, description) -> {
            // Copy the description, as it would be otherwise unavailable when the callback is run
            String str = MemoryUtil.memUTF8(description);
            long address = MemoryUtil.memAddress(MemoryUtil.memUTF8(str));
            Ixeris.runLaterOnRenderThread(() -> {
                i.invoke(error, address);
                MemoryUtil.nmemFree(address);
            });
        };
    }
}
