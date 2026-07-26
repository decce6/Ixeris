package me.decce.ixeris.mixins;

import org.spongepowered.asm.mixin.Mixin;
//? >=26.3 {
/*
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.SDLEventHandler;
import org.lwjgl.sdl.SDL_Event;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SDLEventHandler.class)
public class SDLEventHandlerMixin {
    @Inject(method = "pollEvents", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/SDLEventHandler;handleTextEditingEvent(Lorg/lwjgl/sdl/SDL_Event;)V", shift = At.Shift.AFTER))
    private void ixeris$freeCopiedStrings$Edit(CallbackInfo ci, @Local(name = "event") SDL_Event event) {
        MemoryUtil.memFree(event.edit().text());
    }

    @Inject(method = "pollEvents", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/SDLEventHandler;handleTextInputEvent(Lorg/lwjgl/sdl/SDL_Event;)V", shift = At.Shift.AFTER))
    private void ixeris$freeCopiedStrings$Input(CallbackInfo ci, @Local(name = "event") SDL_Event event) {
        MemoryUtil.memFree(event.text().text());
    }
}
*///? } else {
@Mixin(targets = {})
public class SDLEventHandlerMixin {}
//? }