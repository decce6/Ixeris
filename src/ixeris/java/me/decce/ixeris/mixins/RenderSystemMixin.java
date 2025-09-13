package me.decce.ixeris.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderSystem.class, remap = false, priority = 500)
public class RenderSystemMixin {
    /**
     * @see MainThreadDispatcher#requestPollEvents()
     */
    @Inject(method = "pollEvents", at = @At("HEAD"), cancellable = true)
    private static void pollEvents(CallbackInfo ci) {
        ci.cancel();
    }
}
