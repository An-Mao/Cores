package dev.anye.mc.cores.am.config.attribute;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

import java.util.Map;

public class AttributeConfig extends _JsonConfig<Map<String, AttributeData>> {
	public static final String FILE_PATH = _File.getFilePath(Cores.CONFIG_DIR, "attribute-config.json");

	public AttributeConfig() {
		super(FILE_PATH, AttributeData.DEFAULT, new TypeToken<>() {});
	}
}
