//? if forge && >=1.18.2 {
/*package me.decce.ixeris.forge;

import me.decce.ixeris.core.transform.ClassLoaderHandler;

import java.lang.module.ResolvedModule;
import java.util.Map;

import static me.decce.ixeris.core.util.ReflectionHelper.unreflectGetter;

public class ForgeClassLoaderHandler extends ClassLoaderHandler {
    public ForgeClassLoaderHandler(ClassLoader bootstrapClassLoader, ClassLoader modClassLoader) {
        super(bootstrapClassLoader, modClassLoader);
    }

    @Override
    public void removeModClassesFromServiceLayer() {
        try {
            //? if >=1.21.1 {
            // At this point our classes are already loaded on the MC-BOOTSTRAP classloader, but we need to do this here
            // to prevent the TRANSFORMER classloader from loading them again (out Mixin plugin needs to use them to
            // decide whether to apply mixins)
            var packageToOurModulesGetter = unreflectGetter(() -> net.minecraftforge.securemodules.SecureModuleClassLoader.class.getDeclaredField("packageToOurModules"));
            var packageToOurModules = (Map<String, ResolvedModule>) packageToOurModulesGetter.invoke(this.modClassLoader);
            packageToOurModules.entrySet().removeIf(e -> e.getKey().startsWith("me.decce.ixeris.core"));

            // If we don't do this the TRANSFORMER classloader will keep asking itself to load our class, eventually
            // causing a StackOverflowException
            var packageToParentLoaderGetter = unreflectGetter(() -> net.minecraftforge.securemodules.SecureModuleClassLoader.class.getDeclaredField("packageToParentLoader"));
            var packageToParentLoader = (Map<String, ClassLoader>) packageToParentLoaderGetter.invoke(this.modClassLoader);
            packageToParentLoader.entrySet().removeIf(e -> e.getKey().startsWith("me.decce.ixeris.core"));
            //?} else {
            /^// At this point our classes are already loaded on the MC-BOOTSTRAP classloader, but we need to do this here
            // to prevent the LAYER SERVICE classloader from loading them again (out Mixin plugin needs to use them to
            // decide whether to apply mixins)
            var packageLookupGetter = unreflectGetter(() -> cpw.mods.cl.ModuleClassLoader.class.getDeclaredField("packageLookup"));
            var packageLookup = (Map<String, ResolvedModule>) packageLookupGetter.invoke(this.modClassLoader);
            packageLookup.entrySet().removeIf(e -> e.getKey().startsWith("me.decce.ixeris.core"));

            // If we don't do this the LAYER SERVICE classloader will keep asking itself to load our class, eventually
            // causing a StackOverflowException
            var parentLoadersGetter = unreflectGetter(() -> cpw.mods.cl.ModuleClassLoader.class.getDeclaredField("parentLoaders"));
            var parentLoaders = (Map<String, ClassLoader>) parentLoadersGetter.invoke(this.modClassLoader);
            parentLoaders.entrySet().removeIf(e -> e.getKey().startsWith("me.decce.ixeris.core"));
            ^///?}
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
*///?}
