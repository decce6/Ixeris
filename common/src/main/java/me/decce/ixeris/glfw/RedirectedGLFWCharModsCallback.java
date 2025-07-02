/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedIntArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWCharModsCallbackI;

public class RedirectedGLFWCharModsCallback implements GLFWCharModsCallbackI {
    private final GLFWCharModsCallbackI callback;
    private final RedirectedGLFWCharModsCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue codepoint = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue mods = SynchronizedIntArrayFIFOQueue.create();
    
    private RedirectedGLFWCharModsCallback(GLFWCharModsCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWCharModsCallbackRunnable(this);
    }

    public static RedirectedGLFWCharModsCallback wrap(GLFWCharModsCallbackI callback) {
        return new RedirectedGLFWCharModsCallback(callback);
    }

    @Override
    public void invoke(long window, int codepoint, int mods) {
        this.window.enqueue(window);
        this.codepoint.enqueue(codepoint);
        this.mods.enqueue(mods);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWCharModsCallbackRunnable implements Runnable {
        private final RedirectedGLFWCharModsCallback callback;

        public RedirectedGLFWCharModsCallbackRunnable(RedirectedGLFWCharModsCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.codepoint.dequeue(), callback.mods.dequeue());
        }
    }
}
