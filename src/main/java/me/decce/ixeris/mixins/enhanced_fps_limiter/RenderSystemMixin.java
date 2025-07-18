package me.decce.ixeris.mixins.enhanced_fps_limiter;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = RenderSystem.class, remap = false)
public class RenderSystemMixin {
    @Unique private static final long MILLIS_IN_A_SECOND = 1_000L;
    @Unique private static final long NANOS_IN_A_SECOND = 1_000_000_000L;
    @Unique private static final double MICROSECOND = 0.000001d;
    @Unique private static final double AHEAD_THRESHOLD = 10 * MICROSECOND;
    @Unique private static final double SLEEP_OFFSET = 500 * MICROSECOND; // Sleep less than required to account for timer imprecisions
    @Unique private static final double SLEEP_THRESHOLD = 1500 * MICROSECOND; // Do not try to sleep less than this time, since that would be very unreliable
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
        double wait = target - now;
        if (wait > SLEEP_THRESHOLD) {
            wait -= SLEEP_OFFSET; // Sleep a bit less than required
            long millis = (long) (wait * MILLIS_IN_A_SECOND);
            int nanos = (int) (wait * NANOS_IN_A_SECOND - millis * ((double) NANOS_IN_A_SECOND / MILLIS_IN_A_SECOND));
            try {
                Thread.sleep(millis, nanos);
            } catch (InterruptedException ignored) {
            }
        }

        now = ixeris$busyWait(target);

        lastDrawTime = now;
    }

    @Unique
    private static double ixeris$busyWait(double target) {
        double now;
        do {
            now = GLFW.glfwGetTime();
        } while (target - now > AHEAD_THRESHOLD);
        return now;
    }
}
