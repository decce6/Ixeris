package me.decce.ixeris.mixins;

import org.spongepowered.asm.mixin.Mixin;

//? >=26.2 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.systems.GpuSurface;
import com.mojang.blaze3d.systems.SurfaceException;
import com.mojang.blaze3d.vulkan.VulkanGpuSurface;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.VersionCompatUtils;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VulkanGpuSurface.class)
public abstract class VulkanGpuSurfaceMixin {
    @Unique
    private boolean ixeris$noRetryConfigure;

    @ModifyExpressionValue(method = "present", at = @At(value = "NEW", target = "(Ljava/lang/String;)Lcom/mojang/blaze3d/systems/SurfaceException;"), allow = 1)
    private SurfaceException ixeris$recreateSwapchain(SurfaceException original) {
        VersionCompatUtils.reconfigureSwapchain();
        return null;
    }

    @Inject(method = "configure", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/SurfaceException;<init>(Ljava/lang/String;)V"), allow = 1, cancellable = true)
    private void ixeris$suppressEventPolling(GpuSurface.Configuration config, CallbackInfo ci) {
        if (!ixeris$noRetryConfigure) {
            try {
                Ixeris.suppressEventPolling();
                ixeris$noRetryConfigure = true;
                VersionCompatUtils.reconfigureSwapchain();
                ci.cancel();
            }
            finally {
                ixeris$noRetryConfigure = false;
                Ixeris.unsuppressEventPolling();
            }
        }
    }
}
*///? } else {

import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
//? forge
//@Mixin(targets = "")
//? !forge
@Mixin(targets = {})
public class VulkanGpuSurfaceMixin {}
//? }
