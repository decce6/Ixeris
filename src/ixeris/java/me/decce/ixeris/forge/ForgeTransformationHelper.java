//? if forge {
/*package me.decce.ixeris.forge;

import me.decce.ixeris.core.Constants;
import me.decce.ixeris.core.transform.TransformationHelper;

public class ForgeTransformationHelper extends TransformationHelper {
    public ForgeTransformationHelper(ClassLoader modClassLoader) {
        super(modClassLoader);
    }

    @Override
    protected Class<?>[] getTransformers() {
        return Constants.getForgeTransformerClasses(modClassLoader);
    }
}
*///?}
