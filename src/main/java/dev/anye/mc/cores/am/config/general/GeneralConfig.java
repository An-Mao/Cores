package dev.anye.mc.cores.am.config.general;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfigR;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

public class GeneralConfig extends _JsonConfigR<GeneralConfigData> {
	public static final String FILE_PATH = _File.getFilePath(Cores.CONFIG_DIR, "general.json");

	public GeneralConfig() {
		super(FILE_PATH, new GeneralConfigData(true), new TypeToken<>() {
		});
	}
}
