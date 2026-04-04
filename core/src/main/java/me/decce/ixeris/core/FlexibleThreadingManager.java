package me.decce.ixeris.core;

import me.decce.ixeris.core.util.PlatformHelper;

public class FlexibleThreadingManager {
    public static final Object CLIPBOARD_LOCK = new Object();
    public static final Object CURSOR_LOCK = new Object();
    public static final Object MONITOR_LOCK = new Object();
    public static final Object WINDOW_LOCK = new Object();

    public static boolean canUseFlexibleClipboard() {
        return PlatformHelper.isWindows();
    }
}
