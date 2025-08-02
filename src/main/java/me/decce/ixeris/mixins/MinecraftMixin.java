package me.decce.ixeris.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.threading.MainThreadDispatcher;
import net.minecraft.client.Minecraft;

@Mixin(value = Minecraft.class, priority = 500)
public abstract class MinecraftMixin {
    @Inject(method = "runTick", at = @At("HEAD"))
    private void ixeris$pollEvents(boolean tick, CallbackInfo ci) {
        MainThreadDispatcher.requestPollEvents();
    }
    
    @Inject(method = "destroy", at = @At(value = "INVOKE", target = "Ljava/lang/System;exit(I)V"))
    private void ixeris$destroy(CallbackInfo ci) {
        Ixeris.shouldExit = true;
        if (!Ixeris.getConfig().isGreedyEventPolling()) {
            Ixeris.wakeUpMainThread();
        }
        if (!Ixeris.getConfig().isFullyBlockingMode()) {
            try {
                Ixeris.mainThread.join(); // wait for the queued GLFW commands to finish
            } catch (InterruptedException ignored) {
            }
        }
    }
}
