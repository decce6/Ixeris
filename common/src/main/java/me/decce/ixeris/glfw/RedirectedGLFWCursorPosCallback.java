/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedDoubleArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public class RedirectedGLFWCursorPosCallback implements GLFWCursorPosCallbackI {
    private final GLFWCursorPosCallbackI callback;
    private final RedirectedGLFWCursorPosCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create(128);
    private final SynchronizedDoubleArrayFIFOQueue xpos = SynchronizedDoubleArrayFIFOQueue.create(128);
    private final SynchronizedDoubleArrayFIFOQueue ypos = SynchronizedDoubleArrayFIFOQueue.create(128);
    
    private RedirectedGLFWCursorPosCallback(GLFWCursorPosCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWCursorPosCallbackRunnable(this);
    }

    public static RedirectedGLFWCursorPosCallback wrap(GLFWCursorPosCallbackI callback) {
        return new RedirectedGLFWCursorPosCallback(callback);
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        this.window.enqueue(window);
        this.xpos.enqueue(xpos);
        this.ypos.enqueue(ypos);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWCursorPosCallbackRunnable implements Runnable {
        private final RedirectedGLFWCursorPosCallback callback;

        public RedirectedGLFWCursorPosCallbackRunnable(RedirectedGLFWCursorPosCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.xpos.dequeue(), callback.ypos.dequeue());
        }
    }
}
