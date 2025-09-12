package me.decce.ixeris.fabric;

//? if fabric {
import me.decce.ixeris.IxerisMod;
import net.fabricmc.api.ClientModInitializer;

public class IxerisModFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IxerisMod.init();
    }
}
//?}
