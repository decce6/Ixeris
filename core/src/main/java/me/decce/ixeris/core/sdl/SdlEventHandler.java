package me.decce.ixeris.core.sdl;

import me.decce.ixeris.core.EventHandler;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.sdl.SDLKeyboard;
import org.lwjgl.system.MemoryUtil;

public class SdlEventHandler implements EventHandler {
    private final SdlEventQueue queue = new SdlEventQueue();
    private volatile boolean requestedSetTextInputArea;
    private volatile long textInputArea;
    private volatile int textInputCursor;

    @Override
    public boolean canPollEvents() {
        return Ixeris.sdlInitialized;
    }

    @Override
    public void pollEvents() {
        if (Ixeris.sdlInitialized) {
            while (queue.pollEvent());
        }
    }

    public void requestSetTextInputArea(long window, long rect, int cursor) {
        if (window != Ixeris.accessor.getMinecraftWindow()) {
            MainThreadDispatcher.query(() -> SDLKeyboard.nSDL_SetTextInputArea(window, rect, cursor));
            return;
        }
        if (this.textInputArea != 0L) {
            MemoryUtil.nmemFree(this.textInputArea);
        }
        this.textInputArea = SdlMemoryHelper.copySDL_Rect(rect);
        this.textInputCursor = cursor;
        this.requestedSetTextInputArea = true;
    }

    public void updateTextInputArea() {
        MainThreadDispatcher.run(() -> {
            if (!requestedSetTextInputArea) {
                return;
            }
            requestedSetTextInputArea = false;
            var area = textInputArea;
            textInputArea = 0L;
            SDLKeyboard.nSDL_SetTextInputArea(Ixeris.accessor.getMinecraftWindow(), area, textInputCursor);
            MemoryUtil.nmemFree(area);
        });
    }

    public boolean readEvents(long event) {
        return queue.readEvent(event);
    }
}
