package dev.anye.mc.cores.am.config.mixin;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

import java.util.HashMap;

public class MixinConfig extends _JsonConfig<HashMap<String, Boolean>> {
	public static final String file = _File.getFilePath(Cores.CONFIG_DIR, "mixins.json");

	public MixinConfig() {
		super(file, """
				{
				  "Attributes": true,
				  "PlayerLevel": true
				}""", new TypeToken<>() {
		});
	}

	public boolean isEnable(String key) {
		return getData().getOrDefault(key, false);
	}
}
