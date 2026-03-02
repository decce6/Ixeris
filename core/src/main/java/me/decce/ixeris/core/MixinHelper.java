package me.decce.ixeris.core;

import me.decce.ixeris.core.util.PlatformHelper;
import org.lwjgl.Version;

public class MixinHelper {
    public static boolean shouldApply(String mixinOrTransformerClassName) {
        // Note: cannot use Version.VERSION_MINOR directly! It is a static final constant and gets inlined during compilation.
        int lwjglMinorVersion = 0;
        int lwjglRevisionVersion = 0;
        try {
            lwjglMinorVersion = Version.class.getField("VERSION_MINOR").getInt(null);
            lwjglRevisionVersion = Version.class.getField("VERSION_REVISION").getInt(null);
        } catch (Exception e) {
            Ixeris.LOGGER.error("Failed to get LWJGL version!", e);
        }
        if (mixinOrTransformerClassName.contains("_330") && lwjglMinorVersion < 3) {
            return false;
        }
        if (mixinOrTransformerClassName.contains("_334") && lwjglMinorVersion <= 3 && lwjglRevisionVersion < 4) {
            return false;
        }
        if (mixinOrTransformerClassName.contains("flexible_threading") && !Ixeris.getConfig().useFlexibleThreading()) {
            return false;
        }
        if (mixinOrTransformerClassName.contains("macos") && !PlatformHelper.isMacOs()) {
            return false;
        }
        return true;
    }
}
