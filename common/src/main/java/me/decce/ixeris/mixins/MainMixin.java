package me.decce.ixeris.mixins;

import me.decce.ixeris.Ixeris;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.Main;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MainMixin {
    // Note: Minecraft.run() returns instantly on the main thread (see MinecraftMixin)
    @Inject(method = "main", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;run()V", shift = At.Shift.AFTER), cancellable = true)
    private static void ixeris$main(String[] strings, CallbackInfo ci) { //TODO: this CallbackInfo is never collected by GC
        while (Minecraft.getInstance().isRunning()) {
            Ixeris.replayMainThreadQueue();
            GLFW.glfwPollEvents();
            if (!Ixeris.getConfig().isGreedyEventPolling()) {
                // woke up on next glfwSwapBuffers() call, or when a GLFW function needs to be called from the main
                // thread
                Ixeris.putAsleepMainThread();
            }
        }

        while (!Ixeris.shouldExit) { // wait until the Render Thread has sent all GLFW commands for termination
            Thread.yield();
            Ixeris.replayMainThreadQueue();
        }

        ci.cancel();

        Ixeris.LOGGER.info("Exiting event polling thread.");
    }
}
