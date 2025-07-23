package me.decce.ixeris.mixins.glfw_state_caching;

import com.mojang.blaze3d.platform.InputConstants;
import me.decce.ixeris.glfw.state_caching.GlfwCacheManager;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InputConstants.class)
public class InputConstantsMixin {
    @Inject(method = "setupKeyboardCallbacks", at = @At(value = "RETURN"))
    private static void ixeris$setupKeyboardCallbacks(long window, GLFWKeyCallbackI gLFWKeyCallbackI, GLFWCharModsCallbackI gLFWCharModsCallbackI, CallbackInfo ci) {
        GlfwCacheManager.getWindowCache(window).initializeKeyCache();
    }

    @Inject(method = "setupMouseCallbacks", at = @At(value = "RETURN"))
    private static void ixeris$setupMouseCallbacks(long window, GLFWCursorPosCallbackI gLFWCursorPosCallbackI, GLFWMouseButtonCallbackI gLFWMouseButtonCallbackI, GLFWScrollCallbackI gLFWScrollCallbackI, GLFWDropCallbackI gLFWDropCallbackI, CallbackInfo ci) {
        GlfwCacheManager.getWindowCache(window).initializeMouseButtonCache();
    }
}
