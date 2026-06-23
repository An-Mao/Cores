package dev.anye.mc.cores.am.color.scheme;


import dev.anye.core.color.scheme._ColorScheme;

public class NaturalGreenery extends _ColorScheme {
	public NaturalGreenery() {
	}

	@Override
	public void pushColor() {
		addColor(BORDER,
				new Color(0x8066CC66, 0xA655AA55, 0xCC448844));
		addColor(BACKGROUND,
				new Color(0x4DE6FFE6, 0x66D9FFD9, 0x80CCFFCC));
		addColor(TEXT,
				new Color(0x99330033, 0xB2220022, 0xCC110011));
		addColor(ELEMENT_BORDER,
				new Color(0xA699CC99, 0xCC77AA77, 0xFF558855));
		addColor(ELEMENT_BACKGROUND,
				new Color(0x66D9FFD9, 0x80BFFFBF, 0x99A6FFA6));
		addColor(ELEMENT_TEXT,
				new Color(0xB2336633, 0xCC224422, 0xFF112211));
	}
}