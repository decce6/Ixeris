package me.decce.ixeris.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.decce.ixeris.threading.RenderThreadDispatcher;
import net.minecraft.client.Minecraft;

@Mixin(value = Minecraft.class, priority = 100001)
public abstract class MinecraftMixinEx {
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;yield()V"))
    private void ixeris$replayQueue(boolean tick, CallbackInfo ci) {
        RenderThreadDispatcher.replayQueue();
    }
}
