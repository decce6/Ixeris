/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedFloatArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;

public class RedirectedGLFWWindowContentScaleCallback implements GLFWWindowContentScaleCallbackI {
    private final GLFWWindowContentScaleCallbackI callback;
    private final RedirectedGLFWWindowContentScaleCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedFloatArrayFIFOQueue xscale = SynchronizedFloatArrayFIFOQueue.create();
    private final SynchronizedFloatArrayFIFOQueue yscale = SynchronizedFloatArrayFIFOQueue.create();
    
    private RedirectedGLFWWindowContentScaleCallback(GLFWWindowContentScaleCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWWindowContentScaleCallbackRunnable(this);
    }

    public static RedirectedGLFWWindowContentScaleCallback wrap(GLFWWindowContentScaleCallbackI callback) {
        return new RedirectedGLFWWindowContentScaleCallback(callback);
    }

    @Override
    public void invoke(long window, float xscale, float yscale) {
        this.window.enqueue(window);
        this.xscale.enqueue(xscale);
        this.yscale.enqueue(yscale);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWWindowContentScaleCallbackRunnable implements Runnable {
        private final RedirectedGLFWWindowContentScaleCallback callback;

        public RedirectedGLFWWindowContentScaleCallbackRunnable(RedirectedGLFWWindowContentScaleCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.xscale.dequeue(), callback.yscale.dequeue());
        }
    }
}
