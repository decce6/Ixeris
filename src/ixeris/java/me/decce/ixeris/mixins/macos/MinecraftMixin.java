package me.decce.ixeris.mixins.macos;

import me.decce.ixeris.IxerisMod;
import me.decce.ixeris.core.util.PlatformHelper;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.CGL;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "runTick", at = @At(value = "CONSTANT", args = "stringValue=Render"))
    private void ixeris$beforeRender(CallbackInfo ci) {
        if (PlatformHelper.isMacOs()) {
            long context = CGL.CGLGetCurrentContext();
            if (context != 0L) {
                CGL.CGLLockContext(context);
                IxerisMod.lockedContext = context;
            }
        }
    }

    //? if >=26 {
    /*@Inject(method = "renderFrame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;updateDisplay(Lcom/mojang/blaze3d/TracyFrameCapture;)V", shift = At.Shift.AFTER))
     *///?} else if >=1.21.4 {
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;updateDisplay(Lcom/mojang/blaze3d/TracyFrameCapture;)V", shift = At.Shift.AFTER))
            //?} else {
    /*@Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;updateDisplay()V", shift = At.Shift.AFTER))
     *///?}
    private void ixeris$afterRender(CallbackInfo ci) {
        if (PlatformHelper.isMacOs()) {
            long context = CGL.CGLGetCurrentContext();
            if (context != 0L) {
                CGL.CGLUnlockContext(context);
                IxerisMod.lockedContext = 0L;
            }
        }
    }
}

