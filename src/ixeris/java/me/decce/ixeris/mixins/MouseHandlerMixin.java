package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow
    public abstract void setIgnoreFirstMove();
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    private boolean mouseGrabbed;

    @WrapMethod(method = "grabMouse")
    private void ixeris$wrapGrabMouse(Operation<Void> original) {
        var shouldGrab = this.minecraft.isWindowActive() && !this.mouseGrabbed;
        if (shouldGrab) {
            RenderThreadDispatcher.suppressCursorPosCallbacks();
            RenderThreadDispatcher.clearQueuedCursorPosCallbacks();
        }

        original.call();

        if (shouldGrab) {
            MainThreadDispatcher.run(() -> {
                this.setIgnoreFirstMove();
                RenderThreadDispatcher.unsuppressCursorPosCallbacks();
            });
        }
    }

    @WrapMethod(method = "releaseMouse")
    private void ixeris$wrapReleaseMouse(Operation<Void> original) {
        var shouldRelease = this.mouseGrabbed;
        if (shouldRelease) {
            RenderThreadDispatcher.suppressCursorPosCallbacks();
            RenderThreadDispatcher.clearQueuedCursorPosCallbacks();
        }

        original.call();

        if (shouldRelease) {
            MainThreadDispatcher.run(() -> {
                this.setIgnoreFirstMove();
                RenderThreadDispatcher.unsuppressCursorPosCallbacks();
            });
        }
    }
}
