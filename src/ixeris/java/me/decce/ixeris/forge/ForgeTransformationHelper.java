//? if forge {
/*package me.decce.ixeris.forge;

import me.decce.ixeris.core.transform.TransformationHelper;

public class ForgeTransformationHelper extends TransformationHelper {
    public ForgeTransformationHelper(ClassLoader modClassLoader) {
        super(modClassLoader);
    }

    @Override
    protected Class<?>[] getTransformers() {
        return new Class[] {
                me.decce.ixeris.forge.transformers.GLFWTransformer.class,
                me.decce.ixeris.forge.transformers.callback_dispatcher.GLFWTransformer.class,
                me.decce.ixeris.forge.transformers.glfw_state_caching.GLFWTransformer.class,
                me.decce.ixeris.forge.transformers.glfw_threading.GLFWTransformer.class
                //? if >=1.19 {
                ,me.decce.ixeris.forge.transformers.glfw_threading_330.GLFWTransformer.class
                //?}
        };
    }
}
*///?}
