package me.decce.ixeris.mixins.glfw_state_caching;

import me.decce.ixeris.Ixeris;
import me.decce.ixeris.glfw.state_caching.GlfwCacheManager;
import me.decce.ixeris.glfw.state_caching.GlfwGlobalCacheManager;
import me.decce.ixeris.glfw.state_caching.GlfwWindowCacheManager;
import me.decce.ixeris.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwSetInputMode", at = @At("TAIL"))
    private static void ixeris$glfwSetInputMode(long window, int mode, int value, CallbackInfo ci) {
        GlfwCacheManager.getWindowCache(window).inputMode().set(mode, value);
    }

    @Inject(method = "glfwGetInputMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetInputMode(long window, int mode, CallbackInfoReturnable<Integer> cir) {
        if (GlfwWindowCacheManager.useInputModeCache) {
            cir.setReturnValue(GlfwCacheManager.getWindowCache(window).inputMode().get(mode));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetInputMode(window, mode)));
        }
    }

    @Inject(method = "glfwGetKey", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKey(long window, int key, CallbackInfoReturnable<Integer> cir) {
        if (GlfwWindowCacheManager.useKeyCache) {
            var ret = GlfwCacheManager.getWindowCache(window).keys().get(key);
            if (ret == GLFW.GLFW_REPEAT) {
                ret = GLFW.GLFW_PRESS;
            }
            cir.setReturnValue(ret);
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetKey(window, key)));
        }
    }

    @Inject(method = "glfwGetKeyName", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKeyName(int key, int scancode, CallbackInfoReturnable<String> cir) {
        if (GlfwGlobalCacheManager.useKeyNameCache) {
            cir.setReturnValue(GlfwCacheManager.getGlobalCache().keyNames().get(key, scancode));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetKeyName(key, scancode)));
        }
    }

    @Inject(method = "glfwGetMouseButton", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetMouseButton(long window, int button, CallbackInfoReturnable<Integer> cir) {
        if (GlfwWindowCacheManager.useMouseButtonCache) {
            cir.setReturnValue(GlfwCacheManager.getWindowCache(window).mouseButtons().get(button));
        }
        else if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> GLFW.glfwGetMouseButton(window, button)));
        }
    }
}
