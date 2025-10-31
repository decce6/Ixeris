package me.decce.ixeris.neoforge.core;

import me.decce.ixeris.core.util.TransformationHelper;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

public class NeoForgeTransformationHelper extends TransformationHelper {
    private FileSystem fileSystem;

    public NeoForgeTransformationHelper(ClassLoader mcBootstrapClassLoader, ClassLoader modClassLoader) {
        super(mcBootstrapClassLoader, modClassLoader);
    }

    @Override
    public void removeModClassesFromServiceLayer() {
        // no-op
    }

    @Override
    protected Module findGlfwModule() {
        return GLFWErrorCallback.class.getModule();
    }

    @Override
    protected Module findLog4jModule() {
        return LOGGER.getClass().getModule();
    }

    @Override
    protected Stream<Path> walkResource(URI resource) throws IOException {
        var s = resource.toString().split("!");
        fileSystem = FileSystems.newFileSystem(URI.create(s[0]), Map.of());
        var path = fileSystem.getPath(s[1]);
        return Files.walk(path);
    }

    public void close() throws IOException {
        if (fileSystem != null) {
            fileSystem.close();
        }
    }
}
