/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;

public class RedirectedGLFWWindowRefreshCallback implements GLFWWindowRefreshCallbackI {
    private final GLFWWindowRefreshCallbackI callback;
    private final RedirectedGLFWWindowRefreshCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    
    private RedirectedGLFWWindowRefreshCallback(GLFWWindowRefreshCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWWindowRefreshCallbackRunnable(this);
    }

    public static RedirectedGLFWWindowRefreshCallback wrap(GLFWWindowRefreshCallbackI callback) {
        return new RedirectedGLFWWindowRefreshCallback(callback);
    }

    @Override
    public void invoke(long window) {
        this.window.enqueue(window);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWWindowRefreshCallbackRunnable implements Runnable {
        private final RedirectedGLFWWindowRefreshCallback callback;

        public RedirectedGLFWWindowRefreshCallbackRunnable(RedirectedGLFWWindowRefreshCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue());
        }
    }
}
