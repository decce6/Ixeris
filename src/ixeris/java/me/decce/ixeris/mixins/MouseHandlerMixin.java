package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    private boolean mouseGrabbed;

    @ModifyExpressionValue(method = "handleAccumulatedMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MouseHandler;isMouseGrabbed()Z"))
    private boolean ixeris$modifyMouseGrabbed(boolean original) {
        return original && Ixeris.mouseGrabbed;
    }

    @ModifyExpressionValue(method = "onMove", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MouseHandler;ignoreFirstMove:Z", opcode = Opcodes.GETFIELD))
    private boolean ixeris$modifyIgnoreFirstMove(boolean original) {
        var ignore = original || Ixeris.ignoreFirstMove;
        Ixeris.ignoreFirstMove = false;
        return ignore;
    }

    @WrapMethod(method = "grabMouse")
    private void ixeris$wrapGrabMouse(Operation<Void> original) {
        var shouldGrab = this.minecraft.isWindowActive() && !this.mouseGrabbed;

        original.call();

        if (shouldGrab) {
            RenderThreadDispatcher.clearQueuedCursorPosCallbacks();
            // First send to main thread - for sync with glfwSetInputMode
            MainThreadDispatcher.run(() -> {
                // Then send to render thread - these should be set after previous cursor pos callbacks are processed
                RenderThreadDispatcher.runLater(() -> {
                    Ixeris.mouseGrabbed = true;
                    Ixeris.ignoreFirstMove = true;
                });
            });
        }
    }

    @WrapMethod(method = "releaseMouse")
    private void ixeris$wrapReleaseMouse(Operation<Void> original) {
        var shouldRelease = this.mouseGrabbed;

        original.call();

        if (shouldRelease) {
            RenderThreadDispatcher.clearQueuedCursorPosCallbacks();
            // See comments above
            MainThreadDispatcher.run(() -> {
                RenderThreadDispatcher.runLater(() -> {
                    Ixeris.mouseGrabbed = false;
                });
            });
        }
    }
}
