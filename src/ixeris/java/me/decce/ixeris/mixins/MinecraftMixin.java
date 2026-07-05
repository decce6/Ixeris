//~ auto_logger
package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.decce.ixeris.VersionCompatUtils;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import net.minecraft.client.Minecraft;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, priority = 500)
public abstract class MinecraftMixin {
    //? if >=26.2 {
    /*@Inject(method = "renderFrame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/CommandEncoder;submit()V"))
    *///?} else if >=26 {
    /*@Inject(method = "renderFrame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;flipFrame(Lcom/mojang/blaze3d/TracyFrameCapture;)V"))
    *///?} else if >=1.21.2 {
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;updateDisplay(Lcom/mojang/blaze3d/TracyFrameCapture;)V"))
    //?} else {
    /*@Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;updateDisplay()V"))
    *///?}
    private void ixeris$pollEvents(boolean tick, CallbackInfo ci) {
        MainThreadDispatcher.requestPollEvents();
    }

    //? if >=26 {
    /*@Redirect(method = "run", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;pollEvents()V"))
    private void ixeris$replayQueue() {
        VersionCompatUtils.profilerPush("callback");
        RenderThreadDispatcher.replayQueue();
        VersionCompatUtils.profilerPop();
    }
    *///?} else {
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;yield()V", shift = At.Shift.AFTER))
    private void ixeris$replayQueue(boolean tick, CallbackInfo ci) {
        VersionCompatUtils.profilerPopPush("callback"); // Pop the "yield" section and push ours
        RenderThreadDispatcher.replayQueue();
        RenderThreadDispatcher.replayErrorQueue();
        // We injected before the "pop" call for the "yield" section, do not pop here
    }
    //?}

    //? if <26.2 {
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
    //?}

    //? >=26.2 {
    /*@Unique
    private boolean ixeris$windowSurfaceNeedsReconfiguring;

    @WrapWithCondition(method = "renderFrame", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", ordinal = 0))
    private boolean ixeris$markForReconfigure(Logger instance, String s, Object o1, Object o2) {
        ixeris$windowSurfaceNeedsReconfiguring = true;
        return true;
    }

    @ModifyExpressionValue(method = "renderFrame", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Minecraft;windowSurfaceNeedsReconfiguring:Z"))
    private boolean ixeris$windowSurfaceNeedsReconfiguring(boolean original) {
        var flag = ixeris$windowSurfaceNeedsReconfiguring;
        ixeris$windowSurfaceNeedsReconfiguring = false;
        return original || flag;
    }
    *///? }
}
