//~ auto_logger

package me.decce.ixeris;

import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.workarounds.WindowMinimizedStateWorkaround;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.main.SilentInitException;
import net.minecraft.client.resources.language.LanguageManager;
//? if <1.21.11 {
/*import net.minecraft.Util;
*///?} else {
import net.minecraft.util.Util;
//?}

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

            try {
                minecraft.stop();
            } finally {
                minecraft.destroy();
            }
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
        } catch (SilentInitException silentInitException) {
            Util.shutdownExecutors();
            logger.warn("Failed to create window: ", silentInitException);
            return null;
        } catch (Throwable throwable) {
            //? if >=1.20.4 {
            CrashReport crashReport2 = CrashReport.forThrowable(throwable, "Initializing game");
            CrashReportCategory crashReportCategory2 = crashReport2.addCategory("Initialization");
            net.minecraft.util.NativeModuleLister.addCrashSection(crashReportCategory2);
            Minecraft.fillReport(minecraft, (LanguageManager)null, gameConfig.game.launchVersion, (Options)null, crashReport2);
            Minecraft.crash(minecraft, gameConfig.location.gameDirectory, crashReport2);
            //?} else if >= 1.18.2 {
             /*CrashReport crashReport = CrashReport.forThrowable(throwable, "Initializing game");
             CrashReportCategory crashReportCategory = crashReport.addCategory("Initialization");
             net.minecraft.util.NativeModuleLister.addCrashSection(crashReportCategory);
             Minecraft.fillReport((Minecraft)null, (LanguageManager)null, gameConfig.game.launchVersion, (Options)null, crashReport);
             Minecraft.crash(crashReport);
            *///?} else {
            /*CrashReport crashReport = CrashReport.forThrowable(throwable, "Initializing game");
            crashReport.addCategory("Initialization");
            Minecraft.fillReport((LanguageManager)null, gameConfig.game.launchVersion, (Options)null, crashReport);
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
