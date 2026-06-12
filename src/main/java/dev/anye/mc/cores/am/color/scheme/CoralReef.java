package dev.anye.mc.cores.am.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class CoralReef extends _ColorScheme {
	@Override
	protected void pushColor() {
		Color c = new Color(0xFFFF6F61, 0xFFFF8A80, 0xFFFF5252);
		addColor(BORDER, c);
		addColor(ELEMENT_BORDER, c);
		c = new Color(0xFFFFE0E0, 0xFFFFCDD2, 0xFFFF8A80);
		addColor(BACKGROUND, c);
		addColor(ELEMENT_BACKGROUND, c);
		c = new Color(0xFF212121, 0xFF424242, 0xFF616161);
		addColor(TEXT, c);
		addColor(ELEMENT_TEXT, c);
	}
}
