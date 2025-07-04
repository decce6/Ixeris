/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callbacks_threading;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public interface RedirectedGLFWCursorPosCallbackI extends GLFWCursorPosCallbackI {
    static RedirectedGLFWCursorPosCallbackI wrap(GLFWCursorPosCallbackI i) {
        return (window, xpos, ypos) ->
                RenderThreadDispatcher.runLater((CursorPosRunnable)() -> {
                    i.invoke(window, xpos, ypos);
                });
    }

    @FunctionalInterface
    interface CursorPosRunnable extends Runnable {
    }
}
