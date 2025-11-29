//? if forge {
/*package me.decce.ixeris.forge;

import me.decce.ixeris.core.Ixeris;
import net.lenni0451.reflect.Agents;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Objects;

public class IxerisTransformer {
    private final Logger LOGGER = LogManager.getLogger();

    public void run() {
        if (!isOnClient()) {
            LOGGER.info("Skipped Ixeris bootstrapping because: on dedicated server");
            return;
        }

        //? if >=1.18.2 {
        var classLoaderHandler = new ForgeClassLoaderHandler(Logger.class.getClassLoader(), this.getClass().getClassLoader());
        //?} else {
        /^var classLoaderHandler = new LegacyForgeClassLoaderHandler(Logger.class.getClassLoader(), this.getClass().getClassLoader());
        ^///?}
        classLoaderHandler.loadCoreClasses(this.getClass());
        classLoaderHandler.removeModClassesFromServiceLayer();

        if (!Ixeris.getConfig().isEnabled()) {
            LOGGER.info("Skipped Ixeris bootstrapping because: disabled by config");
            return;
        }

        LOGGER.info("Attempting to transform org.lwjgl.glfw.GLFW");

        //? if >=1.18.2 {
        var helper = new ForgeTransformationHelper(classLoaderHandler.modClassLoader);
        //?} else {
        /^var helper = new LegacyForgeTransformationHelper(classLoaderHandler.modClassLoader);
        ^///?}

        helper.expandGlfwModuleReads();

        var bytes = helper.doTransformation("org.lwjgl.glfw.GLFW", classLoaderHandler.readClassBytes("org/lwjgl/glfw/GLFW.class"), false);

        try {
            this.redefineGlfw(bytes);
        } catch (UnmodifiableClassException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Ixeris.inEarlyDisplay = true;

        //? if <=1.16.5 {
        /^try {
            classLoaderHandler.close();
        } catch (IOException ignored) {}
        ^///?}
    }

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

    public static boolean isOnClient() {
        return FMLLoader.getDist().isClient();
    }
}
*///?}