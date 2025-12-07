package me.decce.ixeris;

import me.decce.ixeris.core.Ixeris;

public class RenderThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        var defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler != null) {
            defaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
        }
        Ixeris.shouldExit = true;
    }
}
