package dev.anye.mc.cores.am.color.scheme;


import dev.anye.core.color.scheme._ColorScheme;

public class TwilightPurple extends _ColorScheme {
    public TwilightPurple() {
    }
    @Override
    public void pushColor() {
        Color c = new Color(0x805E4B8B, 0x995E4B8B,0xCC5E4B8B);
        addColor(BORDER, c);
        addColor(ELEMENT_BORDER, c);
        c = new Color(0x4DE3D4F8, 0x66E3D4F8,0x80E3D4F8);
        addColor(BACKGROUND, c);
        addColor(ELEMENT_BACKGROUND, c);
        c = new Color(0x803A2E75, 0x993A2E75,0xCC3A2E75);
        addColor(TEXT, c);
        addColor(ELEMENT_TEXT, c);
    }
}
