package me.decce.ixeris.mixins;

import me.decce.ixeris.VersionCompatUtils;
import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import net.minecraft.client.MouseHandler;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MouseHandler.class, priority = 100)
public class MouseHandlerMixin {
    @Inject(method = "isMouseGrabbed", at = @At(value= "HEAD"), cancellable = true)
    private void ixeris$isMouseGrabbed(CallbackInfoReturnable<Boolean> cir) {
        var cache = GlfwCacheManager.getWindowCache(VersionCompatUtils.getMinecraftWindow());
        cir.setReturnValue(cache.inputMode().get(GLFW.GLFW_CURSOR) == GLFW.GLFW_CURSOR_DISABLED);
    }
}