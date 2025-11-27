package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.platform.InputConstants;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(InputConstants.class)
public class InputConstantsMixin {
    @WrapMethod(method = "grabOrReleaseMouse")
    //? if <=1.21.8 {
    /*private static void ixeris$grabOrReleaseMouse(long window, int i, double x, double y, Operation<Void> original)
    *///?} else {
     private static void ixeris$grabOrReleaseMouse(com.mojang.blaze3d.platform.Window window, int i, double x, double y, Operation<Void> original)
    //?}
    {
        MainThreadDispatcher.runNowImpl(() -> original.call(window, i, x, y));
    }
}
