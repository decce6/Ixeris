//? if forge && <=1.16.5 {
/*package me.decce.ixeris.forge;

import me.decce.ixeris.core.transform.ClassLoaderHandler;

public class LegacyForgeClassLoaderHandler extends ClassLoaderHandler {
    public LegacyForgeClassLoaderHandler(ClassLoader bootstrapClassLoader, ClassLoader modClassLoader) {
        super(bootstrapClassLoader, modClassLoader);
    }

    @Override
    public void removeModClassesFromServiceLayer() {

    }
}
*///?}