package dev.anye.mc.cores.mixin;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.loading.LogMarkers;
import net.minecraftforge.fml.loading.ModDirTransformerDiscoverer;
import net.minecraftforge.fml.loading.StringUtils;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Unique;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class ModsFolderLocatorMixin {

    @Unique
    private static final Logger AMLib$ModsFolderLocatorMixin$LOGGER = LogUtils.getLogger();
    @Final
    private static String SUFFIX;
    @Final
    private Path modFolder;

    /**
     * @author AnMaos
     * @reason fix
     */
    public Stream<Path> scanCandidates() {
        AMLib$ModsFolderLocatorMixin$LOGGER.debug(LogMarkers.SCAN,"Scanning mods dir {} for mods", this.modFolder);
        var excluded = ModDirTransformerDiscoverer.allExcluded();
        try {
            return Files.walk(this.modFolder)
                    .filter(p -> !Files.isDirectory(p))
                    .filter(p -> !excluded.contains(p))
                    .filter(p -> StringUtils.toLowerCase(p.getFileName().toString()).endsWith(SUFFIX))
                    .sorted(Comparator.comparing(path -> StringUtils.toLowerCase(path.getFileName().toString())));
        } catch (IOException e) {
            AMLib$ModsFolderLocatorMixin$LOGGER.error("Error while scanning mods directory: {}", e.getMessage());
            return Stream.empty();
        }
    }

}
