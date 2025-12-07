package me.decce.ixeris.core;

public interface IxerisMinecraftAccessor {
    long getMinecraftWindow();
    void setIgnoreFirstMouseMove();
    boolean isMouseInternallyGrabbed();
    void replayRenderThreadQueue();
    default boolean isMinecraftWindowCreated() {
        return getMinecraftWindow() != 0L;
    }
}
