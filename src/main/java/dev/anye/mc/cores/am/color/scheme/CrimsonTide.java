package dev.anye.mc.cores.am.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class CrimsonTide extends _ColorScheme {
    @Override
    public void pushColor() {
        Color c = new Color(0x99D32F2F, 0xCCB71C1C,0xFFB71C1C);
        addColor(BORDER, c);
        addColor(ELEMENT_BORDER, c);
        c = new Color(0xFFFFEBEE, 0xFFF2F2F2,0xCCF2F2F2);
        addColor(BACKGROUND, c);
        addColor(ELEMENT_BACKGROUND, c);
        c = new Color(0xFF000000, 0x99000000,0xCC000000);
        addColor(TEXT, c);
        addColor(ELEMENT_TEXT, c);
    }
}
