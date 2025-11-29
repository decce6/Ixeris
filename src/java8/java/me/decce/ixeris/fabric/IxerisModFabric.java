package me.decce.ixeris.fabric;

//? if fabric {
import me.decce.ixeris.IxerisMod;
import me.decce.ixeris.JavaHelper;
import net.fabricmc.api.ClientModInitializer;

public class IxerisModFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        if (JavaHelper.JAVA_SUPPORTED) {
            IxerisMod.init();
        }
    }
}
//?}
