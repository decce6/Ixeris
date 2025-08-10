package me.decce.ixeris;

import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.workarounds.WindowMinimizedStateWorkaround;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.main.SilentInitException;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.util.NativeModuleLister;
import org.slf4j.Logger;

public class VersionCompatUtils {
    public static void initGameThread() {
        //? if <=1.20.4 {
        //  RenderSystem.initGameThread(false);
        //? }
    }

    public static void beginInitialization() {
        //? if =1.21.1 {
        //  RenderSystem.beginInitialization();
        //? }
    }

    public static void finishInitialization() {
        //? if =1.21.1 {
        //  RenderSystem.finishInitialization();
        //? }
    }

    public static Minecraft tryCreateMinecraft(GameConfig gameConfig, Logger logger) {
        Minecraft minecraft = null;
        try {
            RenderSystem.initRenderThread();
            VersionCompatUtils.beginInitialization();
            minecraft = new Minecraft(gameConfig);
            VersionCompatUtils.finishInitialization();
            WindowMinimizedStateWorkaround.init();
        } catch (SilentInitException silentInitException) {
            Util.shutdownExecutors();
            logger.warn("Failed to create window: ", silentInitException);
            return null;
        } catch (Throwable throwable) {
            //? if >=1.20.4 {
            CrashReport crashReport2 = CrashReport.forThrowable(throwable, "Initializing game");
            CrashReportCategory crashReportCategory2 = crashReport2.addCategory("Initialization");
            NativeModuleLister.addCrashSection(crashReportCategory2);
            Minecraft.fillReport(minecraft, (LanguageManager)null, gameConfig.game.launchVersion, (Options)null, crashReport2);
            Minecraft.crash(minecraft, gameConfig.location.gameDirectory, crashReport2);
            //? } else {
            // CrashReport crashReport = CrashReport.forThrowable(throwable, "Initializing game");
            // CrashReportCategory crashReportCategory = crashReport.addCategory("Initialization");
            // NativeModuleLister.addCrashSection(crashReportCategory);
            // Minecraft.fillReport((Minecraft)null, (LanguageManager)null, gameConfig.game.launchVersion, (Options)null, crashReport);
            // Minecraft.crash(crashReport);
            //? }
            return null;
        }
        return minecraft;
    }

    public static void profilerPush(String str) {
        //? if >1.21.1 {
        net.minecraft.util.profiling.Profiler.get().push(str);
        //? } else {
        // Minecraft.getInstance().getProfiler().push(str);
        //? }
    }

    public static void profilerPop() {
        //? if >1.21.1 {
        net.minecraft.util.profiling.Profiler.get().pop();
        //? } else {
        // Minecraft.getInstance().getProfiler().pop();
        //? }
    }

    public static void profilerPopPush(String str) {
        profilerPop();
        profilerPush(str);
    }
}
