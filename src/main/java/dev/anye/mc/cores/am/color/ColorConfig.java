package dev.anye.mc.cores.am.color;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfigS;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

public class ColorConfig extends _JsonConfigS<ColorConfigData> {
	public static String FILE = _File.getFilePath(Cores.CONFIG_DIR, "color.json");
	public static ColorConfig instance = new ColorConfig();

	public ColorConfig() {
		super(FILE, new ColorConfigData(), new TypeToken<>() {
		});
	}
}
