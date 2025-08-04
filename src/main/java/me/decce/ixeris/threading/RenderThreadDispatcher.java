package me.decce.ixeris.threading;

import com.google.common.collect.Queues;
import me.decce.ixeris.glfw.callback_dispatcher.CursorPosCallbackDispatcher;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderThreadDispatcher {
    private static volatile boolean suppressCursorPosCallbacks;

    private static final ConcurrentLinkedQueue<Runnable> recordingQueue = Queues.newConcurrentLinkedQueue();

    public static void runLater(Runnable runnable) {
        if (suppressCursorPosCallbacks && runnable instanceof CursorPosCallbackDispatcher.DispatchedRunnable) {
            return;
        }
        recordingQueue.add(runnable);
    }

    public static void replayQueue() {
        Runnable nextTask;
        while ((nextTask = recordingQueue.poll()) != null) {
            nextTask.run();
        }
    }

    public static void clearQueuedCursorPosCallbacks() {
        recordingQueue.removeIf(r -> r instanceof CursorPosCallbackDispatcher.DispatchedRunnable);
    }

    public static void suppressCursorPosCallbacks(boolean suppress) {
        suppressCursorPosCallbacks = suppress;
    }
}
