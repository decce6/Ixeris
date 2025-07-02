/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedIntArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;

public class RedirectedGLFWFramebufferSizeCallback implements GLFWFramebufferSizeCallbackI {
    private final GLFWFramebufferSizeCallbackI callback;
    private final RedirectedGLFWFramebufferSizeCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue width = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue height = SynchronizedIntArrayFIFOQueue.create();
    
    private RedirectedGLFWFramebufferSizeCallback(GLFWFramebufferSizeCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWFramebufferSizeCallbackRunnable(this);
    }

    public static RedirectedGLFWFramebufferSizeCallback wrap(GLFWFramebufferSizeCallbackI callback) {
        return new RedirectedGLFWFramebufferSizeCallback(callback);
    }

    @Override
    public void invoke(long window, int width, int height) {
        this.window.enqueue(window);
        this.width.enqueue(width);
        this.height.enqueue(height);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWFramebufferSizeCallbackRunnable implements Runnable {
        private final RedirectedGLFWFramebufferSizeCallback callback;

        public RedirectedGLFWFramebufferSizeCallbackRunnable(RedirectedGLFWFramebufferSizeCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.width.dequeue(), callback.height.dequeue());
        }
    }
}
