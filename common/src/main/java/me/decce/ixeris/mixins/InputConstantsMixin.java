package me.decce.ixeris.mixins;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.Ixeris;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InputConstants.class)
public class InputConstantsMixin {
    @Inject(method = "grabOrReleaseMouse", at = @At("HEAD"))
    private static void ixeris$grabOrReleaseMouse(long l, int i, double d, double e, CallbackInfo ci) {
        if (RenderSystem.isOnRenderThread()) {
            Ixeris.suppressCursorPosCallbacks = true; // Fixes https://github.com/decce6/Ixeris/issues/3
        }
    }
}
