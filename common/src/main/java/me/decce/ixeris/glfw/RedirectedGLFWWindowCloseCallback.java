/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

public class RedirectedGLFWWindowCloseCallback implements GLFWWindowCloseCallbackI {
    private final GLFWWindowCloseCallbackI callback;
    private final RedirectedGLFWWindowCloseCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    
    private RedirectedGLFWWindowCloseCallback(GLFWWindowCloseCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWWindowCloseCallbackRunnable(this);
    }

    public static RedirectedGLFWWindowCloseCallback wrap(GLFWWindowCloseCallbackI callback) {
        return new RedirectedGLFWWindowCloseCallback(callback);
    }

    @Override
    public void invoke(long window) {
        this.window.enqueue(window);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWWindowCloseCallbackRunnable implements Runnable {
        private final RedirectedGLFWWindowCloseCallback callback;

        public RedirectedGLFWWindowCloseCallbackRunnable(RedirectedGLFWWindowCloseCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue());
        }
    }
}
