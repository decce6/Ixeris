package me.decce.ixeris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lwjgl.system.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class IxerisConfig {
    // TODO: find a more robust way for determining config path
    private static final Path PATH = Paths.get("config").resolve("ixeris.json");
    private boolean enabledOnWindows = true;
    private boolean enabledOnMacOS = true; // TODO: untested
    private boolean enabledOnLinux = true;
    private transient Boolean enabledOnCurrentPlatform;
    private boolean fullyBlockingMode; // Enable to block the render thread even for functions that do not return value
    private boolean logBlockingCalls;
    private boolean greedyEventPolling; // When disabled, allows event polling thread to sleep
    private int eventPollingThreadPriority; // Range: [0, 10], where 0 = do not modify

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
            return Runtime.getRuntime().availableProcessors() > 4 ? Thread.MAX_PRIORITY : Thread.NORM_PRIORITY;
        }
    }

    public boolean isGreedyEventPolling() {
        return greedyEventPolling;
    }

    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);
        try {
            Files.writeString(PATH, json, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            Ixeris.LOGGER.error("Failed to save configuration!", e);
        }
    }

    public static IxerisConfig load() {
        IxerisConfig config = null;
        if (PATH.toFile().exists()) {
            try {
                config = new Gson().fromJson(Files.readString(PATH), IxerisConfig.class);
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
