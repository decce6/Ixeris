package me.decce.ixeris.core;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.google.gson.Gson;
import me.decce.ixeris.core.util.BooleanHolder;
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
    private static final boolean BUFFERED_RAW_INPUT_SUPPORTED = PlatformHelper.isWindows() && PlatformHelper.isX64();
    @Comment("Specifies whether to enable the mod on Windows")
    private boolean enabledOnWindows = true;
    @Comment("Specifies whether to enable the mod on macOS")
    private boolean enabledOnMacOS = false;
    @Comment("Specifies whether to enable the mod on Linux")
    private boolean enabledOnLinux = true;
    @Comment("Specifies whether to enable the mod on other platforms")
    private boolean enabledOnOtherPlatforms = true;
    private transient BooleanHolder enabledOnCurrentPlatform;
    @Comment("Enable to use some experimental GLFW state cache, which may improve performance with some mods")
    private boolean aggressiveCaching;
    @Comment("Enable to use a more flexible threading model, which improves performance while obeying threading requirements of the underlying operating system.")
    private boolean flexibleThreading = true;
    @Comment("Enable to disallow delaying of any GLFW call. Might reduce performance considerably.")
    private boolean fullyBlockingMode; // Enable to block the render thread for any GLFW function that needs to be called on the main thread
    @Comment("Specifies the priority of the event polling thread. Valid values are 0~10, where 0 = auto decide.")
    private int eventPollingThreadPriority; // Range: [0, 10], where 0 = auto
    @Comment("Enables logging of GLFW event polling calls and stacktrace. Debug Only.")
    private boolean logPollingCalls;
    @Comment("Enables logging of blocking calls and stacktrace. Debug Only.")
    private boolean logBlockingCalls;
    @Comment("Enables logging of cache issues. Debug Only.")
    private boolean logCacheIssues;
    @Key("bufferedRawInput.mouse")
    @Comment("Enable to use buffered raw input for the mouse when supported, which can greatly improve event polling performance and reduce CPU usage.")
    private boolean bufferedRawMouse = true;
    @Key("bufferedRawInput.keyboard")
    @Comment("Enable to use buffered raw input for the keyboard when supported, which can offer a small performance boost to event polling performance.")
    private boolean bufferedRawKeyboard = false;
    @Key("bufferedRawInput.minRawInputBufferSize")
    @Comment("Specifies the initial raw input buffer size.")
    private int minRawInputBufferSize = 32;
    @Key("bufferedRawInput.maxRawInputBufferSize")
    @Comment("Specifies the maximum raw input buffer size.")
    private int maxRawInputBufferSize = 1024;
    @Key("bufferedRawInput.messageOptimizationStrategy")
    @Comment("""
            Specifies the optimization strategy for legacy messages. Valid values:
            DEFAULT: uses the default strategy. Currently, this translates to NOLEGACY.
            UNOPTIMIZED: uses a basic event loop.
            THROTTLED: similar to UNOPTIMIZED, but with a limit on the number of messages read during each event polling to improve performance.
            NOLEGACY: disables legacy messages completely. Best performance.
            If you experience compatibility issues with external programs, try setting this to THROTTLED.""")
    private MessageOptimizationStrategy messageOptimizationStrategy = MessageOptimizationStrategy.DEFAULT;

    public enum MessageOptimizationStrategy {
        DEFAULT,
        UNOPTIMIZED,
        THROTTLED,
        NOLEGACY
    }

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
                enabledOnCurrentPlatform = BooleanHolder.of(false);
            }
            else {
                var platform = Platform.get();
                enabledOnCurrentPlatform = BooleanHolder.of( switch (platform) {
                    case LINUX -> enabledOnLinux;
                    case MACOSX -> enabledOnMacOS;
                    case WINDOWS -> enabledOnWindows;
                    default -> enabledOnOtherPlatforms;
                } );
            }
        }
        return enabledOnCurrentPlatform.value();
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

    public boolean shouldLogPollingCalls() {
        return logPollingCalls;
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
            return Runtime.getRuntime().availableProcessors() >= 4 ? Thread.MAX_PRIORITY : Thread.MAX_PRIORITY - 2;
        }
    }

    public long getMainThreadSleepTime() {
        return 4L;
    }

    public boolean isBufferedRawMouse() {
        return bufferedRawMouse && BUFFERED_RAW_INPUT_SUPPORTED;
    }

    public boolean isBufferedRawKeyboard() {
        return bufferedRawKeyboard && BUFFERED_RAW_INPUT_SUPPORTED;
    }

    public int getMinRawInputBufferSize() {
        return minRawInputBufferSize;
    }

    public int getMaxRawInputBufferSize() {
        return maxRawInputBufferSize;
    }

    public MessageOptimizationStrategy getMessageOptimizationStrategy() {
        if (messageOptimizationStrategy == MessageOptimizationStrategy.DEFAULT) {
            return MessageOptimizationStrategy.NOLEGACY;
        }
        return messageOptimizationStrategy;
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
                String key = field.getName();
                if (field.isAnnotationPresent(Key.class)) {
                    key = field.getAnnotation(Key.class).value();
                }
                if (field.getType().isEnum()) {
                    config.set(key, ((Enum<?>) field.get(this)).name());
                }
                else {
                    config.set(key, field.get(this));
                }
                if (field.isAnnotationPresent(Comment.class)) {
                    config.setComment(key, field.getAnnotation(Comment.class).value());
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
                String key = field.getName();
                if (field.isAnnotationPresent(Key.class)) {
                    key = field.getAnnotation(Key.class).value();
                }
                if (night.contains(key)) {
                    if (field.getType().isEnum()) {
                        try {
                            Enum<?> enumValue = Enum.valueOf(field.getType().asSubclass(Enum.class), night.get(key));
                            field.set(config, enumValue);
                        } catch (IllegalArgumentException e) {
                            Ixeris.LOGGER.error("Invalid value {} for enum {}, using default value", night.get(key), field.getType().getName());
                        }
                    }
                    else {
                        field.set(config, night.get(key));
                    }
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

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Key {
        String value();
    }
}
