package me.decce.ixeris.core.util;

import me.decce.ixeris.core.Ixeris;
import org.lwjgl.Version;

public class LWJGLVersionHelper {
    public static final int lwjglMinorVersion;
    public static final int lwjglRevisionVersion;

    static {
        int minor = 0;
        int revision = 0;
        try {
            minor = Version.class.getField("VERSION_MINOR").getInt(null);
            revision = Version.class.getField("VERSION_REVISION").getInt(null);
        } catch (Throwable e) {
            Ixeris.LOGGER.error("Failed to get LWJGL version!", e);
        }
        lwjglMinorVersion = minor;
        lwjglRevisionVersion = revision;
    }

    public static boolean is330OrGreater() {
        return lwjglMinorVersion >= 3;
    }

    public static boolean is334OrGreater() {
        return lwjglMinorVersion > 3 || (lwjglMinorVersion == 3 && lwjglRevisionVersion >= 4);
    }

    public static boolean is340OrGreater() {
        return lwjglMinorVersion >= 4;
    }
}
