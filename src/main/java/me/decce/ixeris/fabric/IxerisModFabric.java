package me.decce.ixeris.fabric;

//? if fabric {
import me.decce.ixeris.Ixeris;
import net.fabricmc.api.ClientModInitializer;

public class IxerisModFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Ixeris.init();
    }
}
//? }
