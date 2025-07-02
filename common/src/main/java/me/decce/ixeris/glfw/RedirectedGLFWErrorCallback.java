/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedIntArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWErrorCallbackI;

public class RedirectedGLFWErrorCallback implements GLFWErrorCallbackI {
    private final GLFWErrorCallbackI callback;
    private final RedirectedGLFWErrorCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedIntArrayFIFOQueue error = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedLongArrayFIFOQueue description = SynchronizedLongArrayFIFOQueue.create();
    
    private RedirectedGLFWErrorCallback(GLFWErrorCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWErrorCallbackRunnable(this);
    }

    public static RedirectedGLFWErrorCallback wrap(GLFWErrorCallbackI callback) {
        return new RedirectedGLFWErrorCallback(callback);
    }

    @Override
    public void invoke(int error, long description) {
        this.error.enqueue(error);
        this.description.enqueue(description);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWErrorCallbackRunnable implements Runnable {
        private final RedirectedGLFWErrorCallback callback;

        public RedirectedGLFWErrorCallbackRunnable(RedirectedGLFWErrorCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.error.dequeue(), callback.description.dequeue());
        }
    }
}
