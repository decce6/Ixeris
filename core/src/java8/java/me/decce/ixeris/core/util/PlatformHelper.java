package me.decce.ixeris.core.util;

import org.lwjgl.system.Platform;

import java.util.stream.Stream;

public class PlatformHelper {
    private static final Platform platform = Platform.get();
    private static final Platform.Architecture architecture = Platform.getArchitecture();
    private static final boolean android = Stream.of("POJAV_RENDERER", "POJAV_ENVIRON", "POJAV_NATIVEDIR", "MOJO_ENVIRON")
            .anyMatch(s -> System.getenv(s) != null);

    public static boolean isLinux() {
        return platform == Platform.LINUX;
    }

    public static boolean isWindows() {
        return platform == Platform.WINDOWS;
    }

    public static boolean isMacOs() {
        return platform == Platform.MACOSX;
    }

    public static boolean isAndroid() {
        return android;
    }

    public static boolean isX64() {
        return architecture == Platform.Architecture.X64;
    }
}
