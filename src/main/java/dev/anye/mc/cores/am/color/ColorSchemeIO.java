package dev.anye.mc.cores.am.color;

import com.google.gson.reflect.TypeToken;

import dev.anye.core.color.scheme._ColorScheme.Color;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

public class ColorSchemeIO extends _JsonConfig<ColorSchemeIO.Data> {
	public static final String filePath = _File.getFilePath(Cores.CONFIG_DIR, "colorSchemes");

	static {
		_File.checkAndCreateDir(filePath);
	}

	public ColorSchemeIO(String path) {
		super(_File.getFilePath(filePath, path), """
				{
					"border":{
						"UsualColor":"#FF000000",
						"HoverColor":"#FF000000",
						"SelectColor":"#FF000000"
					},
					"text":{
						"UsualColor":"#FFFFFFFF",
						"HoverColor":"#FF0000FF",
						"SelectColor":"#FF000000"
					},
					"background":{
						"UsualColor":"#FF000000",
						"HoverColor":"#FF000000",
						"SelectColor":"#FF000000"
					},
					"elementBorder":{
						"UsualColor":"#FF000000",
						"HoverColor":"#FF000000",
						"SelectColor":"#FF000000"
					},
					"elementText":{
						"UsualColor":"#FFFFFFFF",
						"HoverColor":"#FF0000FF",
						"SelectColor":"#FF000000"
					},
					"elementBackground":{
						"UsualColor":"#FF000000",
						"HoverColor":"#FF000000",
						"SelectColor":"#FF000000"
					}
				}
				""", new TypeToken<>() {
		});
	}


	public static record Data(Color border, Color text, Color background, Color elementBorder, Color elementText,
	                          Color elementBackground) {

	}

}
