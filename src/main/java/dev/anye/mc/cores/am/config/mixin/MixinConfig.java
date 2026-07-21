package dev.anye.mc.cores.am.config.mixin;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

import java.util.HashMap;
import java.util.Map;

public class MixinConfig extends _JsonConfig<Map<String, Boolean>> {
	public static final String FILE_PATH = _File.getFilePath(Cores.CONFIG_DIR, "mixins.json");
	protected static final Map<String,Boolean> DEFAULT = new HashMap<>();
	static {
		DEFAULT.put("Attributes",true);
		DEFAULT.put("PlayerLevel",true);
	}
	public MixinConfig() {
		super(FILE_PATH, DEFAULT, new TypeToken<>() {});
	}

	public boolean isEnable(String key) {
		return map(map -> map.getOrDefault(key,false)).orElse(false);
	}
}
