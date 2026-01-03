package me.decce.ixeris.api;

import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class IxerisApi {
    private static final IxerisApi instance = new IxerisApi();

    public static IxerisApi getInstance() {
        return instance;
    }

    /**
     * Retrieves the name of the main thread.
     */
    public String getMainThreadName() {
        return Ixeris.MAIN_THREAD_NAME;
    }

    /**
     * Checks if Ixeris is enabled in the config.
     */
    public boolean isEnabled() {
        return Ixeris.getConfig().isEnabled();
    }

    /**
     * Checks if the main thread has been initialized. You typically do not need to call this method, as the main thread
     * is initialized very early. This method constantly returns false is Ixeris is disabled in the config.
     */
    public boolean isInitialized() {
        if (!isEnabled()) {
            return false;
        }
        return Ixeris.mainThread != null;
    }

    /**
     * Checks if the current thread is the main thread, i.e. the thread that created the game window and polls events,
     * or the main thread has not been initialized yet.
     * @return True if the code is being executed on the main thread or the main thread has not been initialized yet, false otherwise.
     */
    public boolean isOnMainThreadOrInit() {
        if (!isEnabled()) {
            return RenderSystem.isOnRenderThread();
        }
        return Ixeris.isOnMainThread();
    }

    /**
     * Checks if the current thread is the main thread, i.e. the thread that created the game window and polls events.
     * @return True if the code is being executed on the main thread, false otherwise.
     */
    public boolean isOnMainThread() {
        if (!isEnabled()) {
            return RenderSystem.isOnRenderThread();
        }
        return isOnMainThreadOrInit() && isInitialized();
    }

    /**
     * <p>
     * Execute the provided {@link Runnable} object on the main thread.
     * </p>
     * <p>
     * When fullyBlockingMode is enabled in Ixeris config, this is equivalent to calling
     * {@link IxerisApi#runNowOnMainThread(Runnable)}. Otherwise, this is equivalent to calling
     * {@link IxerisApi#runLaterOnMainThread(Runnable)}.
     * </p>
     */
    public void runOnMainThread(Runnable runnable) {
        if (isEnabled()) {
            MainThreadDispatcher.run(runnable);
        }
        else {
            runnable.run();
        }
    }

    /**
     * Execute the provided {@link Runnable} object instantly on the main thread. If the current thread is not the main thread, it may
     * be blocked until the main thread finishes execution.
     */
    public void runNowOnMainThread(Runnable runnable) {
        if (isEnabled()) {
            MainThreadDispatcher.runNow(runnable);
        }
        else {
            runnable.run();
        }
    }

    /**
     * Execute the provided {@link Runnable} object later on the main thread.
     */
    public void runLaterOnMainThread(Runnable runnable) {
        if (isEnabled()) {
            MainThreadDispatcher.runLater(runnable);
        }
        else {
            runnable.run();
        }
    }

    /**
     * Retrieve value from the provided {@link Supplier} object instantly on the main thread. If the current thread is
     * not the main thread, it may be blocked until the value is retrieved from the main thread.
     */
    public <T> T query(Supplier<T> supplier) {
        if (isEnabled()) {
            return MainThreadDispatcher.query(supplier);
        }
        else {
            return supplier.get();
        }
    }

    /**
     * Execute the provided {@link Runnable} object later on the render thread. It is executed after the current frame
     * has finished.
     */
    public void runLaterOnRenderThread(Runnable runnable) {
        if (isEnabled()) {
            RenderThreadDispatcher.runLater(runnable);
        }
        else {
            runnable.run();
        }
    }
}
