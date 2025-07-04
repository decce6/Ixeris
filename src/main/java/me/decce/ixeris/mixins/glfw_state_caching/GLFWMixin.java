package me.decce.ixeris.mixins.glfw_state_caching;

import me.decce.ixeris.glfw.state_caching.GlfwCacheManager;
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
        GlfwCacheManager.getInputModeCache(window).set(mode, value);
    }

    @Inject(method = "glfwGetInputMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetInputMode(long window, int mode, CallbackInfoReturnable<Integer> cir) {
        if (GlfwCacheManager.useInputModeCache) {
            cir.setReturnValue(GlfwCacheManager.getInputModeCache(window).get(mode));
        }
    }

    @Inject(method = "glfwGetKey", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKey(long window, int key, CallbackInfoReturnable<Integer> cir) {
        if (GlfwCacheManager.useKeyCache) {
            cir.setReturnValue(GlfwCacheManager.getKeyCache(window).get(key));
        }
    }

    @Inject(method = "glfwGetKeyName", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwGetKeyName(int key, int scancode, CallbackInfoReturnable<String> cir) {
        if (GlfwCacheManager.useKeyNameCache) {
            cir.setReturnValue(GlfwCacheManager.getKeyNameCache().get(key, scancode));
        }
    }
}
