package me.decce.ixeris.mixins;

import com.mojang.blaze3d.platform.Window;
import me.decce.ixeris.core.glfw.callback_dispatcher.CallbackDispatchers;
import me.decce.ixeris.core.glfw.callback_dispatcher.CommonCallbacks;
import me.decce.ixeris.core.glfw.callback_dispatcher._334.CallbackDispatchers_334;
import me.decce.ixeris.core.glfw.callback_dispatcher._334.CommonCallbacks_334;
import me.decce.ixeris.core.util.LWJGLVersionHelper;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Window.class, remap = false)
public class WindowMixin {
    @Shadow
    @Final
    //? if >=1.21.9 {
    private long handle;
    //? } else {
    //private long window;
    //? }

    // Re-create our callbacks after Minecraft frees them
    // This is required on 26.2+ where Minecraft does free callbacks when the preferred GPU backend fails
    @Inject(method = "close", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/Callbacks;glfwFreeCallbacks(J)V", shift = At.Shift.AFTER))
    private void ixeris$recreateCallbacks(CallbackInfo ci) {
        // Unset callbacks that are not unset by glfwFreeCallbacks, so that our CallbackDispatchers validation works correctly
        // TODO: this must be verified for each GLFW release
        GLFW.glfwSetErrorCallback(null);
        GLFW.glfwSetMonitorCallback(null);

        //? if >=1.21.9 {
        var handle = this.handle;
        //? } else {
        //var handle = this.window;
        //? }

        CommonCallbacks.initCallbacks();
        CallbackDispatchers.validateAll(handle);
        if (LWJGLVersionHelper.isGreaterThan334()) {
            CommonCallbacks_334.initCallbacks();
            CallbackDispatchers_334.validateAll(handle);
        }
    }
}
