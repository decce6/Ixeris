package me.decce.ixeris.forge.core;

import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileModLocator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class IxerisModLocator extends AbstractJarFileModLocator {
    @Override
    public Stream<Path> scanCandidates() {
        if (!FMLEnvironment.dist.isClient()) {
            return Stream.empty();
        }
        try {
            final var resource = this.getClass().getResource("/META-INF/jarjar/");
            try (var stream = Files.walk(Path.of(Objects.requireNonNull(resource).toURI()), 1)) {
                return stream.filter(path -> path.getFileName().toString().endsWith("-mod.jar"));
            }
        } catch (Throwable t) {
            throw new RuntimeException("Loading Ixeris JiJ mod", t);
        }
    }

    @Override
    public String name() {
        return "IxerisModLocator";
    }

    @Override
    public void initArguments(Map<String, ?> arguments) {

    }
}
