package dev.anye.mc.cores.am.color.scheme;


import dev.anye.core.color.scheme._ColorScheme;

public class FreshMint extends _ColorScheme {
	public FreshMint() {
	}

	@Override
	protected void pushColor() {
		Color c = new Color(0x807CC37C, 0x997CC37C, 0xCC7CC37C);
		addColor(BORDER, c);
		addColor(ELEMENT_BORDER, c);
		c = new Color(0x4DE6FFE6, 0x66E6FFE6, 0x80E6FFE6);
		addColor(BACKGROUND, c);
		addColor(ELEMENT_BACKGROUND, c);
		c = new Color(0x80333333, 0x99333333, 0xCC333333);
		addColor(TEXT, c);
		addColor(ELEMENT_TEXT, c);
            /*
            addColor("border",
                    new ColorScheme.Color(0x807CC37C, 0x997CC37C,0xCC7CC37C));
            addColor("background",
                    new ColorScheme.Color(0x4DE6FFE6, 0x66E6FFE6,0x80E6FFE6));
            addColor("text",
                    new ColorScheme.Color(0x80333333, 0x99333333,0xCC333333));

             */
	}
}