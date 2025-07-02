package me.decce.ixeris;

import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.PriorityQueues;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import me.decce.ixeris.glfw.RedirectedGLFWCursorPosCallback;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public final class Ixeris {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "ixeris";
    private static IxerisConfig config;

    private static final Object mainThreadLock = new Object();

    private static final PriorityQueue<Runnable> renderThreadRecordingQueue = PriorityQueues.synchronize(new ObjectArrayFIFOQueue<>());

    private static final PriorityQueue<Runnable> mainThreadRecordingQueue = PriorityQueues.synchronize(new ObjectArrayFIFOQueue<>());
    private static final Object mainThreadQueryLock = new Object();
    private static final AtomicBoolean mainThreadHasQuery = new AtomicBoolean();
    private static volatile Supplier<?> mainThreadQuery;
    private static volatile Object mainThreadQueryResult;
    private static final AtomicBoolean mainThreadQueryHasResult = new AtomicBoolean();

    private static final Object mainThreadRunnableLock = new Object();
    private static final AtomicBoolean mainThreadHasRunnable = new AtomicBoolean();
    private static volatile Runnable mainThreadRunnable;
    private static final AtomicBoolean mainThreadHasFinishedRunning = new AtomicBoolean();

    public static Thread mainThread;
    public static volatile boolean shouldExit;

    public static void init() {
    }

    public static IxerisConfig getConfig() {
        if (config == null) {
            config = IxerisConfig.load();
            config.save();
        }
        return config;
    }

    public static boolean isOnMainThread() {
        return mainThread == null || Thread.currentThread() == mainThread;
    }

    public static void runLaterOnMainThread(Runnable runnable) {
        mainThreadRecordingQueue.enqueue(runnable);
    }

    public static void replayMainThreadQueue() {
        while (!mainThreadRecordingQueue.isEmpty()) {
            mainThreadRecordingQueue.dequeue().run();
        }
        if (mainThreadHasQuery.compareAndSet(true, false)) {
            synchronized (mainThreadQueryLock) {
                if (mainThreadQuery != null) {
                    mainThreadQueryResult = mainThreadQuery.get();
                    mainThreadQuery = null;
                    mainThreadQueryHasResult.set(true);
                    mainThreadQueryLock.notify();
                }
            }
        }
        if (mainThreadHasRunnable.compareAndSet(true, false)) {
            synchronized (mainThreadRunnableLock) {
                if (mainThreadRunnable != null) {
                    mainThreadRunnable.run();
                    mainThreadRunnable = null;
                    mainThreadHasFinishedRunning.set(true);
                    mainThreadRunnableLock.notify();
                }
            }
        }
    }

    public static <T> T query(Supplier<T> supplier) {
        if (getConfig().shouldLogBlockingCalls()) {
            LOGGER.warn("A call to GLFW has been made that will block the render thread.", new BlockingException());
        }
        synchronized (mainThreadQueryLock) {
            mainThreadQuery = supplier;
            mainThreadHasQuery.set(true);
            wakeUpMainThread();
            while (!mainThreadQueryHasResult.compareAndSet(true, false)) {
                try {
                    mainThreadQueryLock.wait();
                } catch (InterruptedException ignored) {
                }
            }
            return (T) mainThreadQueryResult;
        }
    }

    public static void runNowOnMainThread(Runnable runnable) {
        if (getConfig().shouldLogBlockingCalls()) {
            LOGGER.warn("A call to GLFW has been made that blocks the render thread.", new BlockingException());
        }
        synchronized (mainThreadRunnableLock) {
            mainThreadRunnable = runnable;
            mainThreadHasRunnable.set(true);
            wakeUpMainThread();
            while (!mainThreadHasFinishedRunning.compareAndSet(true, false)) {
                try {
                    mainThreadRunnableLock.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public static void runOnMainThread(Runnable runnable) {
        if (Ixeris.getConfig().isFullyBlockingMode()) {
            Ixeris.runNowOnMainThread(runnable);
        }
        else {
            Ixeris.runLaterOnMainThread(runnable);
        }
    }

    public static void runLaterOnRenderThread(Runnable runnable) {
        renderThreadRecordingQueue.enqueue(runnable);
    }

    public static void replayRenderThreadQueue() {
        while (!renderThreadRecordingQueue.isEmpty()) {
            renderThreadRecordingQueue.dequeue().run();
        }
    }

    public static void clearQueuedCursorPosCallbacks() {
        // TODO
        // renderThreadRecordingQueue(r -> r instanceof RedirectedGLFWCursorPosCallback.RedirectedGLFWCursorPosCallbackRunnable);
    }

    public static void putAsleepMainThread() {
        synchronized (mainThreadLock) {
            try {
                mainThreadLock.wait(200L);
            } catch (InterruptedException ignored) {}
        }
    }

    public static void wakeUpMainThread() {
        synchronized (mainThreadLock) {
            mainThreadLock.notify();
        }
    }
}