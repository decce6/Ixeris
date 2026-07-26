package me.decce.ixeris.core;

public interface EventHandler {
    boolean canPollEvents();
    void pollEvents();
}
