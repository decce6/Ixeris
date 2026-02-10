package me.decce.ixeris.macos.redraw;

import me.decce.ixeris.VersionCompatUtils;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowRefreshCallbackDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import me.decce.ixeris.core.util.PlatformHelper;

public class RedrawHandler {
    public static void init() {
        if (PlatformHelper.isMacOs()) {
            WindowRefreshCallbackDispatcher.get(VersionCompatUtils.getMinecraftWindow()).registerMainThreadCallback(RedrawHandler::onRefresh);
        }
    }

    private static void onRefresh(long window) {
        if (window == VersionCompatUtils.getMinecraftWindow()) {
            RenderThreadDispatcher.waitForBufferSwapping();
        }
    }
}
