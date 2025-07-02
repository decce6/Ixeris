/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedIntArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

public class RedirectedGLFWWindowSizeCallback implements GLFWWindowSizeCallbackI {
    private final GLFWWindowSizeCallbackI callback;
    private final RedirectedGLFWWindowSizeCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue width = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue height = SynchronizedIntArrayFIFOQueue.create();
    
    private RedirectedGLFWWindowSizeCallback(GLFWWindowSizeCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWWindowSizeCallbackRunnable(this);
    }

    public static RedirectedGLFWWindowSizeCallback wrap(GLFWWindowSizeCallbackI callback) {
        return new RedirectedGLFWWindowSizeCallback(callback);
    }

    @Override
    public void invoke(long window, int width, int height) {
        this.window.enqueue(window);
        this.width.enqueue(width);
        this.height.enqueue(height);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWWindowSizeCallbackRunnable implements Runnable {
        private final RedirectedGLFWWindowSizeCallback callback;

        public RedirectedGLFWWindowSizeCallbackRunnable(RedirectedGLFWWindowSizeCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.width.dequeue(), callback.height.dequeue());
        }
    }
}
