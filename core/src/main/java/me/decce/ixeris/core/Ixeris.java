package me.decce.ixeris.core;

import me.decce.ixeris.core.glfw.GlfwEventHandler;
import me.decce.ixeris.core.input.InputManager;
import me.decce.ixeris.core.sdl.SdlEventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Ixeris {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MAIN_THREAD_NAME = "Ixeris Event Polling Thread";

    public static IxerisMinecraftAccessor accessor = new IxerisNoopAccessor();

    public static volatile boolean shouldExit;
    public static volatile boolean inEarlyDisplay;
    public static volatile boolean forceReconfigureSwapchain;
    private static EventHandler eventHandler;
    public static final AtomicInteger suppressPollingWarning = new AtomicInteger();
    public static boolean glfwInitialized;
    public static boolean sdlInitialized;
    private static final InputManager inputManager = new InputManager();

    public static volatile Thread mainThread;

    private static IxerisConfig config;

    public static EventHandler getEventHandler() {
        if (eventHandler == null) {
            return createEventHandler();
        }
        return eventHandler;
    }

    private static EventHandler createEventHandler() {
        if (eventHandler == null) {
            if (sdlInitialized) {
                Ixeris.LOGGER.info("Using SdlEventHandler");
                return (eventHandler = new SdlEventHandler());
            }
            else if (glfwInitialized) {
                Ixeris.LOGGER.info("Using GlfwEventHandler");
                return (eventHandler = new GlfwEventHandler());
            }
        }
        return DummyEventHandler.INSTANCE;
    }

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
