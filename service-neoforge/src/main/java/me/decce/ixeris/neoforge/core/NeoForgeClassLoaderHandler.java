package me.decce.ixeris.neoforge.core;

import cpw.mods.cl.ModuleClassLoader;
import me.decce.ixeris.core.transform.ClassLoaderHandler;

import java.lang.module.ResolvedModule;
import java.util.Map;

import static me.decce.ixeris.core.util.ReflectionHelper.unreflectGetter;

public class NeoForgeClassLoaderHandler extends ClassLoaderHandler {
    public NeoForgeClassLoaderHandler(ClassLoader bootstrapClassLoader, ClassLoader modClassLoader) {
        super(bootstrapClassLoader, modClassLoader);
    }

    @Override
    public void removeModClassesFromServiceLayer() {
        try {
            // At this point our classes are already loaded on the MC-BOOTSTRAP classloader, but we need to do this here
            // to prevent the LAYER SERVICE classloader from loading them again (out Mixin plugin needs to use them to
            // decide whether to apply mixins)
            var packageLookupGetter = unreflectGetter(() -> ModuleClassLoader.class.getDeclaredField("packageLookup"));
            var packageLookup = (Map<String, ResolvedModule>) packageLookupGetter.invoke(this.modClassLoader);
            packageLookup.entrySet().removeIf(e -> e.getKey().startsWith("me.decce.ixeris.core"));

            // If we don't do this the LAYER SERVICE classloader will keep asking itself to load our class, eventually
            // causing a StackOverflowException
            var parentLoadersGetter = unreflectGetter(() -> ModuleClassLoader.class.getDeclaredField("parentLoaders"));
            var parentLoaders = (Map<String, ClassLoader>) parentLoadersGetter.invoke(this.modClassLoader);
            parentLoaders.entrySet().removeIf(e -> e.getKey().startsWith("me.decce.ixeris.core"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
