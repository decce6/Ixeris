package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderSystem.class, priority = 500
//? if >=1.19.4 {
    , remap = false
//?}
)
public class RenderSystemMixin {
    //? if <=26.2 {
    //? if >=1.19.4 {
    @WrapMethod(method = "pollEvents")
    private static void ixeris$suppressPollEventsWarnings(Operation<Void> original) {
        Ixeris.suppressPollingWarning.getAndIncrement();
        original.call();
        Ixeris.suppressPollingWarning.getAndDecrement();
    }
    //?} else {
    /*@WrapOperation(method = "flipFrame", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwPollEvents()V"))
    private static void ixeris$suppressPollEventsWarnings(Operation<Void> original) {
        Ixeris.suppressPollingWarning.getAndIncrement();
        original.call();
        Ixeris.suppressPollingWarning.getAndDecrement();
    }
    *///?}
    //?}
}
