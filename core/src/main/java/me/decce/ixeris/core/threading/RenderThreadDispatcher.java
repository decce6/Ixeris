package me.decce.ixeris.core.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.CursorPosCallbackDispatcher;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderThreadDispatcher {
    private static final ConcurrentLinkedQueue<Runnable> recordingQueue = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<Runnable> errorRecordingQueue = new ConcurrentLinkedQueue<>();

    public static void runLater(Runnable runnable) {
        innerRunLater(runnable, recordingQueue);
    }

    public static void recordError(Runnable runnable) {
        innerRunLater(runnable, errorRecordingQueue);
    }

    private static void innerRunLater(Runnable runnable, ConcurrentLinkedQueue<Runnable> queue) {
        if (!Ixeris.isInitialized()) {
            runnable.run();
            return;
        }
        // It's possible for some callbacks to happen on the render thread; however, we still queue them to ensure the
        // sequential execution of callbacks
        queue.add(runnable);
    }

    public static void replayQueue() {
        Runnable nextTask;
        while ((nextTask = recordingQueue.poll()) != null) {
            nextTask.run();
        }
    }

    public static void replayErrorQueue() {
        Runnable nextTask;
        while ((nextTask = errorRecordingQueue.poll()) != null) {
            nextTask.run();
        }
    }

    public static void clearQueuedCursorPosCallbacks() {
        recordingQueue.removeIf(r -> r instanceof CursorPosCallbackDispatcher.DispatchedRunnable);
    }
}
