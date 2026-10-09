package me.decce.ixeris.core.threading;

import me.decce.ixeris.core.BlockingException;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.GlfwEventHandler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

public class MainThreadDispatcher {
    public static final String BLOCKING_WARN_LOG = "A GLFW/SDL call has been made on non-main thread. This might lead to reduced performance.";
    private static final ConcurrentLinkedQueue<Runnable> mainThreadRecordingQueue = new ConcurrentLinkedQueue<>();
    private static final Object mainThreadLock = new Object();
    private static final Runnable POLL_EVENTS = MainThreadDispatcher::pollEvents;

    private static boolean pollEvents;

    private static boolean shouldPollEvents() {
        return pollEvents && Ixeris.getEventHandler().canPollEvents();
    }

    public static boolean isOnThread() {
        return Ixeris.isOnMainThread();
    }

    public static <T> T query(Supplier<T> supplier) {
        if (isOnThread()) {
            return supplier.get();
        }
        if (Ixeris.getConfig().shouldLogBlockingCalls()) {
            Ixeris.LOGGER.warn(BLOCKING_WARN_LOG, new BlockingException());
        }
        Ixeris.accessor.unlockContext();
        Query<T> query = new Query<>(supplier);
        sendToMainThread(query);
        while (!query.hasFinished) {
            Thread.onSpinWait();
        }
        Ixeris.accessor.lockContext();
        if (Ixeris.accessor.isOnRenderThread() && Ixeris.getEventHandler() instanceof GlfwEventHandler glfwEventHandler) {
            glfwEventHandler.replayErrorQueue();
        }
        rethrow(query.error);
        return query.result;
    }

    public static void run(Runnable runnable) {
        if (Ixeris.getConfig().isFullyBlockingMode()) {
            runNow(runnable);
        } else {
            runLater(runnable);
        }
    }

    public static void runLater(Runnable runnable) {
        if (!Ixeris.isInitialized()) {
            runnable.run();
            return;
        }
        sendToMainThread(runnable);
    }

    private static void sendToMainThread(Runnable runnable) {
        synchronized (mainThreadLock) {
            mainThreadRecordingQueue.add(runnable);
            mainThreadLock.notify();
        }
    }
    
    public static void requestPollEvents() {
        synchronized (mainThreadLock) {
            pollEvents = true;
            mainThreadLock.notify();
        }
    }

    public static void runNow(Runnable runnable) {
        if (isOnThread()) {
            runnable.run();
            return;
        }
        if (Ixeris.getConfig().shouldLogBlockingCalls()) {
            Ixeris.LOGGER.warn(BLOCKING_WARN_LOG, new BlockingException());
        }
        Ixeris.accessor.unlockContext();
        Throwable error = runNowImpl(runnable);
        Ixeris.accessor.lockContext();
        if (Ixeris.accessor.isOnRenderThread() && Ixeris.getEventHandler() instanceof GlfwEventHandler glfwEventHandler) {
            glfwEventHandler.replayErrorQueue();
        }
        rethrow(error);
    }

    private static Throwable runNowImpl(Runnable runnable) {
        ImmediateRunnable runnableWrapper = new ImmediateRunnable(runnable);
        sendToMainThread(runnableWrapper);
        while (!runnableWrapper.hasFinished) {
            Thread.onSpinWait();
        }
        return runnableWrapper.error;
    }

    private static void rethrow(Throwable error) {
        if (error instanceof RuntimeException e) {
            throw e;
        }
        if (error instanceof Error e) {
            throw e;
        }
        if (error != null) {
            throw new RuntimeException("Main thread task failed", error);
        }
    }

    public static void replayQueue() {
        afterTask();
        while (true) {
            Runnable runnable;
            synchronized (mainThreadLock) {
                runnable = findNextTask();
                if (runnable == null) {
                    await(Ixeris.getConfig().getMainThreadSleepTime());
                    pollEvents = true;
                    break;
                }
            }
            try {
                runnable.run();
            } catch (Exception t) {
                if (runnable == POLL_EVENTS) {
                    throw t;
                }
                Ixeris.LOGGER.error("A task failed on the main thread", t);
            } finally {
                if (runnable != POLL_EVENTS && !(runnable instanceof Query<?> || runnable instanceof ImmediateRunnable)) {
                    afterTask();
                }
            }
        }
    }

    private static void afterTask() {
        try {
            Ixeris.getEventHandler().afterMainThreadTask();
        } catch (Throwable t) {
            Ixeris.LOGGER.error("Failed to run post-task hook on the main thread", t);
        }
    }

    private static Runnable findNextTask() {
        //Prioritize blocking tasks to reduce render thread waiting time
        Runnable nextTask = mainThreadRecordingQueue.poll();
        if (nextTask == null && shouldPollEvents()) {
            nextTask = POLL_EVENTS;
            pollEvents = false;
        }
        return nextTask;
    }

    private static void pollEvents() {
        Ixeris.getEventHandler().pollEvents();
    }

    public static void await(long timeout) {
        try {
            mainThreadLock.wait(timeout);
        } catch (InterruptedException ignored) {
        }
    }

    private static class Query<T> implements Runnable {
        private final Supplier<T> query;
        private volatile T result;
        private volatile Throwable error;
        private volatile boolean hasFinished;

        public Query(Supplier<T> query) {
            this.query = query;
        }

        @Override
        public void run() {
            try {
                result = query.get();
            } catch (Throwable t) {
                error = t; // never leave the caller spinning
            }
            afterTask(); // before publishing the result, so caches already reflect this task
            hasFinished = true;
        }
    }

    private static class ImmediateRunnable implements Runnable {
        private final Runnable runnable;
        private volatile Throwable error;
        private volatile boolean hasFinished;

        public ImmediateRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                runnable.run();
            } catch (Throwable t) {
                error = t;
            }
            afterTask();
            hasFinished = true;
        }
    }
}
