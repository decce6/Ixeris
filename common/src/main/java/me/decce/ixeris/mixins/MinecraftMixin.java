package me.decce.ixeris.mixins;

import com.mojang.blaze3d.platform.Window;
import me.decce.ixeris.Ixeris;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, priority = 500)
public abstract class MinecraftMixin {
    @Shadow @Final private Window window;

    @Inject(method = "run", at = @At("HEAD"), cancellable = true)
    private void ixeris$run$pre(CallbackInfo ci) {
        if (Ixeris.mainThread == null) {
            ci.cancel();

            Thread thread = new Thread(() -> Minecraft.getInstance().run());
            thread.setName(Thread.currentThread().getName());

            Thread.currentThread().setName("Ixeris Event Polling Thread"); // include our name so people know we are to blame when things go wrong
            Thread.currentThread().setPriority(Ixeris.getConfig().getEventPollingThreadPriority());
            Ixeris.mainThread = Thread.currentThread();

            RenderSystemAccessor.setRenderThread(thread);

            GLFW.glfwMakeContextCurrent(0);
            thread.start();
        }
    }

    @Inject(method = "run", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;", shift = At.Shift.AFTER))
    private void ixeris$run$inRenderThread(CallbackInfo ci) {
        GLFW.glfwMakeContextCurrent(this.window.getWindow());
        GL.createCapabilities();
    }

    @Inject(method = "run", at = @At("TAIL"))
    private void ixeris$run$post(CallbackInfo ci) {
        Minecraft.getInstance().stop();
        Minecraft.getInstance().destroy();
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
