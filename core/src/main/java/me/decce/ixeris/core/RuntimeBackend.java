package me.decce.ixeris.core;

import org.spongepowered.asm.mixin.MixinEnvironment;

public enum RuntimeBackend {
    GLFW,
    SDL3;

    public static final boolean HAS_GLFW = classResourceExists("org.lwjgl.glfw.GLFW");
    public static final boolean HAS_SDL = classResourceExists("org.lwjgl.sdl.SDL");

    public static RuntimeBackend get() {
        if (HAS_SDL) {
            return RuntimeBackend.SDL3;
        }
        else if (HAS_GLFW) {
            return RuntimeBackend.GLFW;
        }
        else {
            throw new RuntimeException("Could not determine runtime backend");
        }
    }

    private static boolean classResourceExists(String className) {
        ClassLoader classLoader = MixinEnvironment.class.getClassLoader();
        return classLoader.getResource(className.replace('.', '/') + ".class") != null;
    }
}
