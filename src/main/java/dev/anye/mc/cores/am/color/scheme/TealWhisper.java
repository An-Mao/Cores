package dev.anye.mc.cores.am.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class TealWhisper extends _ColorScheme {
	@Override
	public void pushColor() {
		Color c = new Color(0x992E7D32, 0xCC388E3C, 0xFF43A047);
		addColor(BORDER, c);
		addColor(ELEMENT_BORDER, c);
		c = new Color(0xFFB2DFDB, 0xFF80CBC4, 0xFF4DB6AC);
		addColor(BACKGROUND, c);
		addColor(ELEMENT_BACKGROUND, c);
		c = new Color(0xCC004D40, 0x99004D40, 0xFF004D40);
		addColor(TEXT, c);
		addColor(ELEMENT_TEXT, c);
	}
}
