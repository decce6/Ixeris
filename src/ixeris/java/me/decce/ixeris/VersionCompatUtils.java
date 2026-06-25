package me.decce.ixeris;

import net.minecraft.client.Minecraft;
//? >=26.2 {
/*import com.mojang.blaze3d.systems.GpuSurface;
import com.mojang.blaze3d.systems.SurfaceException;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.mixins.GpuSurfaceAccessor;
import me.decce.ixeris.mixins.MinecraftAccessor;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
*///? }

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

    public static void reconfigureSwapchain() {
        //? >=26.2 {
        /*var minecraft = Minecraft.getInstance();
        var accessor = (MinecraftAccessor) minecraft;

        try (var stack = MemoryStack.stackPush()) {
            var width = stack.callocInt(1);
            var height = stack.callocInt(1);
            GLFW.glfwGetFramebufferSize(getMinecraftWindow(), width, height);
            if (width.get(0) != 0 || height.get(0) != 0) {
                GpuSurface.PresentMode presentMode = GpuSurface.PresentMode.getSupportedVsyncMode(minecraft.windowSurface().supportedPresentModes(), minecraft.options.enableVsync().get());
                GpuSurface.Configuration config = new GpuSurface.Configuration(width.get(0), height.get(0), presentMode);

                try {
                    ((GpuSurfaceAccessor) minecraft.windowSurface()).ixeris$setHasImageAcquired(false);
                    minecraft.windowSurface().configure(config);
                    accessor.ixeris$setSurfaceIsInvalid(false);
                } catch (SurfaceException exception) {
                    Ixeris.LOGGER.warn("Couldn't configure surface to {}: {}", config, exception);
                    accessor.ixeris$setSurfaceIsInvalid(true);
                }
            }

            accessor.ixeris$setWindowSurfaceNeedsReconfiguring(false);
        }
        *///? }
    }
}
