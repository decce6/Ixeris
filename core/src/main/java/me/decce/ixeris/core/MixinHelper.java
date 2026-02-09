package me.decce.ixeris.core;

import me.decce.ixeris.core.util.PlatformHelper;
import org.lwjgl.Version;

public class MixinHelper {
    public static boolean shouldApply(String mixinOrTransformerClassName) {
        int lwjglMinorVersion = 0; // Note: cannot use Version.VERSION_MINOR directly! It is a static final constant and gets optimized during compilation.
        try {
            lwjglMinorVersion = Version.class.getField("VERSION_MINOR").getInt(null);
        } catch (Exception e) {
            Ixeris.LOGGER.error("Failed to get LWJGL version!", e);
        }
        if (mixinOrTransformerClassName.contains("glfw_threading_330") && lwjglMinorVersion < 3) {
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
