package me.decce.ixeris.core;

import me.decce.ixeris.core.input.InputManager;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.util.PlatformHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Ixeris {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MAIN_THREAD_NAME = "Ixeris Event Polling Thread";

    public static IxerisMinecraftAccessor accessor = new IxerisNoopAccessor();

    private static final AtomicInteger suppressingEventPolling = new AtomicInteger();
    public static volatile boolean shouldExit;
    public static volatile boolean inEarlyDisplay;
    public static boolean glfwInitialized;
    private static final InputManager inputManager = new InputManager();

    public static volatile Thread mainThread;

    private static IxerisConfig config;

    public static IxerisConfig getConfig() {
        if (config == null) {
            config = IxerisConfig.load();
            config.save();
        }
        return config;
    }

    public static boolean isInitialized() {
        return mainThread != null;
    }

    public static boolean isOnMainThread() {
        return mainThread == null || Thread.currentThread() == mainThread;
    }

    public static void suppressEventPolling() {
        suppressingEventPolling.getAndIncrement();
        MainThreadDispatcher.flush();
    }

    public static void unsuppressEventPolling() {
        suppressingEventPolling.getAndDecrement();
    }

    public static boolean canPollEvents() {
        if (!glfwInitialized) {
            return false;
        }
        if (suppressingEventPolling.get() > 0) {
            return false;
        }

        // Fix: On macOS, do not poll events until window creation, to prevent framebuffer size inconsistencies with
        //  GLFW_COCOA_RETINA_FRAMEBUFFER = GLFW_FALSE.
        // See https://github.com/decce6/Ixeris/issues/40 and https://github.com/glfw/glfw/issues/1968
        return !PlatformHelper.isMacOs() || Ixeris.accessor.isMinecraftWindowCreated();
    }

    public static InputManager input() {
        return inputManager;
    }
}
