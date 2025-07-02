/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedDoubleArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public class RedirectedGLFWScrollCallback implements GLFWScrollCallbackI {
    private final GLFWScrollCallbackI callback;
    private final RedirectedGLFWScrollCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedDoubleArrayFIFOQueue xoffset = SynchronizedDoubleArrayFIFOQueue.create();
    private final SynchronizedDoubleArrayFIFOQueue yoffset = SynchronizedDoubleArrayFIFOQueue.create();
    
    private RedirectedGLFWScrollCallback(GLFWScrollCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWScrollCallbackRunnable(this);
    }

    public static RedirectedGLFWScrollCallback wrap(GLFWScrollCallbackI callback) {
        return new RedirectedGLFWScrollCallback(callback);
    }

    @Override
    public void invoke(long window, double xoffset, double yoffset) {
        this.window.enqueue(window);
        this.xoffset.enqueue(xoffset);
        this.yoffset.enqueue(yoffset);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWScrollCallbackRunnable implements Runnable {
        private final RedirectedGLFWScrollCallback callback;

        public RedirectedGLFWScrollCallbackRunnable(RedirectedGLFWScrollCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.xoffset.dequeue(), callback.yoffset.dequeue());
        }
    }
}
