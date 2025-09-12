package me.decce.ixeris.forge.core;

import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import me.decce.ixeris.core.Ixeris;
import org.jetbrains.annotations.NotNull;

import java.lang.module.ResolvedModule;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static me.decce.ixeris.forge.core.ReflectionHelper.unreflectGetter;

public class IxerisTransformationService implements ITransformationService {
    @Override
    public @NotNull String name() {
        return "ixeris";
    }

    @Override
    public void initialize(IEnvironment environment) {

    }

    @Override
    public void onLoad(IEnvironment env, Set<String> otherServices) throws IncompatibleEnvironmentException {
        IxerisImmediateWindowProvider.init(); // This call is needed when `earlyWindowControl` is disabled

        var cl = (ModuleClassLoader) this.getClass().getClassLoader();
        if (!"LAYER SERVICE".equals(cl.getName())) {
            throw new IllegalStateException("IxerisTransformationService loaded on wrong classloader : " + cl.getName());
        }
        try {
            // At this point our classes are already loaded on the MC-BOOTSTRAP classloader, but we need to do this here
            // to prevent the LAYER SERVICE classloader from loading them again (out Mixin plugin needs to use them to
            // decide whether to apply mixins)
            var getter = unreflectGetter(() -> cl.getClass().getDeclaredField("packageLookup"));
            var packageLookup = (Map<String, ResolvedModule>) getter.invoke(cl);
            packageLookup.entrySet().removeIf(e -> e.getKey().startsWith("me.decce.ixeris.core"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        // We can use mod classes now
        // Suppress the warnings produced by early display window calling glfwPollEvents, which are safely canceled
        Ixeris.suppressEventPollingWarning = true;
    }

    @Override
    public @NotNull List<ITransformer> transformers() {
        return List.of();
    }
}
