package me.decce.ixeris.neoforge.core;

import me.decce.ixeris.core.Constants;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.transform.TransformationHelper;

import java.util.Optional;

public class NeoForgeTransformationHelper extends TransformationHelper {
    public NeoForgeTransformationHelper(ClassLoader modClassLoader) {
        super(modClassLoader);
    }

    @Override
    protected Class<?>[] getTransformers() {
        return Constants.getMixinClasses();
    }


    @Override
    protected Optional<Module> findGlfwModule() {
        try {
            return Optional.ofNullable(Class.forName("org.lwjgl.glfw.GLFWErrorCallbackI").getModule());
        } catch (ClassNotFoundException ignored) {}
        return Optional.empty();
    }

    @Override
    protected Optional<Module> findSdlModule() {
        try {
            return Optional.ofNullable(Class.forName("org.lwjgl.sdl.SDL").getModule());
        } catch (ClassNotFoundException ignored) {}
        return Optional.empty();
    }

    @Override
    protected Optional<Module> findLog4jModule() {
        return Optional.of(LOGGER.getClass().getModule());
    }
}
