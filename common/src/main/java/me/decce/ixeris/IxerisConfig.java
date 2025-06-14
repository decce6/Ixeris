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
    private int renderThreadPriority; // Range: [0, 10], where 0 = auto
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

    /**
     * @return The render thread priority specified in the configuration file if that value falls within range [1, 10],
     * or an automatically selected value based on the vanilla logic (see {@link net.minecraft.client.Minecraft#run})
     */
    public int getRenderThreadPriority() {
        int priority = renderThreadPriority;
        if (priority < Thread.MIN_PRIORITY || priority > Thread.MAX_PRIORITY) {
            priority = Runtime.getRuntime().availableProcessors() > 4 ? Thread.MAX_PRIORITY : Thread.NORM_PRIORITY;
        }
        return priority;
    }

    public int getEventPollingThreadPriority() {
        return eventPollingThreadPriority;
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
