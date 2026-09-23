package dev.anye.mc.cores.am.color.scheme;


import dev.anye.core.color.scheme._ColorScheme;

public class AutumnLeaves extends _ColorScheme {
	@Override
	public void pushColor() {
		Color c = new Color(0x80A0522D, 0x99A0522D, 0xCCA0522D);
		addColor(BORDER, c);
		addColor(ELEMENT_BORDER, c);
		c = new Color(0x4DFFEEE0, 0x66FFEEE0, 0x80FFEEE0);
		addColor(BACKGROUND, c);
		addColor(ELEMENT_BACKGROUND, c);
		c = new Color(0x80401A0A, 0x99401A0A, 0xCC401A0A);
		addColor(TEXT, c);
		addColor(ELEMENT_TEXT, c);
	}
}
