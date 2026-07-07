package me.decce.ixeris.workarounds;

import me.decce.ixeris.IxerisMod;
import me.decce.ixeris.VersionCompatUtils;
import me.decce.ixeris.core.glfw.callback_dispatcher.FramebufferSizeCallbackDispatcher;

// Resolves https://github.com/decce6/Ixeris/issues/117 by forcing a surface reconfiguration at framebuffer resize
// Without this, the window surface may be stuck at an "invalid" state and nothing new gets rendered because Minecraft does not acquire next texture at this state.
public class VulkanSwapchainValidityWorkaround {
    public static void init() {
        //? >=26.2 {
        /*long window = VersionCompatUtils.getMinecraftWindow();
        FramebufferSizeCallbackDispatcher.get(window).registerMainThreadCallback(VulkanSwapchainValidityWorkaround::onFramebufferSizeCallback);
        *///? }
    }

    private static void onFramebufferSizeCallback(long window, int width, int height) {
        IxerisMod.forceReconfigureSwapchain = true;
    }
}
