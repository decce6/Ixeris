/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedIntArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWDropCallbackI;

public class RedirectedGLFWDropCallback implements GLFWDropCallbackI {
    private final GLFWDropCallbackI callback;
    private final RedirectedGLFWDropCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue count = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedLongArrayFIFOQueue names = SynchronizedLongArrayFIFOQueue.create();
    
    private RedirectedGLFWDropCallback(GLFWDropCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWDropCallbackRunnable(this);
    }

    public static RedirectedGLFWDropCallback wrap(GLFWDropCallbackI callback) {
        return new RedirectedGLFWDropCallback(callback);
    }

    @Override
    public void invoke(long window, int count, long names) {
        this.window.enqueue(window);
        this.count.enqueue(count);
        this.names.enqueue(names);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWDropCallbackRunnable implements Runnable {
        private final RedirectedGLFWDropCallback callback;

        public RedirectedGLFWDropCallbackRunnable(RedirectedGLFWDropCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.count.dequeue(), callback.names.dequeue());
        }
    }
}
