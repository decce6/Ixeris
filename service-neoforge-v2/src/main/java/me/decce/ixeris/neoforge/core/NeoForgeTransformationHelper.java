package me.decce.ixeris.neoforge.core;

import me.decce.ixeris.core.Constants;
import me.decce.ixeris.core.transform.TransformationHelper;
import org.lwjgl.glfw.GLFWErrorCallback;

public class NeoForgeTransformationHelper extends TransformationHelper {
    public NeoForgeTransformationHelper(ClassLoader modClassLoader) {
        super(modClassLoader);
    }

    @Override
    protected Class<?>[] getTransformers() {
        return Constants.getMixinClasses();
    }


    @Override
    protected Module findGlfwModule() {
        return GLFWErrorCallback.class.getModule();
    }

    @Override
    protected Module findLog4jModule() {
        return LOGGER.getClass().getModule();
    }
}
