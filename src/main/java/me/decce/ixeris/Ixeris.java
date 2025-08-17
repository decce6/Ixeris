package me.decce.ixeris;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

public final class Ixeris {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "ixeris";
    public static final String MAIN_THREAD_NAME = "Ixeris Event Polling Thread";
    private static IxerisConfig config;
    public static volatile boolean glfwInitialized;
    public static volatile boolean mouseGrabbed;

    public static Thread mainThread;
    public static Thread renderThread;
    public static volatile boolean shouldExit;

    static {
        //TODO: remove
        var cl = Ixeris.class.getClassLoader();
        LOGGER.info("Class {} is loaded on classloader {} of type {}", Ixeris.class.getName(), cl.getName(), cl.getClass().getName());
    }

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
}