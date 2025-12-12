package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow
    public abstract void setIgnoreFirstMove();

    @WrapMethod(method = { "grabMouse", "releaseMouse" })
    private void ixeris$wrapGrabOrReleaseMouse(Operation<Void> original) {
        RenderThreadDispatcher.suppressCursorPosCallbacks();
        RenderThreadDispatcher.clearQueuedCursorPosCallbacks();

        original.call();

        MainThreadDispatcher.run(() -> {
            this.setIgnoreFirstMove();
            RenderThreadDispatcher.unsuppressCursorPosCallbacks();
        });
    }
}
