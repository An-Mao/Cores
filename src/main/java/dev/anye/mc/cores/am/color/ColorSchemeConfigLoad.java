package dev.anye.mc.cores.am.color;

import dev.anye.core.color.scheme._ColorScheme;

public class ColorSchemeConfigLoad extends _ColorScheme {
	public ColorSchemeConfigLoad(ColorSchemeIO.Data data) {
		super(data.border(), data.text(), data.background(), data.elementBorder(), data.elementText(), data.elementBackground());
	}
}
