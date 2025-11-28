package me.decce.ixeris.mixins.enhanced_fps_limiter;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = RenderSystem.class
//? if >=1.18.2 {
    , remap = false
//? }
)
public class RenderSystemMixin {
    @Unique private static final double MICROSECOND = 0.000001d;
    @Unique private static final double SLEEP_THRESHOLD = 2000 * MICROSECOND;
    @Unique private static final double YIELD_THRESHOLD = 200 * MICROSECOND;
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

        now = ixeris$sleep(now, target);
        now = ixeris$yield(now, target);
        now = ixeris$busyWait(now, target);

        lastDrawTime = now - target > frameTime ? now : target;
    }

    @Unique
    private static double ixeris$sleep(double now, double target) {
        // Vanilla uses glfwWaitEventsTimeout, which wakes up every time it receives an event
        // This approach should work better.
        while (target - now > SLEEP_THRESHOLD) {
            try {
                Thread.sleep(1L); // only sleep 1ms to account for timer inaccuracies
                now = GLFW.glfwGetTime();
            } catch (InterruptedException ignored) {
            }
        }
        return now;
    }

    @Unique
    private static double ixeris$yield(double now, double target) {
        while (target - now > YIELD_THRESHOLD) {
            try {
                Thread.sleep(0L);
                now = GLFW.glfwGetTime();
            } catch (InterruptedException ignored) {
            }
        }
        return now;
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
