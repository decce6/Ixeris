package me.decce.ixeris.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
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
    //? if >=1.19.4 {
    /**
     * @see MainThreadDispatcher#requestPollEvents()
     */
    @Inject(method = "pollEvents", at = @At("HEAD"), cancellable = true)
    private static void ixeris$cancelPollEvents(CallbackInfo ci) {
        ci.cancel();
    }
    //?} else {
    /*@Redirect(method = "flipFrame", at = @At(value="INVOKE", target="Lorg/lwjgl/glfw/GLFW;glfwPollEvents()V", ordinal = 0, remap = false))
    private static void ixeris$cancelPollEvents$0() {
        // no-op
    }

    @Redirect(method = "flipFrame", at = @At(value="INVOKE", target="Lorg/lwjgl/glfw/GLFW;glfwPollEvents()V", ordinal = 1, remap = false))
    private static void ixeris$cancelPollEvents$1() {
        // no-op
    }
    *///?}
}
