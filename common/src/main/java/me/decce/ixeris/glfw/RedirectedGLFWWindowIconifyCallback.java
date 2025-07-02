/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedBooleanArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;

public class RedirectedGLFWWindowIconifyCallback implements GLFWWindowIconifyCallbackI {
    private final GLFWWindowIconifyCallbackI callback;
    private final RedirectedGLFWWindowIconifyCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedBooleanArrayFIFOQueue iconified = SynchronizedBooleanArrayFIFOQueue.create();
    
    private RedirectedGLFWWindowIconifyCallback(GLFWWindowIconifyCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWWindowIconifyCallbackRunnable(this);
    }

    public static RedirectedGLFWWindowIconifyCallback wrap(GLFWWindowIconifyCallbackI callback) {
        return new RedirectedGLFWWindowIconifyCallback(callback);
    }

    @Override
    public void invoke(long window, boolean iconified) {
        this.window.enqueue(window);
        this.iconified.enqueue(iconified);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWWindowIconifyCallbackRunnable implements Runnable {
        private final RedirectedGLFWWindowIconifyCallback callback;

        public RedirectedGLFWWindowIconifyCallbackRunnable(RedirectedGLFWWindowIconifyCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.iconified.dequeue());
        }
    }
}
