package me.decce.ixeris.mixins;

import me.decce.ixeris.VersionCompatUtils;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, priority = 500)
public abstract class MinecraftMixin {
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;yield()V"))
    private void ixeris$pollEvents(boolean tick, CallbackInfo ci) {
        MainThreadDispatcher.requestPollEvents();
    }

    @Inject(method = "runTick", at = {
            // Process callbacks right before Minecraft processes mouse input, to maximally reduce input latency
            @At(value = "CONSTANT", args = "stringValue=mouse"),
            @At("HEAD")
    }, require = 1)
    private void ixeris$replayQueue(boolean tick, CallbackInfo ci) {
        VersionCompatUtils.profilerPush("callback");
        RenderThreadDispatcher.replayQueue();
        VersionCompatUtils.profilerPop();
    }

    @Inject(method = "destroy", at = @At(value = "INVOKE", target = "Ljava/lang/System;exit(I)V"))
    private void ixeris$destroy(CallbackInfo ci) {
        Ixeris.shouldExit = true;
        if (!Ixeris.getConfig().isFullyBlockingMode()) {
            try {
                Ixeris.mainThread.join(); // wait for the queued GLFW commands to finish
            } catch (InterruptedException ignored) {
            }
        }
    }
}
