package me.decce.ixeris.mixins.glfw_state_caching;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InputConstants.class)
public class InputConstantsMixin {
    @Unique
    private static GLFWKeyCallback ixeris$originalKeyCallback;

    // Minecraft's key callback does not invoke previous callbacks. This mixin makes sure our key callback is run.
    // Intentionally uses @Redirect, as this cannot be safely chained
    @Redirect(method = "setupKeyboardCallbacks", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetKeyCallback(JLorg/lwjgl/glfw/GLFWKeyCallbackI;)Lorg/lwjgl/glfw/GLFWKeyCallback;", remap = false))
    private static GLFWKeyCallback ixeris$setupKeyboardCallbacks(long window, GLFWKeyCallbackI cbfun) {
        ixeris$originalKeyCallback = GLFW.glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
            cbfun.invoke(win, key, scancode, action, mods);
            if (ixeris$originalKeyCallback != null) {
                ixeris$originalKeyCallback.invoke(win, key, scancode, action, mods);
            }
        });
        return ixeris$originalKeyCallback;
    }
}
