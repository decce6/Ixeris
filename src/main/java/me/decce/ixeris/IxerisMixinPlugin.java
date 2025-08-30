package me.decce.ixeris;

import me.decce.ixeris.core.Ixeris;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class IxerisMixinPlugin implements IMixinConfigPlugin {
    public static final String MIXIN_PACKAGE_ROOT = "me.decce.ixeris.mixins";

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(MIXIN_PACKAGE_ROOT)) {
            Ixeris.LOGGER.error("Unexpected mixin class name! Got {}, expected to be in package {}.", mixinClassName, MIXIN_PACKAGE_ROOT);
            return false;
        }
        return Ixeris.getConfig().isEnabled();
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void onLoad(String mixinPackage) {

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
