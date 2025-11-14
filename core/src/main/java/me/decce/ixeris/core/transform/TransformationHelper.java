package me.decce.ixeris.core.transform;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import net.lenni0451.classtransform.TransformerManager;
import net.lenni0451.classtransform.mixinstranslator.MixinsTranslator;
import net.lenni0451.classtransform.transformer.IAnnotationHandlerPreprocessor;
import net.lenni0451.classtransform.utils.tree.BasicClassProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;

import static me.decce.ixeris.core.util.ReflectionHelper.unreflect;

public abstract class TransformationHelper {
    public static final String MODULE_GLFW = "org.lwjgl.glfw";

    public final MethodHandle IMPL_ADD_READS_ALL_UNNAMED = unreflect(() -> Module.class.getDeclaredMethod("implAddReadsAllUnnamed"));
    public final MethodHandle IMPL_ADD_READS = unreflect(() -> Module.class.getDeclaredMethod("implAddReads", Module.class));

    protected final Logger LOGGER = LogManager.getLogger();

    public final ClassLoader modClassLoader;

    public TransformationHelper(ClassLoader modClassLoader) {
        this.modClassLoader = modClassLoader;
    }

    public static Module findBootModule(String name) {
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        return layer.findModule(name).orElseThrow();
    }

    protected abstract Class<?>[] getTransformers();

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

    public byte[] doTransformation(String className, byte[] classBytes, boolean useMixinsTranslator, IAnnotationHandlerPreprocessor... additionalPreprocessor) {
        var manager = getTransformerManager(getTransformers(), useMixinsTranslator, additionalPreprocessor);

        long millis = System.currentTimeMillis();
        var transformedBytes = manager.transform(className, classBytes);
        long elapsed = System.currentTimeMillis() - millis;

        LOGGER.info("Successfully transformed class {} in {}ms", className, elapsed);

        return transformedBytes;
    }

    protected TransformerManager getTransformerManager(Class<?>[] transformers, boolean useMixinsTranslator, IAnnotationHandlerPreprocessor... additionalPreprocessor) {
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
