package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    private boolean mouseGrabbed;

    @WrapMethod(method = "grabMouse")
    private void ixeris$wrapGrabMouse(Operation<Void> original) {
        var shouldGrab = this.minecraft.isWindowActive() && !this.mouseGrabbed;

        original.call();

        if (shouldGrab) {
            RenderThreadDispatcher.clearQueuedCursorPosCallbacks();
        }
    }

    @WrapMethod(method = "releaseMouse")
    private void ixeris$wrapReleaseMouse(Operation<Void> original) {
        var shouldRelease = this.mouseGrabbed;

        original.call();

        if (shouldRelease) {
            RenderThreadDispatcher.clearQueuedCursorPosCallbacks();
        }
    }
}
