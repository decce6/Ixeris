package me.decce.ixeris.core;

import me.decce.ixeris.core.input.InputManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ixeris {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MAIN_THREAD_NAME = "Ixeris Event Polling Thread";

    public static IxerisMinecraftAccessor accessor = new IxerisNoopAccessor();

    public static volatile boolean shouldExit;
    public static volatile boolean inEarlyDisplay;
    public static boolean suppressEventPollingWarning;
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

    public static InputManager input() {
        return inputManager;
    }
}
