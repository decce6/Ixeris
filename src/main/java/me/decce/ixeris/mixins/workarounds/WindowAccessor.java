package me.decce.ixeris.mixins.workarounds;

import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Window.class)
public interface WindowAccessor {
    //? if >=1.21.4 {
    @Accessor
    void setMinimized(boolean value);
    //?}
}
