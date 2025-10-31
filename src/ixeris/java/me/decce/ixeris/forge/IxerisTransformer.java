//? if forge {
/*package me.decce.ixeris.forge;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.util.TransformationHelper;
import net.lenni0451.reflect.Agents;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Objects;

public class IxerisTransformer {
    private final Logger LOGGER = LogManager.getLogger();
    private final TransformationHelper helper = new ForgeTransformationHelper(Logger.class.getClassLoader(), this.getClass().getClassLoader());

    public void run() {
        if (!isOnClient()) {
            LOGGER.info("Skipped Ixeris bootstrapping because: on dedicated server");
            return;
        }

        helper.verifyClassLoaders();

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
    }

    private final Class<?>[] TRANSFORMERS = new Class[] {
            me.decce.ixeris.forge.transformers.GLFWTransformer.class,
            me.decce.ixeris.forge.transformers.callback_dispatcher.GLFWTransformer.class,
            me.decce.ixeris.forge.transformers.glfw_state_caching.GLFWTransformer.class,
            me.decce.ixeris.forge.transformers.glfw_threading.GLFWTransformer.class,
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

    public static boolean isOnClient() {
        return FMLLoader.getDist().isClient();
    }
}
*///?}