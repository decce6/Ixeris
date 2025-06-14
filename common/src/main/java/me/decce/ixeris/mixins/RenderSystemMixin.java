package me.decce.ixeris.mixins;

import com.mojang.blaze3d.TracyFrameCapture;
import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.Ixeris;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderSystem.class, remap = false)
public class RenderSystemMixin {
    // TODO: consider using Overwrite to reduce alloc
    @Inject(method = "pollEvents", at = @At("HEAD"), cancellable = true)
    private static void ixeris$pollEvents(CallbackInfo ci) {
        if (RenderSystem.isOnRenderThread()) {
            ci.cancel();
            Ixeris.replayRenderThreadQueue();
        }
    }

    @Inject(method = "flipFrame", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSwapBuffers(J)V"))
    private static void ixeris$flipFrames(long l, TracyFrameCapture tracyFrameCapture, CallbackInfo ci) {
        /*
         * In Ixeris#putAsleepMainThread a timeout of 200ms is used - much longer than what a frame normally lasts,
         * meaning the main thread would almost always have to be waken up by here. This prevents the Event Polling
         * Thread from using too much processing power. It has the added benefit that, because glfwSwapBuffers() mostly
         * involves work done on the GPU, the CPU is quite idle here so event polling wouldn't battle with rendering,
         * even on machines with few CPU cores.
         *
         * The only potential issue here is that, if glfwPollEvents() take longer to finish than glfwSwapBuffers(),
         * input can be lagged by up to 1 frame. This is very unlikely, from my observations. Nevertheless, a config
         * option is provided for those who don't care about Event Polling Thread taking up a lot of CPU power.
         */
        if (!Ixeris.getConfig().isGreedyEventPolling()) {
            Ixeris.wakeUpMainThread();
        }
    }
}
