package me.decce.ixeris.mixins;

import org.spongepowered.asm.mixin.Mixin;

//? >=26.2 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.systems.GpuSurface;
import com.mojang.blaze3d.systems.SurfaceException;
import com.mojang.blaze3d.vulkan.VulkanGpuSurface;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.VersionCompatUtils;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VulkanGpuSurface.class)
public abstract class VulkanGpuSurfaceMixin {
    @Shadow
    private boolean swapchainOutOfDate;
    @Shadow
    private boolean swapchainSuboptimal;
    @Shadow
    public abstract void acquireNextTexture() throws SurfaceException;

    @ModifyExpressionValue(method = "present", at = @At(value = "NEW", target = "(Ljava/lang/String;)Lcom/mojang/blaze3d/systems/SurfaceException;"), allow = 1)
    private SurfaceException ixeris$recreateSwapchain(SurfaceException original) {
        VersionCompatUtils.reconfigureSwapchain();
        this.swapchainOutOfDate = false;
        this.swapchainSuboptimal = false;
        return null;
    }

    @Inject(method = "configure", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/SurfaceException;<init>(Ljava/lang/String;)V"), allow = 1, cancellable = true)
    private void ixeris$suppressEventPolling(GpuSurface.Configuration config, CallbackInfo ci) {
        try {
            Ixeris.suppressEventPolling();
            VersionCompatUtils.reconfigureSwapchain();
            ci.cancel();
        }
        finally {
            Ixeris.unsuppressEventPolling();
        }
    }

    @Inject(method = "acquireNextTexture", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/SurfaceException;<init>(Ljava/lang/String;)V"), allow = 1, cancellable = true)
    private void ixeris$acquireNextTexture(CallbackInfo ci) throws SurfaceException {
        this.swapchainOutOfDate = false;
        this.swapchainSuboptimal = false;
        VersionCompatUtils.reconfigureSwapchain();
        ci.cancel();
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
