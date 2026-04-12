//~ auto_logger

package me.decce.ixeris;

import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.workarounds.WindowMinimizedStateWorkaround;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.main.SilentInitException;
//? if <1.21.11 {
/*import net.minecraft.Util;
*///?} else {
import net.minecraft.util.Util;
//?}
//? if >=26.2 {
/*import com.mojang.blaze3d.platform.ClientShutdownWatchdog;
*///? }

public class RenderThreadStarter implements Runnable {
    private final GameConfig gameConfig;
    private final org.slf4j.Logger logger;

    public RenderThreadStarter(GameConfig gameConfig, org.slf4j.Logger logger) {
        this.gameConfig = gameConfig;
        this.logger = logger;
    }

    @Override
    public void run() {
        Minecraft minecraft = tryCreateMinecraft();

        if (minecraft != null) {
            initGameThread();
            minecraft.run();

            //? if >=26.2 {
            /*try {
                minecraft.exitWorldAndClose();
            } catch (Throwable var69) {
                CrashReport report = CrashReport.forThrowable(var69, "Game shutdown");
                Minecraft.fillReport(null, null, gameConfig.game.launchVersion, null, report);
                Minecraft.crash(null, gameConfig.location.gameDirectory, report, -6);
                return;
            }
            ClientShutdownWatchdog.startShutdownWatchdog("post-main", null, gameConfig, Thread.currentThread().threadId());
            *///?} else {
            try {
                minecraft.stop();
            } finally {
                minecraft.destroy();
            }
            //?}
        }
    }

    private Minecraft tryCreateMinecraft() {
        Minecraft minecraft = null;
        try {
            RenderSystem.initRenderThread();
            beginInitialization();
            minecraft = new Minecraft(gameConfig);
            finishInitialization();
            WindowMinimizedStateWorkaround.init();
            if (Ixeris.getConfig().isBufferedRawMouse() || Ixeris.getConfig().isBufferedRawKeyboard()) {
                Ixeris.input().setup(VersionCompatUtils.getMinecraftWindow());
            }
        } catch (SilentInitException silentInitException) {
            Util.shutdownExecutors();
            logger.warn("Failed to create window: ", silentInitException);
            return null;
        } catch (Throwable throwable) {
            //? if >=1.20.4 {
            CrashReport crashReport2 = CrashReport.forThrowable(throwable, "Initializing game");
            CrashReportCategory crashReportCategory2 = crashReport2.addCategory("Initialization");
            net.minecraft.util.NativeModuleLister.addCrashSection(crashReportCategory2);
            Minecraft.fillReport(minecraft, null, gameConfig.game.launchVersion, null, crashReport2);
            //? if >=26.2 {
            /*Minecraft.crash(minecraft, gameConfig.location.gameDirectory, crashReport2, -1);
            *///?} else {
            Minecraft.crash(minecraft, gameConfig.location.gameDirectory, crashReport2);
            //?}
            //?} else if >= 1.18.2 {
             /*CrashReport crashReport = CrashReport.forThrowable(throwable, "Initializing game");
             CrashReportCategory crashReportCategory = crashReport.addCategory("Initialization");
             net.minecraft.util.NativeModuleLister.addCrashSection(crashReportCategory);
             Minecraft.fillReport(null, null, gameConfig.game.launchVersion, null, crashReport);
             Minecraft.crash(crashReport);
            *///?} else {
            /*CrashReport crashReport = CrashReport.forThrowable(throwable, "Initializing game");
            crashReport.addCategory("Initialization");
            Minecraft.fillReport(null, gameConfig.game.launchVersion, null, crashReport);
            Minecraft.crash(crashReport);
            *///?}
            return null;
        }
        return minecraft;
    }


    public static void initGameThread() {
        //? if <=1.20.4 {
        /*RenderSystem.initGameThread(false);
         *///?}
    }

    public static void beginInitialization() {
        //? if =1.21.1 {
        /*RenderSystem.beginInitialization();
         *///?}
    }

    public static void finishInitialization() {
        //? if =1.21.1 {
        /*RenderSystem.finishInitialization();
         *///?}
    }
}
