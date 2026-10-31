package me.decce.ixeris.core.sdl;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.sdl.state_caching.SdlStateCache;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import me.decce.ixeris.core.util.MemoryHelper;
import org.lwjgl.sdl.SDLEvents;
import org.lwjgl.sdl.SDL_Event;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SdlEventQueue {
    // TODO: use a ring buffer
    private final ConcurrentLinkedQueue<SDL_Event> events = new ConcurrentLinkedQueue<>();
    private final ArrayList<SDL_Event> polled = new ArrayList<>();

    // Called by the main thread
    public boolean pollEvent() {
        // TODO: optimize mem alloc
        var event = SDL_Event.malloc();
        var ret = SDLEvents.SDL_PollEvent(event);
        if (!ret) {
            event.free();
            return false;
        }
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
            case SDLEvents.SDL_EVENT_DROP_FILE, SDLEvents.SDL_EVENT_DROP_TEXT -> {
                var originalData = event.drop().data();
                if (originalData != null) {
                    event.drop().data(MemoryHelper.copyString(originalData));
                }
                var originalSource = event.drop().source();
                if (originalSource != null) {
                    event.drop().source(MemoryHelper.copyString(originalSource));
                }
            }
            case SDLEvents.SDL_EVENT_WINDOW_RESIZED, SDLEvents.SDL_EVENT_WINDOW_RESTORED, SDLEvents.SDL_EVENT_WINDOW_MAXIMIZED -> {
                Ixeris.forceReconfigureSwapchain = true;
            }
        }

        polled.add(event);
        return true;
    }

    public void flush() {
        SdlStateCache.refreshWindowFlags();
        events.addAll(polled);
        polled.clear();
    }

    public boolean readEvent(long event) {
        var ret = events.poll();
        if (ret == null) {
            return false;
        }
        MemoryUtil.memCopy(ret.address(), event, ret.sizeof());
        switch (ret.type()) {
            case SDLEvents.SDL_EVENT_TEXT_EDITING -> freeLaterIfNonNull(ret.edit().text());
            case SDLEvents.SDL_EVENT_TEXT_INPUT -> freeLaterIfNonNull(ret.text().text());
            case SDLEvents.SDL_EVENT_DROP_FILE, SDLEvents.SDL_EVENT_DROP_TEXT -> {
                freeLaterIfNonNull(ret.drop().data());
                freeLaterIfNonNull(ret.drop().source());
            }
        }
        ret.free();
        return true;
    }

    private static void freeLaterIfNonNull(ByteBuffer buffer) {
        if (buffer != null) {
            RenderThreadDispatcher.runLater(() -> MemoryUtil.memFree(buffer));
        }
    }
}
