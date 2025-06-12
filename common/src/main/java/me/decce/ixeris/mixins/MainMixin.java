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
    // Note: Minecraft.run() returns instantly (see MinecraftMixin)
    @Inject(method = "main", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;run()V", shift = At.Shift.AFTER), cancellable = true)
    private static void ixeris$main(String[] strings, CallbackInfo ci) { //TODO: this CallbackInfo is never collected by GC
        while (Minecraft.getInstance().isRunning()) {
            GLFW.glfwWaitEventsTimeout(0.2d); // The timeout is to prevent missing GLFW calls when there are no event
            Ixeris.replayQueue();
        }

        while (!Ixeris.shouldExit) { // wait until the Render Thread has sent all GLFW commands for termination
            Thread.yield();
            Ixeris.replayQueue();
        }

        ci.cancel();

        Ixeris.LOGGER.info("Exiting event polling thread.");
    }
}
