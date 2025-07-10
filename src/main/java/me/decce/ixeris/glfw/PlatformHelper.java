package me.decce.ixeris.glfw;

import org.lwjgl.system.Platform;

public class PlatformHelper {
    private static Boolean linux;

    public static boolean isLinux() {
        return linux == null ? linux = (Platform.get() == Platform.LINUX) : linux;
    }
}
