package me.decce.ixeris.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = RenderSystem.class, remap = false)
public interface RenderSystemAccessor {
    @Accessor
    static void setRenderThread(Thread thread) {
        throw new AssertionError();
    }
}
