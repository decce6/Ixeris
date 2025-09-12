package me.decce.ixeris;

import me.decce.ixeris.core.Ixeris;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class IxerisMixinPlugin implements IMixinConfigPlugin {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return Ixeris.getConfig().isEnabled();
    }

    @Override
    public void onLoad(String mixinPackage) {
        //? if neoforge || forge {
        /*var cl = Ixeris.class.getClassLoader().getName();
        if (!"MC-BOOTSTRAP".equals(cl)) {
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
