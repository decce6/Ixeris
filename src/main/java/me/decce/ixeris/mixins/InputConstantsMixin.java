package me.decce.ixeris.mixins;

import com.mojang.blaze3d.platform.InputConstants;
import me.decce.ixeris.Ixeris;
import me.decce.ixeris.threading.MainThreadDispatcher;
import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InputConstants.class)
public class InputConstantsMixin {
    @Inject(method = "grabOrReleaseMouse", at = @At("TAIL"))
    private static void ixeris$grabOrReleaseMouse(long window, int mode, double x, double y, CallbackInfo ci) {
        if (mode == GLFW.GLFW_CURSOR_NORMAL) { // release mouse
            MainThreadDispatcher.runAfterPolling(null); // override the runnable set when grabbing if it has not run
            Ixeris.mouseGrabbed = false;
        }
        else if (mode == GLFW.GLFW_CURSOR_DISABLED) { // grab mouse
            MainThreadDispatcher.runAfterPolling(() -> // poll events first, to make the callbacks queued into the render thread
                    RenderThreadDispatcher.runLater(() -> Ixeris.mouseGrabbed = true)); //set grabbed state after the render thread has processed the cursor position callbacks
            Ixeris.wakeUpMainThread();
        }
    }
}
