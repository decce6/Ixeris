/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.util.SynchronizedIntArrayFIFOQueue;
import me.decce.ixeris.util.SynchronizedLongArrayFIFOQueue;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;

public class RedirectedGLFWWindowPosCallback implements GLFWWindowPosCallbackI {
    private final GLFWWindowPosCallbackI callback;
    private final RedirectedGLFWWindowPosCallbackRunnable runnable;

    // Store method parameters in primitive queues, to avoid memory allocation, boxing and unboxing
    private final SynchronizedLongArrayFIFOQueue window = SynchronizedLongArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue xpos = SynchronizedIntArrayFIFOQueue.create();
    private final SynchronizedIntArrayFIFOQueue ypos = SynchronizedIntArrayFIFOQueue.create();
    
    private RedirectedGLFWWindowPosCallback(GLFWWindowPosCallbackI callback) {
        this.callback = callback;
        this.runnable = new RedirectedGLFWWindowPosCallbackRunnable(this);
    }

    public static RedirectedGLFWWindowPosCallback wrap(GLFWWindowPosCallbackI callback) {
        return new RedirectedGLFWWindowPosCallback(callback);
    }

    @Override
    public void invoke(long window, int xpos, int ypos) {
        this.window.enqueue(window);
        this.xpos.enqueue(xpos);
        this.ypos.enqueue(ypos);
        Ixeris.runLaterOnRenderThread(runnable);
    }

    public static class RedirectedGLFWWindowPosCallbackRunnable implements Runnable {
        private final RedirectedGLFWWindowPosCallback callback;

        public RedirectedGLFWWindowPosCallbackRunnable(RedirectedGLFWWindowPosCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.callback.invoke(callback.window.dequeue(), callback.xpos.dequeue(), callback.ypos.dequeue());
        }
    }
}
