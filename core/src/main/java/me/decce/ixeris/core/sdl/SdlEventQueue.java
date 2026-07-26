package me.decce.ixeris.core.sdl;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.util.MemoryHelper;
import org.lwjgl.sdl.SDLEvents;
import org.lwjgl.sdl.SDL_Event;
import org.lwjgl.system.MemoryUtil;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SdlEventQueue {
    // TODO: use a ring buffer
    private final ConcurrentLinkedQueue<SDL_Event> events = new ConcurrentLinkedQueue<>();

    // Called by the main thread
    public boolean pollEvent() {
        // TODO: optimize mem alloc
        var event = SDL_Event.malloc();
        var ret = SDLEvents.SDL_PollEvent(event);
        switch (event.type()) {
            case SDLEvents.SDL_EVENT_TEXT_INPUT -> {
                var originalText = event.text().text();
                if (originalText != null) {
                    event.text().text(MemoryHelper.copyString(originalText));
                }
            }
            case SDLEvents.SDL_EVENT_TEXT_EDITING -> {
                var originalText = event.edit().text();
                if (originalText != null) {
                    event.edit().text(MemoryHelper.copyString(originalText));
                }
            }
            case SDLEvents.SDL_EVENT_WINDOW_RESIZED, SDLEvents.SDL_EVENT_WINDOW_RESTORED, SDLEvents.SDL_EVENT_WINDOW_MAXIMIZED -> {
                Ixeris.forceReconfigureSwapchain = true;
            }
        }

        if (ret) {
            events.offer(event);
        }
        return ret;
    }

    public boolean readEvent(SDL_Event event) {
        var ret = events.poll();
        if (ret == null) {
            return false;
        }
        event.set(ret);
        ret.free();
        return true;
    }

    public boolean readEvent(long event) {
        var ret = events.poll();
        if (ret == null) {
            return false;
        }
        MemoryUtil.memCopy(ret.address(), event, ret.sizeof());
        ret.free();
        return true;
    }
}
