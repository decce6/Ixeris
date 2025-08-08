package me.decce.ixeris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import net.fabricmc.loader.api.FabricLoader;
import org.lwjgl.system.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class IxerisConfig {
    private static final Path CONFIG_PATH;
    private static final Path FILE;
    private boolean enabledOnWindows = true;
    private boolean enabledOnMacOS = true;
    private boolean enabledOnLinux = true;
    private transient Boolean enabledOnCurrentPlatform;
    private boolean fullyBlockingMode; // Enable to block the render thread for any GLFW function that needs to be called on the main thread
    private boolean logBlockingCalls;
    @SerializedName("greedyEventPolling_v2") private boolean greedyEventPolling = true; // When disabled, allows event polling thread to sleep longer
    private int eventPollingThreadPriority; // Range: [0, 10], where 0 = auto

    static {
        //? if fabric {
        CONFIG_PATH = FabricLoader.getInstance().getConfigDir();
        //? } else {
        // CONFIG_PATH = Paths.get("config");
        //? }
        FILE = CONFIG_PATH.resolve("ixeris.json");
    }

    private IxerisConfig() {

    }

    public boolean isEnabled() {
        if (enabledOnCurrentPlatform == null) {
            var platform = Platform.get();
            enabledOnCurrentPlatform = switch (platform) {
                case LINUX -> enabledOnLinux;
                case MACOSX -> enabledOnMacOS;
                case WINDOWS -> enabledOnWindows;
            };
        }
        return enabledOnCurrentPlatform;
    }

    public boolean isFullyBlockingMode() {
        return fullyBlockingMode;
    }

    public boolean shouldLogBlockingCalls() {
        return logBlockingCalls;
    }

    public int getEventPollingThreadPriority() {
        if (eventPollingThreadPriority >= Thread.MIN_PRIORITY && eventPollingThreadPriority <= Thread.MAX_PRIORITY) {
            return eventPollingThreadPriority;
        }
        else {
            return Thread.MAX_PRIORITY - 2;
        }
    }

    public boolean isGreedyEventPolling() {
        return greedyEventPolling;
    }

    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);
        try {
            if (!Files.exists(CONFIG_PATH)) {
                Files.createDirectory(CONFIG_PATH); // The config directory might have not been created if on a new instance
            }
            Files.writeString(FILE, json, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            Ixeris.LOGGER.error("Failed to save configuration!", e);
        }
    }

    public static IxerisConfig load() {
        IxerisConfig config = null;
        if (FILE.toFile().exists()) {
            try {
                config = new Gson().fromJson(Files.readString(FILE), IxerisConfig.class);
            } catch (IOException e) {
                Ixeris.LOGGER.error("Failed to read configuration!", e);
            }
        }
        if (config == null) {
            config = new IxerisConfig();
        }
        return config;
    }
}
