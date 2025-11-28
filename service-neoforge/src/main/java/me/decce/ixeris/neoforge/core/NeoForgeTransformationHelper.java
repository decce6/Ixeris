package me.decce.ixeris.neoforge.core;

import me.decce.ixeris.core.transform.TransformationHelper;

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
                me.decce.ixeris.core.mixins.glfw_threading_330.GLFWMixin.class,
        };
    }

}
