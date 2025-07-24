package me.decce.ixeris.mixins.callback_stack;

import me.decce.ixeris.glfw.callback_stack.CallbackStacks;
import org.lwjgl.system.Callback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Callback.class, remap = false)
public class CallbackMixin {
    @Inject(method = "free(J)V", at = @At("RETURN"))
    private static void free(long functionPointer, CallbackInfo ci) {
        CallbackStacks.invalidate(functionPointer);
    }
}
