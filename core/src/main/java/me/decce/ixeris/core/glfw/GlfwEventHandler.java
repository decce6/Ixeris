package me.decce.ixeris.core.glfw;

import me.decce.ixeris.core.EventHandler;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.CursorPosCallbackDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import me.decce.ixeris.core.util.PlatformHelper;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GlfwEventHandler implements EventHandler {
    private static final boolean IS_MACOS = PlatformHelper.isMacOs();
    private static final ConcurrentLinkedQueue<Runnable> errorRecordingQueue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean canPollEvents() {
        // Fix: On macOS, do not poll events until window creation, to prevent framebuffer size inconsistencies with
        //  GLFW_COCOA_RETINA_FRAMEBUFFER = GLFW_FALSE.
        // See https://github.com/decce6/Ixeris/issues/40 and https://github.com/glfw/glfw/issues/1968
        return Ixeris.glfwInitialized && (!IS_MACOS || Ixeris.accessor.isMinecraftWindowCreated());
    }

    @Override
    public void pollEvents() {
        if (Ixeris.glfwInitialized) {
            Ixeris.input().pollEvents();
        }
    }

    public void recordError(Runnable runnable) {
        errorRecordingQueue.add(runnable);
    }

    public void replayErrorQueue() {
        Runnable nextTask;
        while ((nextTask = errorRecordingQueue.poll()) != null) {
            RenderThreadDispatcher.runTask(nextTask);
        }
    }

    public void clearQueuedCursorPosCallbacks() {
        RenderThreadDispatcher.recordingQueue.removeIf(r -> r instanceof CursorPosCallbackDispatcher.DispatchedRunnable);
    }
}
