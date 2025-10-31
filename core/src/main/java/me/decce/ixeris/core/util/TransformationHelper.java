package me.decce.ixeris.core.util;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import net.lenni0451.classtransform.TransformerManager;
import net.lenni0451.classtransform.mixinstranslator.MixinsTranslator;
import net.lenni0451.classtransform.transformer.IAnnotationHandlerPreprocessor;
import net.lenni0451.classtransform.utils.tree.BasicClassProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static me.decce.ixeris.core.util.ReflectionHelper.unreflect;

public abstract class TransformationHelper {
    public static final String MODULE_GLFW = "org.lwjgl.glfw";
    public static final Set<String> LEGAL_BOOTSTRAP_CLASSLOADERS = Set.of("MC-BOOTSTRAP", "SECURE-BOOTSTRAP", "app");
    public static final Set<String> LEGAL_MOD_CLASSLOADERS = Set.of("LAYER SERVICE", "TRANSFORMER", "FML Early Services");

    public final MethodHandle DEFINE_CLASS = unreflect(() -> ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class));
    public final MethodHandle RESOLVE_CLASS = unreflect(() -> ClassLoader.class.getDeclaredMethod("resolveClass", Class.class));
    public final MethodHandle IMPL_ADD_READS_ALL_UNNAMED = unreflect(() -> Module.class.getDeclaredMethod("implAddReadsAllUnnamed"));
    public final MethodHandle IMPL_ADD_READS = unreflect(() -> Module.class.getDeclaredMethod("implAddReads", Module.class));

    protected final Logger LOGGER = LogManager.getLogger();

    public final ClassLoader mcBootstrapClassLoader;
    public final ClassLoader modClassLoader;

    public TransformationHelper(ClassLoader mcBootstrapClassLoader, ClassLoader modClassLoader) {
        this.mcBootstrapClassLoader = mcBootstrapClassLoader;
        this.modClassLoader = modClassLoader;
    }

    public static Module findBootModule(String name) {
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        return layer.findModule(name).orElseThrow();
    }

    protected Module findGlfwModule() {
        return findBootModule(MODULE_GLFW);
    }

    protected Module findLog4jModule() {
        return findBootModule("org.apache.logging.log4j");
    }

    public void expandGlfwModuleReads() {
        try {
            LOGGER.debug("Trying to expand GLFW module reads");
            var glfwModule = findGlfwModule();
            addReads(glfwModule, findLog4jModule()); // We use logger in the injected code
            IMPL_ADD_READS_ALL_UNNAMED.invoke(glfwModule); // For access to classes in our mod
            LOGGER.debug("Successfully expanded GLFW module reads");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void addReads(Module thisModule, Module thatModule) throws Throwable {
        if (!thisModule.toString().equals(thatModule.toString())) {
            IMPL_ADD_READS.invoke(thisModule, thatModule);
        }
    }

    public byte[] doTransformation(Class<?>[] transformers, boolean useMixinsTranslator, IAnnotationHandlerPreprocessor... additionalPreprocessor) {
        try (var is = mcBootstrapClassLoader.getResourceAsStream("org/lwjgl/glfw/GLFW.class")) {
            var bytes = Objects.requireNonNull(is).readAllBytes();
            var manager = getTransformerManager(transformers, useMixinsTranslator, additionalPreprocessor);

            long millis = System.currentTimeMillis();
            var transformedBytes = manager.transform("org.lwjgl.glfw.GLFW", bytes);
            long elapsed = System.currentTimeMillis() - millis;

            LOGGER.info("Successfully transformed class org.lwjgl.glfw.GLFW in {}ms", elapsed);

            return transformedBytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyClassLoaders() {
        if (!LEGAL_BOOTSTRAP_CLASSLOADERS.contains(mcBootstrapClassLoader.getName())) {
            throw new IllegalStateException("IxerisBootstrapper found incorrect MC-BOOTSTRAP classloader: " + mcBootstrapClassLoader.getName());
        }
        if (!LEGAL_MOD_CLASSLOADERS.contains(modClassLoader.getName())) {
            throw new IllegalStateException("IxerisBootstrapper found incorrect mod classloader: " + modClassLoader.getName());
        }
    }

    public static String toClassName(String name) {
        if (name.startsWith("/")) name = name.substring(1);
        return name.replace(".class", "").replace('/', '.');
    }


    public void loadCoreClasses(Class<?> serviceClass) {
        LOGGER.info("Loading Ixeris coremod");
        var resource = serviceClass.getResource("/me/decce/ixeris/core");
        try (var stream = walkResource(Objects.requireNonNull(resource).toURI())) {
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

    protected Stream<Path> walkResource(URI resource) throws URISyntaxException, IOException {
        return Files.walk(Path.of(resource));
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

    protected TransformerManager getTransformerManager(Class<?>[] transformers, boolean useMixinsTranslator, IAnnotationHandlerPreprocessor... additionalPreprocessor) {
        var provider = new BasicClassProvider();
        var manager = new TransformerManager(provider);
        if (useMixinsTranslator) {
            manager.addTransformerPreprocessor(new MixinsTranslator());
        }
        for (IAnnotationHandlerPreprocessor preprocessor : additionalPreprocessor) {
            manager.addTransformerPreprocessor(preprocessor);
        }
        for (Class<?> transformer : transformers) {
            manager.addTransformer(transformer.getName());
        }
        return manager;
    }

    public abstract void removeModClassesFromServiceLayer();
}
