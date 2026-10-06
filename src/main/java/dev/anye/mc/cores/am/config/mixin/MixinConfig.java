package dev.anye.mc.cores.am.config.mixin;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfigR;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

public class MixinConfig extends _JsonConfigR<MixinConfigData> {
	public static final String FILE_PATH = _File.getFilePath(Cores.CONFIG_DIR, "mixins.json");

	public MixinConfig() {
		super(FILE_PATH, MixinConfigData.DEFAULT, new TypeToken<>() {});
	}
}
