package me.decce.ixeris.mixins;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.logging.LogUtils;
import me.decce.ixeris.Ixeris;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.OutOfMemoryScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.SingleTickProfiler;
import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow @Nullable private Supplier<CrashReport> delayedCrash;
    @Shadow private ProfilerFiller profiler;
    @Shadow protected abstract ProfilerFiller constructProfiler(boolean bl, SingleTickProfiler arg);
    @Shadow protected abstract boolean shouldRenderFpsPie();
    @Shadow private MetricsRecorder metricsRecorder;
    @Shadow protected abstract void runTick(boolean bl);
    @Shadow protected abstract void finishProfilers(boolean bl, SingleTickProfiler arg);
    @Shadow public abstract void emergencySave();
    @Shadow public abstract void setScreen(Screen arg);
    @Shadow @Final private static Logger LOGGER;
    @Shadow public abstract CrashReport fillReport(CrashReport arg);
    @Shadow @Final private Window window;
    @Shadow private Thread gameThread;
    @Shadow @Final private GameNarrator narrator;
    @Shadow @Nullable public ClientLevel level;
    @Shadow public abstract void clearLevel();
    @Shadow @Nullable public Screen screen;
    @Shadow public abstract void close();

    @Inject(method = "run", at = @At("HEAD"), cancellable = true)
    private void ixeris$injectRun(CallbackInfo ci) {
        ci.cancel();

        Thread thread = new Thread(this::ixeris$run);
        thread.setPriority(Ixeris.getConfig().getRenderThreadPriority());
        thread.setName(Thread.currentThread().getName());

        Thread.currentThread().setName("Ixeris Event Polling Thread"); // include our name so people know we are to blame when things go wrong
        int priority = Ixeris.getConfig().getEventPollingThreadPriority();
        if (priority >= Thread.MIN_PRIORITY && priority <= Thread.MAX_PRIORITY) {
            Thread.currentThread().setPriority(priority);
        }
        Ixeris.mainThread = Thread.currentThread();

        RenderSystemAccessor.setRenderThread(thread);
        this.gameThread = thread;

        GLFW.glfwMakeContextCurrent(0);
        thread.start();
    }

    @Unique
    private void ixeris$run() {
        GLFW.glfwMakeContextCurrent(this.window.getWindow());
        GL.createCapabilities();

        try {
            boolean bl = false;

            while (Minecraft.getInstance().isRunning()) {
                if (this.delayedCrash != null) {
                    Minecraft.crash((CrashReport)this.delayedCrash.get());
                    return;
                }

                try {
                    SingleTickProfiler singleTickProfiler = SingleTickProfiler.createTickProfiler("Renderer");
                    boolean bl2 = this.shouldRenderFpsPie();
                    this.profiler = this.constructProfiler(bl2, singleTickProfiler);
                    this.profiler.startTick();
                    this.metricsRecorder.startTick();
                    this.runTick(!bl);
                    this.metricsRecorder.endTick();
                    this.profiler.endTick();
                    this.finishProfilers(bl2, singleTickProfiler);
                } catch (OutOfMemoryError outOfMemoryError) {
                    if (bl) {
                        throw outOfMemoryError;
                    }

                    this.emergencySave();
                    this.setScreen(new OutOfMemoryScreen());
                    System.gc();
                    LOGGER.error(LogUtils.FATAL_MARKER, "Out of memory", outOfMemoryError);
                    bl = true;
                }
            }

            BufferUploader.reset();

            Minecraft.getInstance().stop();
            this.ixeris$destroy();

        } catch (ReportedException reportedException) {
            this.fillReport(reportedException.getReport());
            this.emergencySave();
            LOGGER.error(LogUtils.FATAL_MARKER, "Reported exception thrown!", reportedException);
            Minecraft.crash(reportedException.getReport());
        } catch (Throwable throwable) {
            CrashReport crashReport = this.fillReport(new CrashReport("Unexpected error", throwable));
            LOGGER.error(LogUtils.FATAL_MARKER, "Unreported exception thrown!", throwable);
            this.emergencySave();
            Minecraft.crash(crashReport);
        }
    }

    // copied from vanilla
    @Unique
    private void ixeris$destroy() {
        try {
            LOGGER.info("Stopping!");
            try {
                this.narrator.destroy();
            } catch (Throwable ignored) {
            }
            try {
                if (this.level != null) {
                    this.level.disconnect();
                }
                this.clearLevel();
            } catch (Throwable ignored) {
            }
            if (this.screen != null) {
                this.screen.removed();
            }
            this.close();
        } finally {
            Util.timeSource = System::nanoTime;
            if (this.delayedCrash == null) {
                try {
                    Ixeris.shouldExit = true;
                    GLFW.glfwPostEmptyEvent(); // wakes up the main thread
                    if (!Ixeris.getConfig().isFullyBlockingMode()) {
                        Ixeris.mainThread.join(); // wait for the queued GLFW commands to finish
                    }
                } catch (InterruptedException ignored) {
                }
                System.exit(0);
            }
        }
    }
}
