package me.decce.ixeris;

import net.minecraft.client.Minecraft;

public class VersionCompatUtils {
    public static void profilerPush(String str) {
        //? if >1.21.1 {
         net.minecraft.util.profiling.Profiler.get().push(str);
        //?} else {
        /*Minecraft.getInstance().getProfiler().push(str);
        *///?}
    }

    public static void profilerPop() {
        //? if >1.21.1 {
         net.minecraft.util.profiling.Profiler.get().pop();
        //?} else {
        /*Minecraft.getInstance().getProfiler().pop();
        *///?}
    }

    public static void profilerPopPush(String str) {
        profilerPop();
        profilerPush(str);
    }

    @SuppressWarnings("ConstantValue")
    public static long getMinecraftWindow() {
        var minecraft = Minecraft.getInstance();
        if (minecraft == null) return 0L;
        var window = minecraft.getWindow();
        if (window == null) return 0L;
        //? if >=1.21.9 {
        return window.handle();
        //?} else {
         /*return window.getWindow();
        *///?}
    }
}
