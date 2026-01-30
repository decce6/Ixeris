package me.decce.ixeris;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.MixinHelper;
import me.decce.ixeris.core.util.PlatformHelper;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

// Compiled for Java 8 for 1.16 compatibility
@SuppressWarnings("unused")
public class IxerisMixinPlugin implements IMixinConfigPlugin {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();
    static {
        if (PlatformHelper.isAndroid()) {
            LOGGER.warn("Ixeris is not supported on Android!");
        }
        if (!JavaHelper.JAVA_SUPPORTED) {
            LOGGER.warn("Java 17+ is required for Ixeris to work!");
        } else if (JavaHelper.message != null) {
            LOGGER.warn(JavaHelper.message);
        }
        //? if forge {
        /*if (JavaHelper.JAVA_SUPPORTED) {
            new me.decce.ixeris.forge.IxerisTransformer().run();
        }
        *///?}
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return JavaHelper.JAVA_SUPPORTED && Ixeris.getConfig().isEnabled() && MixinHelper.shouldApply(mixinClassName);
    }

    @Override
    public void onLoad(String mixinPackage) {
        //? if forge && <=1.16.5 {
        /*if (JavaHelper.JAVA_SUPPORTED) {
            com.llamalad7.mixinextras.MixinExtrasBootstrap.init();
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
