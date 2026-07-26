package me.decce.ixeris.core.sdl;

import me.decce.ixeris.core.EventHandler;
import me.decce.ixeris.core.Ixeris;

public class SdlEventHandler implements EventHandler {
    private final SdlEventQueue queue = new SdlEventQueue();

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

    public boolean readEvents(long event) {
        return queue.readEvent(event);
    }
}
