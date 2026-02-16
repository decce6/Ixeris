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
        return pollEvents && canPollEvents();
    }

    private static boolean canPollEvents() {
        if (!Ixeris.glfwInitialized) {
            return false;
        }

        // Fix: On macOS, do not poll events until window creation, to prevent framebuffer size inconsistencies with
        //  GLFW_COCOA_RETINA_FRAMEBUFFER = GLFW_FALSE.
        // See https://github.com/decce6/Ixeris/issues/40 and https://github.com/glfw/glfw/issues/1968
        return !PlatformHelper.isMacOs() || Ixeris.accessor.isMinecraftWindowCreated();
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
    }

    public static void runNowImpl(Runnable runnable) {
        ImmediateRunnable runnableWrapper = new ImmediateRunnable(runnable);
        sendToMainThread(runnableWrapper);
        while (!runnableWrapper.hasFinished) {
            Thread.onSpinWait();
        }
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
