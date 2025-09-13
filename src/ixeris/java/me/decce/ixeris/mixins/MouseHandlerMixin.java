package me.decce.ixeris.mixins;

import me.decce.ixeris.core.Ixeris;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MouseHandler.class, priority = 100)
public class MouseHandlerMixin {
    @Inject(method = "isMouseGrabbed", at = @At(value= "RETURN"), cancellable = true)
    private void ixeris$isMouseGrabbed(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValueZ() && Ixeris.mouseGrabbed);
    }
}