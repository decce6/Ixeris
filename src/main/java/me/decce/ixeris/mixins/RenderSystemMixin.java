package me.decce.ixeris.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mojang.blaze3d.systems.RenderSystem;

@Mixin(value = RenderSystem.class, remap = false)
public class RenderSystemMixin {
    /**
     * @author decce6
     * @reason disable vanilla glfwPollEvents
     */
    @Overwrite
    private static void pollEvents() {}
}
