package me.decce.ixeris.neoforge.core;

import me.decce.ixeris.core.Constants;
import me.decce.ixeris.core.transform.TransformationHelper;

public class NeoForgeTransformationHelper extends TransformationHelper {
    public NeoForgeTransformationHelper(ClassLoader modClassLoader) {
        super(modClassLoader);
    }

    @Override
    protected Class<?>[] getTransformers() {
        return Constants.getMixinClasses();
    }

}
