package me.decce.ixeris.mixins;

import org.spongepowered.asm.mixin.Mixin;
//? >=26.2 {
/*import com.mojang.blaze3d.systems.GpuSurface;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GpuSurface.class)
public interface GpuSurfaceAccessor {
    @Accessor("hasImageAcquired")
    void ixeris$setHasImageAcquired(boolean value);
}
*///? } else {
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
//? forge
//@Mixin(targets = "")
//? !forge
@Mixin(targets = {})
public interface GpuSurfaceAccessor {}
//? }
