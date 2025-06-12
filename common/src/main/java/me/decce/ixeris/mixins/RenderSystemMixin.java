package me.decce.ixeris.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderSystem.class, remap = false)
public class RenderSystemMixin {
    // TODO: consider using Overwrite to reduce alloc
    @Inject(method = "pollEvents", at = @At("HEAD"), cancellable = true)
    private static void ixeris$pollEvents(CallbackInfo ci) {
        if (RenderSystem.isOnRenderThread()) {
            ci.cancel();
        }
    }
}
