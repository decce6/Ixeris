package me.decce.ixeris.neoforge.core;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import net.lenni0451.classtransform.TransformerManager;
import net.lenni0451.classtransform.utils.tree.BasicClassProvider;
import net.neoforged.neoforgespi.earlywindow.GraphicsBootstrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static me.decce.ixeris.neoforge.core.ReflectionHelper.unreflect;

public class IxerisBootstrapper implements GraphicsBootstrapper {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final MethodHandle DEFINE_CLASS = unreflect(() -> ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class));
    public static final MethodHandle RESOLVE_CLASS = unreflect(() -> ClassLoader.class.getDeclaredMethod("resolveClass", Class.class));
    public static final MethodHandle IMPL_ADD_READS_ALL_UNNAMED = unreflect(() -> Module.class.getDeclaredMethod("implAddReadsAllUnnamed"));
    public static final MethodHandle IMPL_ADD_READS = unreflect(() -> Module.class.getDeclaredMethod("implAddReads", Module.class));

    private static List<Path> classesToLoad;

    private static final Class<?>[] TRANSFORMERS = new Class[] {
            me.decce.ixeris.neoforge.core.transformers.GLFWTransformer.class,
            me.decce.ixeris.neoforge.core.transformers.callback_dispatcher.GLFWTransformer.class,
            me.decce.ixeris.neoforge.core.transformers.glfw_state_caching.GLFWTransformer.class,
            me.decce.ixeris.neoforge.core.transformers.glfw_threading.GLFWTransformer.class,
    };

    private static Module findBootModule(String name) {
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        return layer.findModule(name).orElseThrow();
    }

    @Override
    public String name() {
        return "ixeris";
    }

    // Must run before org.lwjgl.glfw.GLFW is loaded
    @Override
    public void bootstrap(String[] arguments) {
        var cl = Thread.currentThread().getContextClassLoader();

        // Important: do not use the LOGGER from Ixeris at here - Ixeris must be loaded on the MC-BOOTSTRAP classloader
        LOGGER.debug("IxerisBootstrapper - Bootstrapping on classloader {} of type {}", cl.getName(), cl.getClass().getName());

        this.loadCoreClasses();

        LOGGER.info("Attempting to transform org.lwjgl.glfw.GLFW");

        this.expandGlfwModuleReads();

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

    private void expandGlfwModuleReads() {
        try {
            var glfwModule = findBootModule("org.lwjgl.glfw");
            IMPL_ADD_READS.invoke(glfwModule, findBootModule("org.apache.logging.log4j")); // We use logger in the injected code
            IMPL_ADD_READS_ALL_UNNAMED.invoke(glfwModule); // For access to classes in our mod
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCoreClasses() {
        LOGGER.info("Loading Ixeris coremod");
        var resource = IxerisBootstrapper.class.getResource("/me/decce/ixeris/core");
        try (var stream = Files.walk(Path.of(Objects.requireNonNull(resource).toURI()))) {
            classesToLoad = new LinkedList<>(stream.filter(p -> !Files.isDirectory(p) && p.toString().endsWith(".class")).toList());
            while (!classesToLoad.isEmpty()) {
                loadClass(classesToLoad.remove(0));
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private void loadClass(Path path) {
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

    private void defineClass(ClassLoader cl, String name, byte[] bytes) throws Throwable {
        var clazz = (Class<?>) DEFINE_CLASS.invoke(cl, name, bytes, 0, bytes.length);
        RESOLVE_CLASS.invoke(cl, clazz);
    }

    private static String toClassName(String name) {
        return name.substring(0, name.length() - ".class".length()).replace('/', '.');
    }

    private TransformerManager getTransformerManager() {
        var provider = new BasicClassProvider();
        var manager = new TransformerManager(provider);
        // manager.addTransformerPreprocessor(new MixinsTranslator());
        for (Class<?> transformer : TRANSFORMERS) {
            manager.addTransformer(transformer.getName());
        }
        return manager;
    }
}
