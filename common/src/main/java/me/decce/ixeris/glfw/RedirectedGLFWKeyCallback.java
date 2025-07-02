/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedIntArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class RedirectedGLFWKeyCallback implements GLFWKeyCallbackI {
    private final GLFWKeyCallbackI callback;
    private final RedirectedGLFWKeyCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue key = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue scancode = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue action = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue mods = SynchronizedIntArrayFIFOQueue.create();
    
    private RedirectedGLFWKeyCallback(GLFWKeyCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWKeyCallbackRunnable(this);
    }

    public static RedirectedGLFWKeyCallback wrap(GLFWKeyCallbackI callback) {
        return new RedirectedGLFWKeyCallback(callback);
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        this.window.enqueue(window);
        this.key.enqueue(key);
        this.scancode.enqueue(scancode);
        this.action.enqueue(action);
        this.mods.enqueue(mods);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWKeyCallbackRunnable implements Runnable {
        private final RedirectedGLFWKeyCallback callback;

        public RedirectedGLFWKeyCallbackRunnable(RedirectedGLFWKeyCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.key.dequeue(), callback.scancode.dequeue(), callback.action.dequeue(), callback.mods.dequeue());
        }
    }
}
