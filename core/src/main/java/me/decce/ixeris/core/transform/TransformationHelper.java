package me.decce.ixeris.core.transform;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import me.decce.ixeris.core.MixinHelper;
import me.decce.ixeris.core.transform.util.TransformationConstants;
import net.lenni0451.classtransform.TransformerManager;
import net.lenni0451.classtransform.mixinstranslator.MixinsTranslator;
import net.lenni0451.classtransform.transformer.IAnnotationHandlerPreprocessor;
import net.lenni0451.classtransform.utils.tree.BasicClassProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static me.decce.ixeris.core.util.ReflectionHelper.unreflect;

public abstract class TransformationHelper {
    public final MethodHandle IMPL_ADD_READS_ALL_UNNAMED = unreflect(() -> Module.class.getDeclaredMethod("implAddReadsAllUnnamed"));
    public final MethodHandle IMPL_ADD_READS = unreflect(() -> Module.class.getDeclaredMethod("implAddReads", Module.class));

    protected final Logger LOGGER = LogManager.getLogger();

    public final ClassLoader modClassLoader;

    public TransformationHelper(ClassLoader modClassLoader) {
        this.modClassLoader = modClassLoader;
    }

    public static Optional<Module> findBootModule(String name) {
        return findBootModule(List.of(name));
    }

    public static Optional<Module> findBootModule(Iterable<String> aliases) {
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        for (String name : aliases) {
            return layer.findModule(name);
        }
        return Optional.empty();
    }

    protected abstract Class<?>[] getTransformers();

    protected Optional<Module> findGlfwModule() {
        return findBootModule(TransformationConstants.GLFW_MODULE_ALIASES);
    }

    protected Optional<Module> findSdlModule() {
        return findBootModule(TransformationConstants.SDL_MODULE_ALIASES);
    }

    protected Optional<Module> findLog4jModule() {
        return findBootModule("org.apache.logging.log4j");
    }

    public void expandGlfwModuleReads() {
        try {
            LOGGER.debug("Trying to expand module reads");
            var optionalGlfwModule = findGlfwModule();
            var optionalSdlModule = findSdlModule();
            var log4jModule = findLog4jModule().orElseThrow();
            if (optionalGlfwModule.isPresent()) {
                addReads(optionalGlfwModule.get(), log4jModule);
                IMPL_ADD_READS_ALL_UNNAMED.invoke(optionalGlfwModule.get()); // For access to classes in our mod
            }
            if (optionalSdlModule.isPresent()) {
                addReads(optionalSdlModule.get(), log4jModule);
                IMPL_ADD_READS_ALL_UNNAMED.invoke(optionalSdlModule.get()); // For access to classes in our mod
            }
            LOGGER.debug("Successfully expanded module reads");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void addReads(Module thisModule, Module thatModule) throws Throwable {
        if (!thisModule.toString().equals(thatModule.toString())) {
            IMPL_ADD_READS.invoke(thisModule, thatModule);
        }
    }

    public byte[] doTransformation(String className, byte[] classBytes, boolean useMixinsTranslator, IAnnotationHandlerPreprocessor... additionalPreprocessor) {
        var transformers = Arrays.stream(getTransformers()).filter(transformer -> MixinHelper.shouldApply(transformer.getName())).toList();
        if (transformers.isEmpty()) {
            return classBytes;
        }
        var manager = getTransformerManager(transformers, useMixinsTranslator, additionalPreprocessor);

        long millis = System.currentTimeMillis();
        var transformedBytes = manager.transform(className, classBytes);
        long elapsed = System.currentTimeMillis() - millis;

        LOGGER.debug("Successfully transformed class {} in {}ms", className, elapsed);

        return transformedBytes;
    }

    protected TransformerManager getTransformerManager(Iterable<Class<?>> transformers, boolean useMixinsTranslator, IAnnotationHandlerPreprocessor... additionalPreprocessor) {
        var provider = new BasicClassProvider(modClassLoader);
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
}
