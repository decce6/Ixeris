package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.Ixeris;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.main.Main;
import net.minecraft.client.main.SilentInitException;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.util.NativeModuleLister;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Main.class, remap = false)
public class MainMixin {
    @Inject(method = "main", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;setName(Ljava/lang/String;)V", shift = At.Shift.AFTER), cancellable = true)
    private static void ixeris$main(String[] strings, CallbackInfo ci, @Local GameConfig gameConfig, @Local Logger logger) { //TODO: this CallbackInfo is never collected by GC
        ci.cancel();

        Ixeris.mainThread = Thread.currentThread();

        Thread renderThread = new Thread(() -> ixeris$runRenderThread(gameConfig, logger));
        renderThread.setName(Thread.currentThread().getName());
        renderThread.start();

        Thread.currentThread().setName("Ixeris Event Polling Thread"); // include our name so people know we are to blame when things go wrong
        Thread.currentThread().setPriority(Ixeris.getConfig().getEventPollingThreadPriority());

        //noinspection ConstantValue
        while (Minecraft.getInstance() == null || !Minecraft.getInstance().isRunning()) {
            Thread.yield();
        }

        while (Minecraft.getInstance().isRunning()) {
            Ixeris.replayMainThreadQueue();
            if (Ixeris.glfwInitialized) {
                GLFW.glfwPollEvents();
            }
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

        Ixeris.LOGGER.info("Exiting event polling thread.");
    }

    @Unique
    private static void ixeris$runRenderThread(GameConfig gameConfig, Logger logger) {
        Minecraft minecraft = null;
        try {
            RenderSystem.initRenderThread();
            minecraft = new Minecraft(gameConfig);
        } catch (SilentInitException silentInitException) {
            Util.shutdownExecutors();
            logger.warn("Failed to create window: ", silentInitException);
            return;
        } catch (Throwable throwable2) {
            CrashReport crashReport2 = CrashReport.forThrowable(throwable2, "Initializing game");
            CrashReportCategory crashReportCategory2 = crashReport2.addCategory("Initialization");
            NativeModuleLister.addCrashSection(crashReportCategory2);
            Minecraft.fillReport(minecraft, (LanguageManager)null, gameConfig.game.launchVersion, (Options)null, crashReport2);
            Minecraft.crash(minecraft, gameConfig.location.gameDirectory, crashReport2);
            return;
        }

        minecraft.run();

        try {
            minecraft.stop();
        } finally {
            minecraft.destroy();
        }
    }
}
