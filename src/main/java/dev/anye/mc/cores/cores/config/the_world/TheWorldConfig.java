package dev.anye.mc.cores.cores.config.the_world;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

public class TheWorldConfig extends _JsonConfig<TheWorldConfigData> {
	public static final String FILE_PATH = _File.getFilePath(Cores.CONFIG_DIR, "TheWorld.json");
    public TheWorldConfig() {
        super(FILE_PATH, TheWorldConfigData.DEFAULT, new TypeToken<>() {});
    }
}
