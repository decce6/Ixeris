package me.decce.ixeris.core.threading;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.CursorPosCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.UpcallRunnable;
import me.decce.ixeris.core.util.LWJGLVersionHelper;
import me.decce.ixeris.core.util.UpcallExceptionHelper;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderThreadDispatcher {
    private static final boolean CHECK_EXCEPTIONS_IN_UPCALLS = LWJGLVersionHelper.is340OrGreater() && UpcallExceptionHelper.isSupported();
    public static final ConcurrentLinkedQueue<Runnable> recordingQueue = new ConcurrentLinkedQueue<>();

    public static void runLater(Runnable runnable) {
        innerRunLater(runnable, recordingQueue);
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
            runTask(nextTask);
        }
    }

    public static void runTask(Runnable task) {
        if (CHECK_EXCEPTIONS_IN_UPCALLS && task instanceof UpcallRunnable) {
            runTaskChecked(task);
        }
        else {
            task.run();
        }
    }

    private static void runTaskChecked(Runnable task) {
        try {
            task.run();
        }
        catch (Throwable throwable) {
            UpcallExceptionHelper.handleUpcallException(throwable);
        }
    }
}
