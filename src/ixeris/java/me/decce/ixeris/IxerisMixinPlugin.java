package me.decce.ixeris;

import me.decce.ixeris.core.Ixeris;
import org.lwjgl.Version;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class IxerisMixinPlugin implements IMixinConfigPlugin {
    static {
        //? if forge {
        /*new me.decce.ixeris.forge.IxerisTransformer().run();
        *///?}
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        boolean skip = mixinClassName.contains("glfw_threading_330") && Version.VERSION_MINOR < 3;
        return Ixeris.getConfig().isEnabled() && !skip;
    }

    @Override
    public void onLoad(String mixinPackage) {
        //? if forge && <=1.16.5 {
        /*com.llamalad7.mixinextras.MixinExtrasBootstrap.init();
        *///?}
        //? if neoforge || forge {
        /*var cl = Ixeris.class.getClassLoader().getName();
        if (!"MC-BOOTSTRAP".equals(cl) && !"SECURE-BOOTSTRAP".equals(cl) && !"app".equals(cl) && !"cpw.mods.modlauncher.TransformingClassLoader".equals(Ixeris.class.getClassLoader().getClass().getName())) {
            throw new IllegalStateException("Ixeris loaded on wrong classloader: " + cl);
        }
        *///?}
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
