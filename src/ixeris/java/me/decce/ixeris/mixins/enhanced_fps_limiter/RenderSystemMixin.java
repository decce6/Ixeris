package me.decce.ixeris.mixins.enhanced_fps_limiter;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.concurrent.locks.LockSupport;

@Mixin(value = RenderSystem.class
//? if >=1.19.4 {
    , remap = false
//?}
)
public class RenderSystemMixin {
    @Unique private static final long NANOS_IN_A_SEC = 1_000_000_000L;
    @Unique private static final double MICROSECOND = 0.000001d;
    @Unique private static final double PERIOD = 1000 * MICROSECOND;
    @Unique private static final double SLEEP_THRESHOLD = PERIOD + 100 * MICROSECOND;
    @Unique private static final double AHEAD_THRESHOLD = 10 * MICROSECOND;
    @Shadow private static double lastDrawTime;

    /**
     * @author decce
     * @reason Reimplement FPS limiter without using glfwWaitEventsTimeout, which cannot be called on the render thread.
     * Calling it on the main thread is unrealistic due to the massive overhead of synchronizing states between
     * threads. It also has the problem of possibly returning too late due to processing events.
     */
    @Overwrite
    public static void limitDisplayFPS(int i) {
        double frameTime = 1.0D / (double) i;
        double target = lastDrawTime + frameTime;
        double now = GLFW.glfwGetTime();

        if (target - now > SLEEP_THRESHOLD) {
            long nanos = (long) ((target - now - SLEEP_THRESHOLD) * NANOS_IN_A_SEC);
            // use parkNanos instead of Thread.sleep - the latter keeps sleeping until the slept time is greater than
            // requested (as reported by System.nanoTime()), which is not ideal as it would then require another
            // scheduler period to finish
            // See: https://github.com/openjdk/jdk/blob/161aa5d52865295059f9506b2ba4ffc4b98324de/src/hotspot/share/runtime/javaThread.cpp#L2052-L2083
            LockSupport.parkNanos(nanos);
            now = GLFW.glfwGetTime();
        }

        now = ixeris$busyWait(now, target);

        lastDrawTime = now - target > frameTime ? now : target;
    }

    @Unique
    private static double ixeris$busyWait(double now, double target) {
        while (target - now > AHEAD_THRESHOLD) {
            Thread.onSpinWait();
            now = GLFW.glfwGetTime();
        }
        return now;
    }
}
