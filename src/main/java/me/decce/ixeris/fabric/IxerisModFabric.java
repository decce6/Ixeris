package me.decce.ixeris.fabric;

import me.decce.ixeris.Ixeris;
import net.fabricmc.api.ClientModInitializer;

//? if fabric {
public class IxerisModFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Ixeris.init();
    }
}
//? }
