package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import me.decce.ixeris.Ixeris;
import me.decce.ixeris.VersionCompatUtils;
import me.decce.ixeris.threading.MainThreadDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.main.Main;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MainMixin {
    //? if <=1.20.4 {
    // @org.spongepowered.asm.mixin.Shadow @org.spongepowered.asm.mixin.Final
    // static org.slf4j.Logger LOGGER;
    //? }

    @Inject(method = "main", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;setName(Ljava/lang/String;)V", shift = At.Shift.AFTER), cancellable = true, remap = false)
    //? if >=1.21.1 {
    private static void ixeris$main(String[] strings, CallbackInfo ci, @Local GameConfig gameConfig, @Local Logger logger) {
    //? } else {
    // private static void ixeris$main(String[] strings, CallbackInfo ci, @Local GameConfig gameConfig) {
    //? }
        ci.cancel();

        Ixeris.mainThread = Thread.currentThread();

        //? if >=1.21.1 {
        var LOGGER = logger;
        //? }
        Ixeris.renderThread = new Thread(() -> ixeris$runRenderThread(gameConfig, LOGGER));
        Ixeris.renderThread.setName(Thread.currentThread().getName());
        Ixeris.renderThread.start();

        Thread.currentThread().setName(Ixeris.MAIN_THREAD_NAME);
        Thread.currentThread().setPriority(Ixeris.getConfig().getEventPollingThreadPriority());

        //noinspection ConstantValue
        while (Minecraft.getInstance() == null) {
            Thread.yield();
        }

        while (!Ixeris.shouldExit) {
            if (Ixeris.glfwInitialized) {
                GLFW.glfwPollEvents();
            }
            MainThreadDispatcher.replayQueue();
        }

        // There might be queued calls of glfwDestroyWindow and glfwTerminate, etc. - execute them.
        MainThreadDispatcher.replayQueue();

        Ixeris.LOGGER.info("Exiting event polling thread.");
    }

    @Unique
    private static void ixeris$runRenderThread(GameConfig gameConfig, Logger logger) {
        Minecraft minecraft = VersionCompatUtils.tryCreateMinecraft(gameConfig, logger);

        if (minecraft != null) {
            VersionCompatUtils.initGameThread();
            minecraft.run();

            try {
                minecraft.stop();
            } finally {
                minecraft.destroy();
            }
        }
    }
}
