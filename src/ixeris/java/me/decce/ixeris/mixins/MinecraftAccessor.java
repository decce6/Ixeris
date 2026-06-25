package me.decce.ixeris.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {
    //? >=26.2 {
    /*@Accessor("surfaceIsInvalid")
    void ixeris$setSurfaceIsInvalid(boolean value);
    @Accessor("windowSurfaceNeedsReconfiguring")
    void ixeris$setWindowSurfaceNeedsReconfiguring(boolean value);
    *///? }
}
