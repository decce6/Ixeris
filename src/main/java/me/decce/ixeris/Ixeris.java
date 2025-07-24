package me.decce.ixeris;

import com.mojang.logging.LogUtils;

import me.decce.ixeris.threading.MainThreadDispatcher;

import org.slf4j.Logger;

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

    public static void wakeUpMainThread() {
        MainThreadDispatcher.awake();
    }
}