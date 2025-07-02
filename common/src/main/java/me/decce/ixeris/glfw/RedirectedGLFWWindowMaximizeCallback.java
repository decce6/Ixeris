/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedBooleanArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;

public class RedirectedGLFWWindowMaximizeCallback implements GLFWWindowMaximizeCallbackI {
    private final GLFWWindowMaximizeCallbackI callback;
    private final RedirectedGLFWWindowMaximizeCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedBooleanArrayFIFOQueue maximized = SynchronizedBooleanArrayFIFOQueue.create();
    
    private RedirectedGLFWWindowMaximizeCallback(GLFWWindowMaximizeCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWWindowMaximizeCallbackRunnable(this);
    }

    public static RedirectedGLFWWindowMaximizeCallback wrap(GLFWWindowMaximizeCallbackI callback) {
        return new RedirectedGLFWWindowMaximizeCallback(callback);
    }

    @Override
    public void invoke(long window, boolean maximized) {
        this.window.enqueue(window);
        this.maximized.enqueue(maximized);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWWindowMaximizeCallbackRunnable implements Runnable {
        private final RedirectedGLFWWindowMaximizeCallback callback;

        public RedirectedGLFWWindowMaximizeCallbackRunnable(RedirectedGLFWWindowMaximizeCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.maximized.dequeue());
        }
    }
}
