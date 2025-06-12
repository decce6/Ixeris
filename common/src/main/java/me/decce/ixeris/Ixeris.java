package me.decce.ixeris;

import com.google.common.collect.Queues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

public final class Ixeris {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "ixeris";
    private static IxerisConfig config;

    private static final ConcurrentLinkedQueue<Runnable> mainThreadRecordingQueue = Queues.newConcurrentLinkedQueue();
    private static final Object mainThreadQueryLock = new Object();
    private static volatile boolean mainThreadHasQuery;
    private static Supplier<?> mainThreadQuery;
    private static Object mainThreadQueryResult;
    private static volatile boolean mainThreadQueryHasResult;

    private static final Object mainThreadRunnableLock = new Object();
    private static volatile boolean mainThreadHasRunnable;
    private static Runnable mainThreadRunnable;
    private static volatile boolean mainThreadHasFinishedRunning;

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
        mainThreadRecordingQueue.add(runnable);
    }

    public static void replayQueue() {
        if (mainThreadHasQuery) {
            synchronized (mainThreadQueryLock) {
                if (mainThreadQuery != null) {
                    mainThreadQueryResult = mainThreadQuery.get();
                    mainThreadQuery = null;
                    mainThreadHasQuery = false;
                    mainThreadQueryHasResult = true;
                    mainThreadQueryLock.notify();
                }
            }
        }
        if (mainThreadHasRunnable) {
            synchronized (mainThreadRunnableLock) {
                if (mainThreadRunnable != null) {
                    mainThreadRunnable.run();
                    mainThreadRunnable = null;
                    mainThreadHasRunnable = false;
                    mainThreadHasFinishedRunning = true;
                    mainThreadRunnableLock.notify();
                }
            }
        }
        while (!mainThreadRecordingQueue.isEmpty()) {
            mainThreadRecordingQueue.poll().run();
        }
    }

    public static <T> T query(Supplier<T> supplier) {
        if (getConfig().shouldLogBlockingCalls()) {
            LOGGER.warn("A call to GLFW has been made that will block the render thread.", new BlockingException());
        }
        synchronized (mainThreadQueryLock) {
            mainThreadQuery = supplier;
            mainThreadHasQuery = true;
            GLFW.glfwPostEmptyEvent();
            while (!mainThreadQueryHasResult) {
                try {
                    mainThreadQueryLock.wait();
                } catch (InterruptedException ignored) {
                }
            }
            mainThreadQueryHasResult = false;
            return (T) mainThreadQueryResult;
        }
    }

    public static void runNowOnMainThread(Runnable runnable) {
        if (getConfig().shouldLogBlockingCalls()) {
            LOGGER.warn("A call to GLFW has been made that blocks the render thread.", new BlockingException());
        }
        synchronized (mainThreadRunnableLock) {
            mainThreadRunnable = runnable;
            mainThreadHasRunnable = true;
            GLFW.glfwPostEmptyEvent();
            while (!mainThreadHasFinishedRunning) {
                try {
                    mainThreadRunnableLock.wait();
                } catch (InterruptedException ignored) {
                }
            }
            mainThreadHasFinishedRunning = false;
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
}