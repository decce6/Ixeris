// Note: this line is not recognized in the core package, but it is when translated and put into the main src directory
//? if >=1.19 {
package me.decce.ixeris.core.mixins.glfw_threading_330;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWAllocator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// This mixins houses functions that are added GLFW 3.3.4 (LWJGL 3.3.0) and do not exist in older versions of Minecraft
@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(method = "glfwInitAllocator", at = @At("HEAD"), cancellable = true)
    private static void ixeris$glfwInitAllocator(GLFWAllocator allocator, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            MainThreadDispatcher.runNow(() -> GLFW.glfwInitAllocator(allocator));
        }
    }
}
//? }