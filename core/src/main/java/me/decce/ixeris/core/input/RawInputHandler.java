package me.decce.ixeris.core.input;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.GlfwEventHandler;
import me.decce.ixeris.core.input.win32.RawInputHandlerGlfwWin32;
import me.decce.ixeris.core.util.PlatformHelper;

public interface RawInputHandler {
    void grab();
    void release();
    void pollEvents();
    void setCursorPos(double x, double y);
    boolean supported();

    static RawInputHandler create(long window) {
        if (PlatformHelper.isWindows() && Ixeris.getEventHandler() instanceof GlfwEventHandler) {
            return new RawInputHandlerGlfwWin32(window);
        }
        throw new IllegalStateException("Buffered raw input is not supported on current platform");
    }
}
