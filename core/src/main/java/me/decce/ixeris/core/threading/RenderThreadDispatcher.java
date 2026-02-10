package me.decce.ixeris.core.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.CursorPosCallbackDispatcher;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RenderThreadDispatcher {
    private static final AtomicInteger suppressCursorPosCallbacks = new AtomicInteger();
    private static final AtomicInteger frames = new AtomicInteger();
    private static final AtomicBoolean waiting = new AtomicBoolean();

    private static final ConcurrentLinkedQueue<Runnable> recordingQueue = new ConcurrentLinkedQueue<>();

    public static void runLater(Runnable runnable) {
        // It's possible for some callbacks to happen on the main thread; however, we still queue them to ensure the
        // sequential execution of callbacks
        if (!Ixeris.isInitialized()) {
            runnable.run();
            return;
        }
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

    public static void waitForBufferSwapping() {
        int current = frames.get();
        waiting.set(true);
        while (frames.get() <= current) {
            Thread.onSpinWait();
        }
        waiting.set(false);
    }

    public static void notifyBufferSwapped() {
        frames.getAndIncrement();
    }

    public static boolean shouldSkipFramerateLimit() {
        return waiting.get();
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
