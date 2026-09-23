package dev.anye.mc.cores.am.color;

import dev.anye.core.color.scheme._ColorScheme;

public class ColorSchemeConfigLoad extends _ColorScheme {

	public ColorSchemeConfigLoad(ColorSchemeIO.Data data) {
		addColor(BORDER, data.border());
		addColor(TEXT, data.text());
		addColor(BACKGROUND, data.background());
		addColor(ELEMENT_BORDER, data.elementBorder());
		addColor(ELEMENT_TEXT, data.elementText());
		addColor(ELEMENT_BACKGROUND, data.elementBackground());
	}
}
