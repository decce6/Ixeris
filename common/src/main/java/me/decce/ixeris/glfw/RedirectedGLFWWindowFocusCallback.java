/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedBooleanArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;

public class RedirectedGLFWWindowFocusCallback implements GLFWWindowFocusCallbackI {
    private final GLFWWindowFocusCallbackI callback;
    private final RedirectedGLFWWindowFocusCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedBooleanArrayFIFOQueue focused = SynchronizedBooleanArrayFIFOQueue.create();
    
    private RedirectedGLFWWindowFocusCallback(GLFWWindowFocusCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWWindowFocusCallbackRunnable(this);
    }

    public static RedirectedGLFWWindowFocusCallback wrap(GLFWWindowFocusCallbackI callback) {
        return new RedirectedGLFWWindowFocusCallback(callback);
    }

    @Override
    public void invoke(long window, boolean focused) {
        this.window.enqueue(window);
        this.focused.enqueue(focused);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWWindowFocusCallbackRunnable implements Runnable {
        private final RedirectedGLFWWindowFocusCallback callback;

        public RedirectedGLFWWindowFocusCallbackRunnable(RedirectedGLFWWindowFocusCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.focused.dequeue());
        }
    }
}
