package me.decce.ixeris.mixins;

import me.decce.ixeris.Ixeris;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Shadow
    private boolean mouseGrabbed;
    @Unique
    private boolean ixeris$grabbed;

    @Inject(method = "isMouseGrabbed", at = @At(value= "HEAD"), cancellable = true)
    private void ixeris$isMouseGrabbed(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(mouseGrabbed && ixeris$grabbed);
    }

    @Inject(method = "grabMouse", at = @At("TAIL"))
    private void ixeris$grabMouse(CallbackInfo ci) {
        Ixeris.clearQueuedCursorPosCallbacks();
        Ixeris.runLaterOnMainThread(() -> ixeris$grabbed = true);
    }

    @Inject(method = "releaseMouse", at = @At("TAIL"))
    private void ixeris$releaseMouse(CallbackInfo ci) {
        Ixeris.clearQueuedCursorPosCallbacks();
        ixeris$grabbed = false;
    }
}