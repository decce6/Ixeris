/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedBooleanArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;

public class RedirectedGLFWCursorEnterCallback implements GLFWCursorEnterCallbackI {
    private final GLFWCursorEnterCallbackI callback;
    private final RedirectedGLFWCursorEnterCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedBooleanArrayFIFOQueue entered = SynchronizedBooleanArrayFIFOQueue.create();
    
    private RedirectedGLFWCursorEnterCallback(GLFWCursorEnterCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWCursorEnterCallbackRunnable(this);
    }

    public static RedirectedGLFWCursorEnterCallback wrap(GLFWCursorEnterCallbackI callback) {
        return new RedirectedGLFWCursorEnterCallback(callback);
    }

    @Override
    public void invoke(long window, boolean entered) {
        this.window.enqueue(window);
        this.entered.enqueue(entered);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWCursorEnterCallbackRunnable implements Runnable {
        private final RedirectedGLFWCursorEnterCallback callback;

        public RedirectedGLFWCursorEnterCallbackRunnable(RedirectedGLFWCursorEnterCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.entered.dequeue());
        }
    }
}
