package me.decce.ixeris.core.threading;

import com.google.common.collect.Queues;
import me.decce.ixeris.core.glfw.callback_dispatcher.CursorPosCallbackDispatcher;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RenderThreadDispatcher {
    private static final AtomicInteger suppressCursorPosCallbacks = new AtomicInteger();

    private static final ConcurrentLinkedQueue<Runnable> recordingQueue = Queues.newConcurrentLinkedQueue();

    public static void runLater(Runnable runnable) {
        if (suppressCursorPosCallbacks.get() > 0 && runnable instanceof CursorPosCallbackDispatcher.DispatchedRunnable) {
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

    public static void suppressCursorPosCallbacks() {
        suppressCursorPosCallbacks.getAndIncrement();
    }

    public static void unsuppressCursorPosCallbacks() {
        suppressCursorPosCallbacks.getAndDecrement();
    }
}
