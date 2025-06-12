package me.decce.ixeris.fabric;

import net.fabricmc.api.ClientModInitializer;

import me.decce.ixeris.Ixeris;

public final class IxerisModFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Ixeris.init();
    }
}
