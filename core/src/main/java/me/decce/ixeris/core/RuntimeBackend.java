package me.decce.ixeris.core;

import org.spongepowered.asm.mixin.MixinEnvironment;

public class RuntimeBackend {
    public static final boolean HAS_GLFW = classResourceExists("org.lwjgl.glfw.GLFW");
    public static final boolean HAS_SDL = classResourceExists("org.lwjgl.sdl.SDL");

    private static boolean classResourceExists(String className) {
        ClassLoader classLoader = MixinEnvironment.class.getClassLoader();
        return classLoader.getResource(className.replace('.', '/') + ".class") != null;
    }
}
