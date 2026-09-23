package dev.anye.mc.cores.am.color.scheme;


import dev.anye.core.color.scheme._ColorScheme;

public class SunsetGlow extends _ColorScheme {
	public SunsetGlow() {
	}

	@Override
	public void pushColor() {
		Color c = new Color(0x80FF7043, 0x99FF7043, 0xCCFF7043);
		addColor(BORDER, c);
		addColor(ELEMENT_BORDER, c);
		c = new Color(0x4DFFE6E6, 0x66FFE6E6, 0x80FFE6E6);
		addColor(BACKGROUND, c);
		addColor(ELEMENT_BACKGROUND, c);
		c = new Color(0x803D1E1E, 0x993D1E1E, 0xCC3D1E1E);
		addColor(TEXT, c);
		addColor(ELEMENT_TEXT, c);
	}
}