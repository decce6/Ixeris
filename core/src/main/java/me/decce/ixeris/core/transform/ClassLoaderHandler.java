package me.decce.ixeris.core.transform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static me.decce.ixeris.core.util.ReflectionHelper.unreflect;

public abstract class ClassLoaderHandler {
    public final ClassLoader bootstrapClassLoader;
    public final ClassLoader modClassLoader;
    public final MethodHandle DEFINE_CLASS = unreflect(() -> ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class));
    public final MethodHandle RESOLVE_CLASS = unreflect(() -> ClassLoader.class.getDeclaredMethod("resolveClass", Class.class));
    public static final Set<String> LEGAL_BOOTSTRAP_CLASSLOADERS = Set.of("MC-BOOTSTRAP", "SECURE-BOOTSTRAP", "app");
    public static final Set<String> LEGAL_MOD_CLASSLOADERS = Set.of("LAYER SERVICE", "TRANSFORMER", "FML Early Services");

    protected final Logger LOGGER = LogManager.getLogger();

    public ClassLoaderHandler(ClassLoader bootstrapClassLoader, ClassLoader modClassLoader) {
        this.bootstrapClassLoader = bootstrapClassLoader;
        this.modClassLoader = modClassLoader;
        this.verifyClassLoaders();
    }

    public void verifyClassLoaders() {
        if (!LEGAL_BOOTSTRAP_CLASSLOADERS.contains(bootstrapClassLoader.getName())) {
            throw new IllegalStateException("Ixeris found incorrect bootstrap classloader: " + bootstrapClassLoader.getName());
        }
        if (!LEGAL_MOD_CLASSLOADERS.contains(modClassLoader.getName())) {
            throw new IllegalStateException("Ixeris found incorrect mod classloader: " + modClassLoader.getName());
        }
    }

    public static String toClassName(String name) {
        if (name.startsWith("/")) name = name.substring(1);
        return name.replace(".class", "").replace('/', '.');
    }

    public void loadCoreClasses(Class<?> modClass) {
        LOGGER.info("Loading Ixeris coremod");
        try (var stream = getClassesStream(modClass)) {
            var classesToLoad = new LinkedList<>(stream.filter(p -> !Files.isDirectory(p) && p.toString().endsWith(".class")).toList());
            while (!classesToLoad.isEmpty()) {
                var clazz = classesToLoad.remove(0);
                if (!loadClass(clazz)) {
                    classesToLoad.add(clazz);
                }
            }
        }
    }

    protected Stream<Path> getClassesStream(Class<?> modClass) {
        var resource = modClass.getResource("/me/decce/ixeris/core");
        try {
            return walkResource(Objects.requireNonNull(resource).toURI());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    protected Stream<Path> walkResource(URI resource) throws IOException {
        return Files.walk(Path.of(resource));
    }

    public byte[] readClassBytes(String name) {
        try (var stream = Objects.requireNonNull(bootstrapClassLoader.getResourceAsStream(name))) {
            return stream.readAllBytes();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean loadClass(Path path) {
        try {
            var name = toClassName(path.toString());
            this.defineClass(bootstrapClassLoader, name, Files.readAllBytes(path));
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

    public abstract void removeModClassesFromServiceLayer();
}
