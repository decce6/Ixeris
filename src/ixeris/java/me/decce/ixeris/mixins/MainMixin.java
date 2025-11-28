//~ auto_logger

package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import me.decce.ixeris.IxerisMinecraftAccessorImpl;
import me.decce.ixeris.IxerisMod;
import me.decce.ixeris.VersionCompatUtils;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.CallbackDispatchers;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MainMixin {
    //? if <=1.20.6 {
    /*@org.spongepowered.asm.mixin.Shadow @org.spongepowered.asm.mixin.Final
     static org.slf4j.Logger LOGGER;
    *///?}


    //? if >=1.19.4 || <=1.18.2 {
    @Inject(method = "main", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;setName(Ljava/lang/String;)V", shift = At.Shift.AFTER), cancellable = true, remap = false)
    //?} else {
    /*@Inject(method = "run", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;setName(Ljava/lang/String;)V", shift = At.Shift.AFTER), cancellable = true, remap = true)
    *///?}
    //? if >=1.21.1 {
    private static void ixeris$main(String[] strings, CallbackInfo ci, @Local GameConfig gameConfig, @Local org.slf4j.Logger logger) {
    //?} else if >=1.19.4 || <=1.18.2 {
     /*private static void ixeris$main(String[] strings, CallbackInfo ci, @Local GameConfig gameConfig) {
    *///?} else {
     /*private static void ixeris$main(String[] strings, boolean bl, CallbackInfo ci, @Local GameConfig gameConfig) {
    *///?}
        ci.cancel();

        Ixeris.mainThread = Thread.currentThread();
        Ixeris.accessor = new IxerisMinecraftAccessorImpl();

        //? if >=1.21 {
        var LOGGER = logger;
        //?}
        IxerisMod.renderThread = new Thread(() -> ixeris$runRenderThread(gameConfig, LOGGER));
        IxerisMod.renderThread.setName(Thread.currentThread().getName());
        IxerisMod.renderThread.start();

        Thread.currentThread().setName(Ixeris.MAIN_THREAD_NAME);
        Thread.currentThread().setPriority(Ixeris.getConfig().getEventPollingThreadPriority());

        //noinspection ConstantValue
        while (Minecraft.getInstance() == null) {
            Thread.yield();
        }

        while (!Ixeris.shouldExit) {
            MainThreadDispatcher.replayQueue();
        }

        // There might be queued calls of glfwDestroyWindow and glfwTerminate, etc. - execute them.
        MainThreadDispatcher.replayQueue();

        Ixeris.LOGGER.info("Exiting event polling thread.");
    }

    @Unique
    private static void ixeris$runRenderThread(GameConfig gameConfig, org.slf4j.Logger logger) {
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
