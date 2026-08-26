package me.decce.ixeris.mixins.compat.caramelchat;

import me.decce.ixeris.compat.caramelchat.CaramelChatCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "moe.caramel.chat.wrapper.AbstractIMEWrapper", remap = false)
public abstract class CaramelChatIMEWrapperMixin {
    @Inject(method = "appendPreviewText(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
    private void ixeris$appendPreviewTextOnRenderThread(String text, CallbackInfo ci) {
        if (!CaramelChatCompat.isOnRenderThread()) {
            ci.cancel();
            CaramelChatCompat.invokeLaterOnRenderThread(this, "appendPreviewText", String.class, text);
        }
    }

    @Inject(method = "insertText(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
    private void ixeris$insertTextOnRenderThread(String text, CallbackInfo ci) {
        if (!CaramelChatCompat.isOnRenderThread()) {
            ci.cancel();
            CaramelChatCompat.invokeLaterOnRenderThread(this, "insertText", String.class, text);
        }
    }
}
