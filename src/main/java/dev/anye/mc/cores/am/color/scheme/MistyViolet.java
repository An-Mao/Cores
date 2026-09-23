package dev.anye.mc.cores.am.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class MistyViolet extends _ColorScheme {
	@Override
	public void pushColor() {
		Color c = new Color(0x804A148C, 0x994A148C, 0xCC4A148C);
		addColor(BORDER, c);
		addColor(ELEMENT_BORDER, c);
		c = new Color(0xFFEDE7F6, 0xFFC5CAE9, 0xFFA5A4E4);
		addColor(BACKGROUND, c);
		addColor(ELEMENT_BACKGROUND, c);
		c = new Color(0xFF311B92, 0x99311B92, 0xCC311B92);
		addColor(TEXT, c);
		addColor(ELEMENT_TEXT, c);
	}
}
