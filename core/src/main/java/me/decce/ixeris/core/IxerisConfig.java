package me.decce.ixeris.core;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.google.gson.Gson;
import me.decce.ixeris.core.util.PlatformHelper;
import org.lwjgl.system.Platform;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal", "unused"})
public class IxerisConfig {
    private static final Path CONFIG_PATH;
    private static final Path FILE_OLD;
    private static final Path FILE;
    @Comment("Specifies whether to enable the mod on Windows")
    private boolean enabledOnWindows = true;
    @Comment("Specifies whether to enable the mod on macOS")
    private boolean enabledOnMacOS = false;
    @Comment("Specifies whether to enable the mod on Linux")
    private boolean enabledOnLinux = true;
    @Comment("Specifies whether to enable the mod on other platforms")
    private boolean enabledOnOtherPlatforms = true;
    private transient Boolean enabledOnCurrentPlatform;
    @Comment("Enable to use some experimental GLFW state cache, which may improve performance with some mods")
    private boolean aggressiveCaching;
    @Comment("Enable to use a more flexible threading model, which improves performance while obeying threading requirements of the underlying operating system.")
    private boolean flexibleThreading = true;
    @Comment("Enable to disallow delaying of any GLFW call. Might reduce performance considerably.")
    private boolean fullyBlockingMode; // Enable to block the render thread for any GLFW function that needs to be called on the main thread
    @Comment("Specifies the priority of the event polling thread. Valid values are 0~10, where 0 = auto decide.")
    private int eventPollingThreadPriority; // Range: [0, 10], where 0 = auto
    @Comment("Enables logging of blocking calls and stacktrace. Debug Only.")
    private boolean logBlockingCalls;
    @Comment("Enables logging of cache issues. Debug Only.")
    private boolean logCacheIssues;
    @Comment("Enable to use buffered raw input when supported, which can greatly improve event polling performance.")
    private boolean bufferedRawInput = false;
    @Comment("Specifies the initial raw input buffer size.")
    private int minRawInputBufferSize = 32;
    @Comment("Specifies the maximum raw input buffer size.")
    private int maxRawInputBufferSize = 1024;

    static {
        CONFIG_PATH = Paths.get("config");
        FILE_OLD = CONFIG_PATH.resolve("ixeris.json");
        FILE = CONFIG_PATH.resolve("ixeris.toml");
        if (!Files.exists(CONFIG_PATH)) {
            try {
                Files.createDirectory(CONFIG_PATH);
            } catch (IOException ignored) {
            }
        }
    }

    private IxerisConfig() {

    }

    public boolean isEnabled() {
        if (enabledOnCurrentPlatform == null) {
            if (PlatformHelper.isAndroid()) {
                enabledOnCurrentPlatform = false;
            }
            else {
                var platform = Platform.get();
                enabledOnCurrentPlatform = switch (platform) {
                    case LINUX -> enabledOnLinux;
                    case MACOSX -> enabledOnMacOS;
                    case WINDOWS -> enabledOnWindows;
                    default -> enabledOnOtherPlatforms;
                };
            }
        }
        return enabledOnCurrentPlatform;
    }

    public boolean isAggressiveCaching() {
        return aggressiveCaching;
    }

    public boolean useFlexibleThreading() {
        return flexibleThreading;
    }

    public boolean isFullyBlockingMode() {
        return fullyBlockingMode;
    }

    public boolean shouldLogBlockingCalls() {
        return logBlockingCalls;
    }

    public boolean shouldLogCacheIssues() {
        return logCacheIssues;
    }

    public int getEventPollingThreadPriority() {
        if (eventPollingThreadPriority >= Thread.MIN_PRIORITY && eventPollingThreadPriority <= Thread.MAX_PRIORITY) {
            return eventPollingThreadPriority;
        }
        else {
            return Thread.MAX_PRIORITY - 2;
        }
    }

    public long getMainThreadSleepTime() {
        return 4L;
    }

    public boolean isBufferedRawInput() {
        return bufferedRawInput && PlatformHelper.isWindows();
    }

    public int getMinRawInputBufferSize() {
        return minRawInputBufferSize;
    }

    public int getMaxRawInputBufferSize() {
        return maxRawInputBufferSize;
    }

    private static CommentedFileConfig makeNightConfig() {
        return CommentedFileConfig.builder(FILE, TomlFormat.instance())
                .preserveInsertionOrder()
                .sync()
                .build();
    }

    public void save() {
        try (var config = toNightConfig()) {
            config.save();
        } catch (Exception e) {
            Ixeris.LOGGER.error("Failed to save configuration!", e);
        }
    }

    public static IxerisConfig load() {
        maybeImportOld();
        if (FILE.toFile().exists()) {
            try {
                return fromNightConfig();
            } catch (Exception e) {
                Ixeris.LOGGER.error("Failed to read configuration!", e);
            }
        }
        return new IxerisConfig();
    }

    private CommentedFileConfig toNightConfig() {
        var config = makeNightConfig();
        try {
            for (Field field : IxerisConfig.class.getDeclaredFields()) {
                var modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers) || Modifier.isFinal(modifiers)) {
                    continue;
                }
                config.set(field.getName(), field.get(this));
                if (field.isAnnotationPresent(Comment.class)) {
                    config.setComment(field.getName(), field.getAnnotation(Comment.class).value());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return config;
    }

    private static IxerisConfig fromNightConfig() {
        var config = new IxerisConfig();
        try (var night = makeNightConfig()) {
            night.load();
            for (Field field : IxerisConfig.class.getDeclaredFields()) {
                var modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers) || Modifier.isFinal(modifiers)) {
                    continue;
                }
                if (night.contains(field.getName())) {
                    field.set(config, night.get(field.getName()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return config;
    }

    private static void maybeImportOld() {
        if (FILE_OLD.toFile().exists()) {
            if (FILE.toFile().exists()) {
                FILE_OLD.toFile().delete();
            }
            else {
                try {
                    var old = new Gson().fromJson(Files.readString(FILE_OLD), IxerisConfig.class);
                    var night = old.toNightConfig();
                    night.set("enabledOnMacOS", false); //TODO: only for now
                    night.save();
                    night.close();
                    FILE_OLD.toFile().delete();
                    Ixeris.LOGGER.info("Successfully imported configuration from ixeris.json.");
                } catch (Exception e) {
                    Ixeris.LOGGER.error("Failed to import old configuration!", e);
                }
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Comment {
        String value();
    }
}
