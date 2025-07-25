package me.decce.ixeris.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import me.decce.ixeris.threading.RenderThreadDispatcher;

@Mixin(value = RenderSystem.class, remap = false)
public class RenderSystemMixin {
    // TODO: consider using Overwrite to reduce alloc
    @Inject(method = "pollEvents", at = @At("HEAD"), cancellable = true)
    private static void ixeris$pollEvents(CallbackInfo ci) {
        if (RenderSystem.isOnRenderThread()) {
            ci.cancel();
        }
    }

    @Inject(method = "flipFrame", at = @At("HEAD"))
    //? if >=1.21.5 {
    private static void ixeris$flipFrames(long l, com.mojang.blaze3d.TracyFrameCapture tracyFrameCapture, CallbackInfo ci) {
    //? } else {
    // private static void ixeris$flipFrames(long l, CallbackInfo ci) {
    //? }
        /*
         * We wake up the main thread here to make it poll the latest events. Ideally the main thread should finish
         * polling before the render thread finishes swapping buffers (if it doesn't, there might be up to one frame's
         * input delay, but that's very unlikely.)
         * */
        RenderThreadDispatcher.replayQueue();
        //MainThreadDispatcher.requestPollEvents();
    }

    /*@Inject(method = "flipFrame", at = @At(value = "TAIL"))
    //? if >=1.21.5 {
    private static void ixeris$flipFrames$tail(long l, com.mojang.blaze3d.TracyFrameCapture tracyFrameCapture, CallbackInfo ci) {
    //? } else {
    // private static void ixeris$flipFrames$tail(long l, CallbackInfo ci) {
    //? }
        MainThreadDispatcher.requestPollEvents();
    }*/
}
