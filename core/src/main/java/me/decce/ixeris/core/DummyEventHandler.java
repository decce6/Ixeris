package me.decce.ixeris.core;

public class DummyEventHandler implements EventHandler {
    public static final DummyEventHandler INSTANCE = new DummyEventHandler();

    @Override
    public boolean canPollEvents() {
        return false;
    }

    @Override
    public void pollEvents() {

    }
}
