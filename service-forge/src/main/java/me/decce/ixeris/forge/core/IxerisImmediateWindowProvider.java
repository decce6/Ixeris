package me.decce.ixeris.forge.core;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import me.decce.ixeris.forge.core.transformers.callback_dispatcher.GLFWTransformer;
import net.lenni0451.classtransform.TransformerManager;
import net.lenni0451.classtransform.utils.tree.BasicClassProvider;
import net.minecraftforge.fml.loading.ImmediateWindowProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static me.decce.ixeris.forge.core.ReflectionHelper.unreflect;

/*
* Forge loads ImmediateWindowProvider's with ServiceLoader, meaning our static constructor *will* be called, even if
* it is not the chosen provider in fml.toml.
* TODO: much of the code in this class is just the same as in the service-neoforge project. Find a way to share code.
* */
public class IxerisImmediateWindowProvider implements ImmediateWindowProvider {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final MethodHandle DEFINE_CLASS = unreflect(() -> ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class));
    public static final MethodHandle RESOLVE_CLASS = unreflect(() -> ClassLoader.class.getDeclaredMethod("resolveClass", Class.class));
    public static final MethodHandle IMPL_ADD_READS_ALL_UNNAMED = unreflect(() -> Module.class.getDeclaredMethod("implAddReadsAllUnnamed"));
    public static final MethodHandle IMPL_ADD_READS = unreflect(() -> Module.class.getDeclaredMethod("implAddReads", Module.class));

    // Forge uses ServiceLoader to load the ImmediateWindowProvider's. This means our static constructor can run even
    // when we are not the selected immediate window provider in fml.toml.
    static {
        var cl = Thread.currentThread().getContextClassLoader();

        // Important: do not use the LOGGER from Ixeris at here - Ixeris must be loaded on the MC-BOOTSTRAP classloader
        LOGGER.debug("IxerisImmediateWindowProvider - Bootstrapping on classloader {} of type {}", cl.getName(), cl.getClass().getName());

        loadCoreClasses();

        LOGGER.info("Attempting to transform org.lwjgl.glfw.GLFW");

        expandGlfwModuleReads();

        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        var module = layer.configuration().findModule("org.lwjgl.glfw").orElseThrow();
        var ref = module.reference();
        try (var reader = ref.open()) {
            try (var stream = reader.open("org/lwjgl/glfw/GLFW.class").orElseThrow()) {
                var bytes = stream.readAllBytes();
                var manager = getTransformerManager();

                long millis = System.currentTimeMillis();
                var transformedBytes = manager.transform("org.lwjgl.glfw.GLFW", bytes);
                long elapsed = System.currentTimeMillis() - millis;

                defineClass(cl, "org.lwjgl.glfw.GLFW", transformedBytes);
                LOGGER.info("Successfully transformed class org.lwjgl.glfw.GLFW in {}ms", elapsed);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static List<Path> classesToLoad;

    private static final Class<?>[] TRANSFORMERS = new Class[] {
            me.decce.ixeris.forge.core.transformers.GLFWTransformer.class,
            GLFWTransformer.class,
            me.decce.ixeris.forge.core.transformers.glfw_state_caching.GLFWTransformer.class,
            me.decce.ixeris.forge.core.transformers.glfw_threading.GLFWTransformer.class,
    };

    // Dummy method to trigger static constructor
    public static void init() {
    }

    private static Module findBootModule(String name) {
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        return layer.findModule(name).orElseThrow();
    }

    private static void expandGlfwModuleReads() {
        try {
            var glfwModule = findBootModule("org.lwjgl.glfw");
            IMPL_ADD_READS.invoke(glfwModule, findBootModule("org.apache.logging.log4j")); // We use logger in the injected code
            IMPL_ADD_READS_ALL_UNNAMED.invoke(glfwModule); // For access to classes in our mod
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadCoreClasses() {
        LOGGER.info("Loading Ixeris coremod");
        var resource = IxerisImmediateWindowProvider.class.getResource("/me/decce/ixeris/core");
        try (var stream = Files.walk(Path.of(Objects.requireNonNull(resource).toURI()))) {
            classesToLoad = new LinkedList<>(stream.filter(p -> !Files.isDirectory(p) && p.toString().endsWith(".class")).toList());
            while (!classesToLoad.isEmpty()) {
                loadClass(classesToLoad.remove(0));
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static void loadClass(Path path) {
        try {
            var original = path.toString();
            var name = toClassName(original);defineClass(Thread.currentThread().getContextClassLoader(), name, Files.readAllBytes(path));
        }
        catch (NoClassDefFoundError e) {
            // Parent class not loaded yet - load the class later
            classesToLoad.add(path);
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void defineClass(ClassLoader cl, String name, byte[] bytes) throws Throwable {
        var clazz = (Class<?>) DEFINE_CLASS.invoke(cl, name, bytes, 0, bytes.length);
        RESOLVE_CLASS.invoke(cl, clazz);
    }

    private static String toClassName(String name) {
        return name.substring(0, name.length() - ".class".length()).replace('/', '.');
    }

    private static TransformerManager getTransformerManager() {
        var provider = new BasicClassProvider();
        var manager = new TransformerManager(provider);
        // manager.addTransformerPreprocessor(new MixinsTranslator());
        for (Class<?> transformer : TRANSFORMERS) {
            manager.addTransformer(transformer.getName());
        }
        return manager;
    }

    @Override
    public String name() {
        return "ixeris-dummy";
    }

    @Override
    public Runnable initialize(String[] arguments) {
        throw new RuntimeException("IxerisImmediateWindowProvider is not a valid immediate window provider!");
    }

    @Override
    public void updateFramebufferSize(IntConsumer width, IntConsumer height) {

    }

    @Override
    public long setupMinecraftWindow(IntSupplier width, IntSupplier height, Supplier<String> title, LongSupplier monitor) {
        return 0;
    }

    @Override
    public boolean positionWindow(Optional<Object> monitor, IntConsumer widthSetter, IntConsumer heightSetter, IntConsumer xSetter, IntConsumer ySetter) {
        return false;
    }

    @Override
    public <T> Supplier<T> loadingOverlay(Supplier<?> mc, Supplier<?> ri, Consumer<Optional<Throwable>> ex, boolean fade) {
        return null;
    }

    @Override
    public void updateModuleReads(ModuleLayer layer) {

    }

    @Override
    public void periodicTick() {

    }

    @Override
    public String getGLVersion() {
        return "";
    }
}
