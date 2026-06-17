package me.decce.ixeris.core.mixins;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.PollingException;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.locks.LockSupport;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwWaitEventsTimeout", at = @At("HEAD"), cancellable = true)
    private static void ixeris$waitEventsTimeout(double timeout, CallbackInfo ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        ci.cancel();
        if (!Ixeris.inEarlyDisplay && Ixeris.getConfig().shouldLogPollingCalls()) {
            Ixeris.LOGGER.warn("", new PollingException());
        }
        if (Ixeris.accessor.isOnRenderThread() && timeout <= 1d) {
            LockSupport.parkNanos((long) (timeout * 1_000_000_000));
        }
    }

    @Inject(method = { "glfwPollEvents", "glfwWaitEvents" }, at = @At("HEAD"), cancellable = true)
    private static void ixeris$pollOrWaitEvents(CallbackInfo ci) {
        if (Ixeris.isOnMainThread()) {
            return;
        }
        ci.cancel();
        MainThreadDispatcher.requestPollEvents();
        if (!Ixeris.inEarlyDisplay && Ixeris.getConfig().shouldLogPollingCalls()) {
            Ixeris.LOGGER.warn("", new PollingException());
        }
    }

    @Inject(method = "glfwInit", at = @At("TAIL"))
    private static void ixeris$glfwInit(CallbackInfoReturnable<Boolean> cir) {
        Ixeris.glfwInitialized = true;
    }

    @Inject(method = "glfwTerminate", at = @At("TAIL"))
    private static void ixeris$glfwTerminate(CallbackInfo ci) {
        Ixeris.glfwInitialized = false;
    }

    @Inject(method = "glfwDestroyWindow", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwDestroyWindow(long window, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();

            // Release the context if it is on the current thread
            if (window == GLFW.glfwGetCurrentContext()) {
                GLFW.glfwMakeContextCurrent(0L);

                // Make the context of the window current on the main thread before destruction, fixing crash on specific drivers
                // No need to detach the context afterward because glfwDestroyWindow does that: https://www.glfw.org/docs/latest/group__window.html#gacdf43e51376051d2c091662e9fe3d7b2
                MainThreadDispatcher.run(() -> GLFW.glfwMakeContextCurrent(window));
            }

            MainThreadDispatcher.run(() -> GLFW.glfwDestroyWindow(window));

            return;
        }

        GlfwCacheManager.destroyWindowCache(window);
    }

    @Inject(method = "glfwSetInputMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetInputMode(window, mode, value));
            return;
        }
        if (Ixeris.getConfig().isBufferedRawMouse()) {
            if (mode == GLFW.GLFW_CURSOR) {
                if (value == GLFW.GLFW_CURSOR_DISABLED) {
                    Ixeris.input().grab(window);
                }
                else {
                    Ixeris.input().release(window);
                }
            }
            if (mode == GLFW.GLFW_RAW_MOUSE_MOTION && Ixeris.input().shouldHijackSettingRawInput()) {
                Ixeris.input().setRawInput(value == GLFW.GLFW_TRUE);
                ci.cancel();
            }
        }
    }

    @Inject(method = "glfwSetCursorPos", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwSetCursorPos(long window, double xpos, double ypos, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.run(() -> GLFW.glfwSetCursorPos(window, xpos, ypos));
            return;
        }

        var accessor = Ixeris.accessor;
        if (window == accessor.getMinecraftWindow()) {
            if (Ixeris.getConfig().isBufferedRawMouse()) {
                Ixeris.input().setCursorPos(xpos, ypos);
            }
            if (!Ixeris.getConfig().isFullyBlockingMode()) {
                // Signal to the render thread to ignore the cursor pos callback that this `glfwSetCursorPos` call is to produce
                RenderThreadDispatcher.runLater(accessor::setIgnoreFirstMouseMove);
            }
        }
    }
}
