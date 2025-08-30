package me.decce.ixeris.core.util;

import org.lwjgl.system.Platform;

public class PlatformHelper {
    private static final Platform platform = Platform.get();

    public static boolean isLinux() {
        return platform == Platform.LINUX;
    }

    public static boolean isWindows() {
        return platform == Platform.WINDOWS;
    }
}
