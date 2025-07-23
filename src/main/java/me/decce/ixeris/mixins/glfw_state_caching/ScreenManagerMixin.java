package me.decce.ixeris.mixins.glfw_state_caching;

import com.mojang.blaze3d.platform.MonitorCreator;
import com.mojang.blaze3d.platform.ScreenManager;
import me.decce.ixeris.glfw.state_caching.GlfwCacheManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenManager.class)
public class ScreenManagerMixin {
    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void ixeris$ctor(MonitorCreator monitorCreator, CallbackInfo ci) {
        GlfwCacheManager.getGlobalCache().initializeMonitorCache();
    }
}
