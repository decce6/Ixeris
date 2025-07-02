/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedIntArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWCharCallbackI;

public class RedirectedGLFWCharCallback implements GLFWCharCallbackI {
    private final GLFWCharCallbackI callback;
    private final RedirectedGLFWCharCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue codepoint = SynchronizedIntArrayFIFOQueue.create();
    
    private RedirectedGLFWCharCallback(GLFWCharCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWCharCallbackRunnable(this);
    }

    public static RedirectedGLFWCharCallback wrap(GLFWCharCallbackI callback) {
        return new RedirectedGLFWCharCallback(callback);
    }

    @Override
    public void invoke(long window, int codepoint) {
        this.window.enqueue(window);
        this.codepoint.enqueue(codepoint);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWCharCallbackRunnable implements Runnable {
        private final RedirectedGLFWCharCallback callback;

        public RedirectedGLFWCharCallbackRunnable(RedirectedGLFWCharCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.codepoint.dequeue());
        }
    }
}
