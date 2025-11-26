package me.decce.ixeris.core;

public interface IxerisMinecraftAccessor {
    long getMinecraftWindow();
    void setIgnoreFirstMouseMove();
    boolean isMouseInternallyGrabbed();
    void replayRenderThreadQueue();
}
