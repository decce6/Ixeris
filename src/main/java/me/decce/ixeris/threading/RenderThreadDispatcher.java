package me.decce.ixeris.threading;

import com.google.common.collect.Queues;
import me.decce.ixeris.glfw.callbacks_threading.RedirectedGLFWCursorPosCallbackI;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderThreadDispatcher {
    private static final ConcurrentLinkedQueue<Runnable> recordingQueue = Queues.newConcurrentLinkedQueue();

    public static void runLater(Runnable runnable) {
        recordingQueue.add(runnable);
    }

    public static void replayQueue() {
        while (!recordingQueue.isEmpty()) {
            recordingQueue.poll().run();
        }
    }

    public static void clearQueuedCursorPosCallbacks() {
        recordingQueue.removeIf(r -> r instanceof RedirectedGLFWCursorPosCallbackI.CursorPosRunnable);
    }
}
