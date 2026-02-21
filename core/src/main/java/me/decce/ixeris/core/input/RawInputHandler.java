package me.decce.ixeris.core.input;

import me.decce.ixeris.core.util.PlatformHelper;

public interface RawInputHandler {
    void enable();
    void disable();
    void pollEvents();

    static RawInputHandler create(long window) {
        if (PlatformHelper.isWindows()) {
            return new RawInputHandlerWin32(window);
        }
        return new DefaultRawInputHandler();
    }
}
