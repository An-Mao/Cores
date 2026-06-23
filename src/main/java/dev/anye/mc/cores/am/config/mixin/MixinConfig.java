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

	@Override
	public HashMap<String, Boolean> getDatas() {
		if (datas == null) datas = new HashMap<>();
		return super.getDatas();
	}

	public boolean isEnable(String key) {
		return getDatas().getOrDefault(key, false);
	}
}
