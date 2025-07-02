/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedIntArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public class RedirectedGLFWMouseButtonCallback implements GLFWMouseButtonCallbackI {
    private final GLFWMouseButtonCallbackI callback;
    private final RedirectedGLFWMouseButtonCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue button = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue action = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue mods = SynchronizedIntArrayFIFOQueue.create();
    
    private RedirectedGLFWMouseButtonCallback(GLFWMouseButtonCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWMouseButtonCallbackRunnable(this);
    }

    public static RedirectedGLFWMouseButtonCallback wrap(GLFWMouseButtonCallbackI callback) {
        return new RedirectedGLFWMouseButtonCallback(callback);
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        this.window.enqueue(window);
        this.button.enqueue(button);
        this.action.enqueue(action);
        this.mods.enqueue(mods);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWMouseButtonCallbackRunnable implements Runnable {
        private final RedirectedGLFWMouseButtonCallback callback;

        public RedirectedGLFWMouseButtonCallbackRunnable(RedirectedGLFWMouseButtonCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.button.dequeue(), callback.action.dequeue(), callback.mods.dequeue());
        }
    }
}
