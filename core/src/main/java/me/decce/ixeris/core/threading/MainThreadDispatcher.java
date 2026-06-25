package me.decce.ixeris.core.threading;

import me.decce.ixeris.core.BlockingException;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.util.PlatformHelper;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

public class MainThreadDispatcher {
    public static final String BLOCKING_WARN_LOG = "A GLFW call has been made on non-main thread. This might lead to reduced performance.";
    private static final ConcurrentLinkedQueue<Runnable> mainThreadRecordingQueue = new ConcurrentLinkedQueue<>();
    private static final Object mainThreadLock = new Object();
    
    private static boolean pollEvents;

    private static boolean shouldPollEvents() {
        return pollEvents && Ixeris.canPollEvents();
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
        if (Ixeris.accessor.isOnRenderThread()) {
            RenderThreadDispatcher.replayErrorQueue();
        }
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
        runNowImpl(runnable);
        Ixeris.accessor.lockContext();
        if (Ixeris.accessor.isOnRenderThread()) {
            RenderThreadDispatcher.replayErrorQueue();
        }
    }

    private static void runNowImpl(Runnable runnable) {
        ImmediateRunnable runnableWrapper = new ImmediateRunnable(runnable);
        sendToMainThread(runnableWrapper);
        while (!runnableWrapper.hasFinished) {
            Thread.onSpinWait();
        }
    }

    public static void flush() {
        runNow(() -> {});
    }

    public static void replayQueue() {
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
            runnable.run();
        }
    }

    private static Runnable findNextTask() {
        //Prioritize blocking tasks to reduce render thread waiting time
        Runnable nextTask = mainThreadRecordingQueue.poll();
        if (nextTask == null && shouldPollEvents()) {
            nextTask = MainThreadDispatcher::pollEvents;
            pollEvents = false;
        }
        return nextTask;
    }

    private static void pollEvents() {
        // Check here again to avoid a race condition when closing the game
        if (!Ixeris.glfwInitialized) {
            return;
        }

        Ixeris.input().pollEvents();
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
        private volatile boolean hasFinished;

        public Query(Supplier<T> query) {
            this.query = query;
        }

        @Override
        public void run() {
            result = query.get();
            hasFinished = true;
        }
    }

    private static class ImmediateRunnable implements Runnable {
        private final Runnable runnable;
        private volatile boolean hasFinished;

        public ImmediateRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            runnable.run();
            hasFinished = true;
        }
    }
}
