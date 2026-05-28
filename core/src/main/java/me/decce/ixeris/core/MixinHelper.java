package me.decce.ixeris.core;

import me.decce.ixeris.core.util.LWJGLVersionHelper;
import me.decce.ixeris.core.util.PlatformHelper;

public class MixinHelper {
    public static boolean shouldApply(String mixinOrTransformerClassName) {
        if (mixinOrTransformerClassName.contains("_330") && !LWJGLVersionHelper.is330OrGreater()) {
            return false;
        }
        if (mixinOrTransformerClassName.contains("_334") && !LWJGLVersionHelper.is334OrGreater()) {
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
