package dev.anye.mc.cores.am.color;

import com.google.gson.reflect.TypeToken;

import dev.anye.core.color.scheme._ColorScheme.Color;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

public class ColorSchemeIO extends _JsonConfig<ColorSchemeIO.Data> {
	public static final String FILE_PATH = _File.getFilePath(Cores.CONFIG_DIR, "colorSchemes");

	static {
		_File.checkAndCreateDir(FILE_PATH);
	}

	public ColorSchemeIO(String path) {
		super(_File.getFilePath(FILE_PATH, path), Data.DEFAULT, new TypeToken<>() {
		});
	}


	public record Data(Color border, Color text, Color background,
	                   Color elementBorder, Color elementText, Color elementBackground) {
		public static final Data DEFAULT = new Data(
				new Color(0xFF000000, 0xFF000000, 0xFF000000),
				new Color(0xFFFFFFFF, 0xFF0000FF, 0xFF000000),
				new Color(0xFF000000, 0xFF000000, 0xFF000000),
				new Color(0xFF000000, 0xFF000000, 0xFF000000),
				new Color(0xFFFFFFFF, 0xFF0000FF, 0xFF000000),
				new Color(0xFF000000, 0xFF000000, 0xFF000000)
		);

	}

}
