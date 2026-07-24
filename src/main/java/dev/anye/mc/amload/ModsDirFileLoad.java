package dev.anye.mc.amload;

import com.mojang.logging.LogUtils;
import dev.anye.mc.cores.Cores;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.LogMarkers;
import net.minecraftforge.fml.loading.ModDirTransformerDiscoverer;
import net.minecraftforge.fml.loading.StringUtils;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileModLocator;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

public class ModsDirFileLoad extends AbstractJarFileModLocator {
    private String NAME = Cores.MOD_ID + " Load Subfolder Mod";
    private final String SUFFIX = ".jar";
    private static final Logger LOGGER = LogUtils.getLogger();
    private Path modFolder;

    public ModsDirFileLoad() {
        this(FMLPaths.MODSDIR.get());
    }

    ModsDirFileLoad(Path modFolder) {
        this(modFolder, "subfolder mods folder");
    }

    ModsDirFileLoad(Path modFolder, String name) {
        this.modFolder = modFolder;
        this.NAME = name;
    }

    @Override
    public Stream<Path> scanCandidates() {
        LOGGER.debug(LogMarkers.SCAN,"Scanning mods dir {} for mods", this.modFolder);
        var excluded = ModDirTransformerDiscoverer.allExcluded();
        try {
            return Files.walk(modFolder)
                    .filter(Files::isDirectory)
                    .filter(path -> !path.equals(modFolder))
                    .flatMap(d -> {
                        try {
                            return Files.walk(d);
                        } catch (IOException e) {
                            LOGGER.error("Error while walking directory {}: {}",d,e.getMessage());
                            return Stream.empty();
                        }
                    })
                    .filter(p -> !Files.isDirectory(p))
                    .filter(p -> !excluded.contains(p))
                    .sorted(Comparator.comparing(path -> StringUtils.toLowerCase(path.getFileName().toString())));
        } catch (IOException e) {
            LOGGER.error("Error while scanning mods directory: {}", e.getMessage());
            return Stream.empty();
        }
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void initArguments(Map<String, ?> arguments) {
    }
}
