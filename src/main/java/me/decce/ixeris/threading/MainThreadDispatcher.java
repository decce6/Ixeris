package me.decce.ixeris.threading;

import com.google.common.collect.Queues;
import me.decce.ixeris.BlockingException;
import me.decce.ixeris.Ixeris;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class MainThreadDispatcher {
    private static final Object queryLock = new Object();
    private static final AtomicBoolean hasQuery = new AtomicBoolean();
    private static final AtomicBoolean queryHasResult = new AtomicBoolean();
    private static volatile Supplier<?> theQuery;
    private static volatile Object queryResult;

    private static final Object runnableLock = new Object();
    private static final AtomicBoolean hasRunnable = new AtomicBoolean();
    private static final AtomicBoolean hasFinishedRunning = new AtomicBoolean();
    private static volatile Runnable theRunnable;

    private static final ConcurrentLinkedQueue<Runnable> mainThreadRecordingQueue = Queues.newConcurrentLinkedQueue();

    public static boolean isOnThread() {
        return Ixeris.isOnMainThread();
    }

    public static <T> T query(Supplier<T> supplier) {
        if (isOnThread()) {
            return supplier.get();
        }
        if (Ixeris.getConfig().shouldLogBlockingCalls()) {
            Ixeris.LOGGER.warn("A call to GLFW has been made that will block the render thread.", new BlockingException());
        }
        synchronized (queryLock) {
            theQuery = supplier;
            hasQuery.set(true);
            Ixeris.wakeUpMainThread();
            while (!queryHasResult.compareAndSet(true, false)) {
                try {
                    queryLock.wait();
                } catch (InterruptedException ignored) {
                }
            }
            return (T) queryResult;
        }
    }

    public static void run(Runnable runnable) {
        if (Ixeris.getConfig().isFullyBlockingMode()) {
            runNow(runnable);
        } else {
            runLater(runnable);
        }
    }

    public static void runLater(Runnable runnable) {
        if (isOnThread()) {
            runnable.run();
            return;
        }
        mainThreadRecordingQueue.add(runnable);
    }

    public static void runNow(Runnable runnable) {
        if (isOnThread()) {
            runnable.run();
            return;
        }
        if (Ixeris.getConfig().shouldLogBlockingCalls()) {
            Ixeris.LOGGER.warn("A call to GLFW has been made that will block the render thread.", new BlockingException());
        }
        synchronized (runnableLock) {
            theRunnable = runnable;
            hasRunnable.set(true);
            Ixeris.wakeUpMainThread();
            while (!hasFinishedRunning.compareAndSet(true, false)) {
                try {
                    runnableLock.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public static void replayQueue() {
        boolean runQuery = hasQuery.compareAndSet(true, false);
        boolean runRunnable = hasRunnable.compareAndSet(true, false);
        while (!mainThreadRecordingQueue.isEmpty()) {
            mainThreadRecordingQueue.poll().run();
        }
        if (runQuery) {
            synchronized (queryLock) {
                if (theQuery != null) {
                    queryResult = theQuery.get();
                    theQuery = null;
                    queryHasResult.set(true);
                    queryLock.notify();
                }
            }
        }
        if (runRunnable) {
            synchronized (runnableLock) {
                if (theRunnable != null) {
                    theRunnable.run();
                    theRunnable = null;
                    hasFinishedRunning.set(true);
                    runnableLock.notify();
                }
            }
        }
    }
}
