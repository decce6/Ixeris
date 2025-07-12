package me.decce.ixeris.threading;

import com.google.common.collect.Queues;
import me.decce.ixeris.BlockingException;
import me.decce.ixeris.Ixeris;
import me.decce.ixeris.glfw.callbacks_threading.RedirectedGLFWCursorPosCallbackI;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class MainThreadDispatcher {
    private static volatile Supplier<?> theQuery;
    private static volatile Object queryResult;
    private static final AtomicBoolean queryHasResult = new AtomicBoolean();

    private static volatile Runnable theRunnable;
    private static final AtomicBoolean hasFinishedRunning = new AtomicBoolean();

    private static volatile Runnable afterPollingRunnable;

    private static final ConcurrentLinkedQueue<Runnable> mainThreadRecordingQueue = Queues.newConcurrentLinkedQueue();

    public static boolean isOnThread() {
        return Ixeris.isOnMainThread();
    }

    public static <T> T query(Supplier<T> supplier) {
        if (isOnThread()) {
            return supplier.get();
        }
        if (Ixeris.getConfig().shouldLogBlockingCalls()) {
            Ixeris.LOGGER.warn("A GLFW call has been made on non-main thread. This might lead to reduced performance.", new BlockingException());
        }
        theQuery = supplier;
        Ixeris.wakeUpMainThread();
        while (!queryHasResult.compareAndSet(true, false)) {
            Thread.onSpinWait();
        }
        return (T) queryResult;
    }

    public static void run(Runnable runnable) {
        if (Ixeris.getConfig().isFullyBlockingMode()) {
            runNow(runnable);
        } else {
            runLater(runnable);
        }
    }

    public static void runLater(Runnable runnable) {
        mainThreadRecordingQueue.add(runnable);
    }

    public static void runAfterPolling(Runnable runnable) {
        afterPollingRunnable = runnable;
    }

    public static void runNow(Runnable runnable) {
        if (isOnThread()) {
            runnable.run();
            return;
        }
        if (Ixeris.getConfig().shouldLogBlockingCalls()) {
            Ixeris.LOGGER.warn("A GLFW call has been made on non-main thread. This might lead to reduced performance.", new BlockingException());
        }
        theRunnable = runnable;
        Ixeris.wakeUpMainThread();
        while (!hasFinishedRunning.compareAndSet(true, false)) {
            Thread.onSpinWait();
        }
    }

    public static void replayQueue() {
        var query = theQuery;
        var runnable = theRunnable;
        while (!mainThreadRecordingQueue.isEmpty()) {
            mainThreadRecordingQueue.poll().run();
        }
        if (query != null) {
            queryResult = query.get();
            theQuery = null;
            queryHasResult.set(true);
        }
        if (runnable != null) {
            runnable.run();
            theRunnable = null;
            hasFinishedRunning.set(true);
        }
    }

    public static void clearQueuedCursorPosCallbacks() {
        mainThreadRecordingQueue.removeIf(r -> r instanceof RedirectedGLFWCursorPosCallbackI.CursorPosRunnable);
    }

    public static boolean replayAfterPolling() {
        if (afterPollingRunnable != null) {
            afterPollingRunnable.run();
            afterPollingRunnable = null;
            return true;
        }
        return false;
    }
}
