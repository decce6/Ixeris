package me.decce.ixeris.mixins.glfw_state_caching;

import com.mojang.blaze3d.platform.ScreenManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMonitorCallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Minecraft's callbacks do not invoke previous callbacks. This mixin makes sure our callbacks are run.
// Intentionally uses @Redirect, as this cannot be safely chained
@Mixin(ScreenManager.class)
public class ScreenManagerMixin {
    @Unique
    private GLFWMonitorCallback ixeris$originalMonitorCallback;

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetMonitorCallback(Lorg/lwjgl/glfw/GLFWMonitorCallbackI;)Lorg/lwjgl/glfw/GLFWMonitorCallback;"))
    private GLFWMonitorCallback ixeris$ctor(GLFWMonitorCallbackI cbfun) {
        ixeris$originalMonitorCallback = GLFW.glfwSetMonitorCallback((monitor, event) -> {
            cbfun.invoke(monitor, event);
            if (ixeris$originalMonitorCallback != null) {
                ixeris$originalMonitorCallback.invoke(monitor, event);
            }
        });
        return ixeris$originalMonitorCallback;
    }
}
