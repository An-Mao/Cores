package dev.anye.mc.cores.am.color;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

public class ColorConfig extends _JsonConfig<ColorConfigData> {
	public static String filePath = _File.getFilePath(Cores.CONFIG_DIR, "color.json");
	public static ColorConfig instance = new ColorConfig();

	public ColorConfig() {
		super(filePath, """
				           {
				"colorScheme": "cores:default"
				           }
				""", new TypeToken<>() {
		});
	}
}
