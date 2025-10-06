package me.decce.ixeris.core.util;

import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import net.lenni0451.classtransform.TransformerManager;
import net.lenni0451.classtransform.mixinstranslator.MixinsTranslator;
import net.lenni0451.classtransform.utils.tree.BasicClassProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.module.ResolvedModule;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import static me.decce.ixeris.core.util.ReflectionHelper.unreflect;
import static me.decce.ixeris.core.util.ReflectionHelper.unreflectGetter;

public class TransformationHelper {
    public static final String MODULE_GLFW = "org.lwjgl.glfw";

    public final MethodHandle DEFINE_CLASS = unreflect(() -> ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class));
    public final MethodHandle RESOLVE_CLASS = unreflect(() -> ClassLoader.class.getDeclaredMethod("resolveClass", Class.class));
    public final MethodHandle IMPL_ADD_READS_ALL_UNNAMED = unreflect(() -> Module.class.getDeclaredMethod("implAddReadsAllUnnamed"));
    public final MethodHandle IMPL_ADD_READS = unreflect(() -> Module.class.getDeclaredMethod("implAddReads", Module.class));

    private final Logger LOGGER = LogManager.getLogger();

    public ModuleClassLoader mcBootstrapClassLoader;
    public ModuleClassLoader layerServiceClassLoader;

    public static boolean isOnClient() {
        // Assume we're on client if the GLFW module does not exist.
        // This is not safe and might cause errors to be silenced.
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        return layer.findModule(MODULE_GLFW).isPresent();
    }

    public static Module findBootModule(String name) {
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        return layer.findModule(name).orElseThrow();
    }

    public void expandGlfwModuleReads() {
        try {
            LOGGER.debug("Trying to expand GLFW module reads");
            var glfwModule = findBootModule(MODULE_GLFW);
            IMPL_ADD_READS.invoke(glfwModule, findBootModule("org.apache.logging.log4j")); // We use logger in the injected code
            IMPL_ADD_READS_ALL_UNNAMED.invoke(glfwModule); // For access to classes in our mod
            LOGGER.debug("Successfully expanded GLFW module reads");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] doTransformation(Class<?>[] transformers, boolean useMixinsTranslator) {
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        var module = layer.configuration().findModule(MODULE_GLFW).orElseThrow();
        var ref = module.reference();
        try (var reader = ref.open()) {
            try (var stream = reader.open("org/lwjgl/glfw/GLFW.class").orElseThrow()) {
                var bytes = stream.readAllBytes();
                var manager = getTransformerManager(transformers, useMixinsTranslator);

                long millis = System.currentTimeMillis();
                var transformedBytes = manager.transform("org.lwjgl.glfw.GLFW", bytes);
                long elapsed = System.currentTimeMillis() - millis;

                LOGGER.info("Successfully transformed class org.lwjgl.glfw.GLFW in {}ms", elapsed);

                return transformedBytes;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyClassLoaders(Class<?> serviceClass) {
        mcBootstrapClassLoader = (ModuleClassLoader) Thread.currentThread().getContextClassLoader();
        if (!"MC-BOOTSTRAP".equals(mcBootstrapClassLoader.getName())) {
            throw new IllegalStateException("IxerisBootstrapper loaded with incorrect context classloader: " + mcBootstrapClassLoader.getName());
        }
        layerServiceClassLoader = (ModuleClassLoader) serviceClass.getClassLoader();
        if (!"LAYER SERVICE".equals(layerServiceClassLoader.getName())) {
            throw new IllegalStateException("IxerisBootstrapper loaded on incorrect classloader: " + layerServiceClassLoader.getName());
        }
    }

    public static String toClassName(String name) {
        if (name.startsWith("/")) name = name.substring(1);
        return name.replace(".class", "").replace('/', '.');
    }


    public void loadCoreClasses(Class<?> serviceClass) {
        LOGGER.info("Loading Ixeris coremod");
        var resource = serviceClass.getResource("/me/decce/ixeris/core");
        try (var stream = Files.walk(Path.of(Objects.requireNonNull(resource).toURI()))) {
            var classesToLoad = new LinkedList<>(stream.filter(p -> !Files.isDirectory(p) && p.toString().endsWith(".class")).toList());
            while (!classesToLoad.isEmpty()) {
                var clazz = classesToLoad.remove(0);
                if (!loadClass(clazz)) {
                    classesToLoad.add(clazz);
                }
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private boolean loadClass(Path path) {
        try {
            var name = toClassName(path.toString());
            this.defineClass(this.mcBootstrapClassLoader, name, Files.readAllBytes(path));
            return true;
        }
        catch (NoClassDefFoundError e) {
            // Parent class not loaded yet - load the class later
            return false;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void defineClass(ClassLoader cl, String name, byte[] bytes) throws Throwable {
        var clazz = (Class<?>) DEFINE_CLASS.invoke(cl, name, bytes, 0, bytes.length);
        RESOLVE_CLASS.invoke(cl, clazz);
    }

    private TransformerManager getTransformerManager(Class<?>[] transformers, boolean useMixinsTranslator) {
        var provider = new BasicClassProvider();
        var manager = new TransformerManager(provider);
        if (useMixinsTranslator) {
            manager.addTransformerPreprocessor(new MixinsTranslator());
        }
        for (Class<?> transformer : transformers) {
            manager.addTransformer(transformer.getName());
        }
        return manager;
    }

    public void removeModClassesFromServiceLayer() {
        try {
            // At this point our classes are already loaded on the MC-BOOTSTRAP classloader, but we need to do this here
            // to prevent the LAYER SERVICE classloader from loading them again (out Mixin plugin needs to use them to
            // decide whether to apply mixins)
            var packageLookupGetter = unreflectGetter(() -> ModuleClassLoader.class.getDeclaredField("packageLookup"));
            var packageLookup = (Map<String, ResolvedModule>) packageLookupGetter.invoke(this.layerServiceClassLoader);
            packageLookup.entrySet().removeIf(e -> e.getKey().startsWith("me.decce.ixeris.core"));

            // If we don't do this the LAYER SERVICE classloader will keep asking itself to load our class, eventually
            // causing a StackOverflowException
            var parentLoadersGetter = unreflectGetter(() -> ModuleClassLoader.class.getDeclaredField("parentLoaders"));
            var parentLoaders = (Map<String, ClassLoader>) parentLoadersGetter.invoke(this.layerServiceClassLoader);
            parentLoaders.entrySet().removeIf(e -> e.getKey().startsWith("me.decce.ixeris.core"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
