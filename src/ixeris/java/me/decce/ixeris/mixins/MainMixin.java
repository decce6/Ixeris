//~ auto_logger

package me.decce.ixeris.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import me.decce.ixeris.IxerisMinecraftAccessorImpl;
import me.decce.ixeris.IxerisMod;
import me.decce.ixeris.RenderThreadStarter;
import me.decce.ixeris.RenderThreadUncaughtExceptionHandler;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.util.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

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
        var renderThread = new Thread(ixeris$createRenderThreadRunnable(gameConfig, LOGGER));
        renderThread.setName(Thread.currentThread().getName());
        renderThread.setUncaughtExceptionHandler(new RenderThreadUncaughtExceptionHandler());
        IxerisMod.renderThread = renderThread;

        renderThread.start();

        Thread.currentThread().setName(Ixeris.MAIN_THREAD_NAME);
        Thread.currentThread().setPriority(Ixeris.getConfig().getEventPollingThreadPriority());

        //noinspection ConstantValue
        while (Minecraft.getInstance() == null && !Ixeris.shouldExit) {
            Thread.yield();
        }

        while (!Ixeris.shouldExit) {
            MainThreadDispatcher.replayQueue();
        }

        // There might be queued calls of glfwDestroyWindow and glfwTerminate, etc. - execute them.
        MainThreadDispatcher.replayQueue();

        Ixeris.LOGGER.info("Exiting event polling thread.");
    }

    // Ixeris needs to start the render thread and thus will always appear in its stacktrace. This causes some crash
    // analyzing tools to blame Ixeris for whatever reason. To prevent this, we intentionally hide ourselves from the
    // stacktrace by defining a hidden class.
    @Unique
    private static Runnable ixeris$createRenderThreadRunnable(GameConfig gameConfig, org.slf4j.Logger logger) {
        Runnable runnable;
        var clazz = RenderThreadStarter.class;
        try (var runnableClazzStream = clazz.getClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class")) {
            var bytes = Objects.requireNonNull(runnableClazzStream).readAllBytes();
            var lookup =MethodHandles.privateLookupIn(clazz, ReflectionHelper.LOOKUP);
            var hiddenClazz = lookup.defineHiddenClass(bytes, true).lookupClass();
            runnable = (Runnable) hiddenClazz.getConstructor(GameConfig.class, org.slf4j.Logger.class).newInstance(gameConfig, logger);
        } catch (Throwable e) {
            Ixeris.LOGGER.error("Failed to obtain hidden render thread runnable!", e);
            runnable = new RenderThreadStarter(gameConfig, logger);
        }
        return runnable;
    }
}
