package me.decce.ixeris.forge.core;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.util.TransformationHelper;
import net.lenni0451.reflect.Agents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static me.decce.ixeris.core.util.TransformationHelper.isOnClient;

public class IxerisTransformationService implements ITransformationService {
    private final Logger LOGGER = LogManager.getLogger();
    private final TransformationHelper helper = new TransformationHelper();

    @Override
    public String name() {
        return "ixeris";
    }

    @Override
    public void initialize(IEnvironment iEnvironment) {
        if (!isOnClient()) {
            LOGGER.info("Skipped Ixeris bootstrapping because: on dedicated server");
            return;
        }

        helper.verifyClassLoaders(this.getClass());

        helper.loadCoreClasses(this.getClass());

        LOGGER.info("Attempting to transform org.lwjgl.glfw.GLFW");

        helper.expandGlfwModuleReads();

        var bytes = helper.doTransformation(TRANSFORMERS, false);

        try {
            this.redefineGlfw(bytes);
        } catch (UnmodifiableClassException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        helper.removeModClassesFromServiceLayer();

        this.temporarilySuppressEventPollingWarning();

        this.setGlfwInitialized();
    }

    @Override
    public void onLoad(IEnvironment iEnvironment, Set<String> set) throws IncompatibleEnvironmentException {

    }

    @Override
    public List<ITransformer> transformers() {
        return List.of();
    }

    private final Class<?>[] TRANSFORMERS = new Class[] {
            me.decce.ixeris.forge.core.transformers.GLFWTransformer.class,
            me.decce.ixeris.forge.core.transformers.callback_dispatcher.GLFWTransformer.class,
            me.decce.ixeris.forge.core.transformers.glfw_state_caching.GLFWTransformer.class,
            me.decce.ixeris.forge.core.transformers.glfw_threading.GLFWTransformer.class,
    };

    private Instrumentation getInstrumentation() {
        try {
            return Objects.requireNonNull(Agents.getInstrumentation());
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void redefineGlfw(byte[] bytes) throws UnmodifiableClassException, ClassNotFoundException {
        var instrumentation = getInstrumentation();
        instrumentation.redefineClasses(new ClassDefinition(GLFW.class, bytes));
    }

    // Must be called *after* everything else is done to make sure it uses the Ixeris class loaded on MC-BOOTSTRAP
    private void temporarilySuppressEventPollingWarning() {
        // Suppress the warnings produced by early display window calling glfwPollEvents, which are safely canceled
        Ixeris.suppressEventPollingWarning = true;
    }

    // We're late to the game! glfwInit() is already called.
    private void setGlfwInitialized() {
        Ixeris.glfwInitialized = true;
    }
}
