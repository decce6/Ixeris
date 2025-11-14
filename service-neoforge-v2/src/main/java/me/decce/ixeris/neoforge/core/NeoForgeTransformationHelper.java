package me.decce.ixeris.neoforge.core;

import me.decce.ixeris.core.transform.TransformationHelper;
import org.lwjgl.glfw.GLFWErrorCallback;

public class NeoForgeTransformationHelper extends TransformationHelper {
    public NeoForgeTransformationHelper(ClassLoader modClassLoader) {
        super(modClassLoader);
    }

    @Override
    protected Class<?>[] getTransformers() {
        //noinspection ReferenceToMixin
        return new Class[] {
                me.decce.ixeris.core.mixins.GLFWMixin.class,
                me.decce.ixeris.core.mixins.callback_dispatcher.GLFWMixin.class,
                me.decce.ixeris.core.mixins.glfw_state_caching.GLFWMixin.class,
                me.decce.ixeris.core.mixins.glfw_threading.GLFWMixin.class,
        };
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
