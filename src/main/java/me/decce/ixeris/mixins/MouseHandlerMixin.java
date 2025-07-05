package me.decce.ixeris.mixins;

import me.decce.ixeris.threading.MainThreadDispatcher;
import me.decce.ixeris.threading.RenderThreadDispatcher;
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
        MainThreadDispatcher.runAfterPolling(() -> // poll events first, to make the callbacks queued into the render thread
                RenderThreadDispatcher.runLater(() -> ixeris$grabbed = true)); //set grabbed state after the render thread has processed the cursor position callbacks
    }

    @Inject(method = "releaseMouse", at = @At("TAIL"))
    private void ixeris$releaseMouse(CallbackInfo ci) {
        ixeris$grabbed = false;
    }
}