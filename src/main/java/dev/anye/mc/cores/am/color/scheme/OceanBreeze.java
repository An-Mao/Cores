package dev.anye.mc.cores.am.color.scheme;


import dev.anye.core.color.scheme._ColorScheme;

public class OceanBreeze extends _ColorScheme {
    public OceanBreeze() {
    }
    @Override
    public void pushColor() {
        Color c = new Color(0x80489FB4, 0x99489FB4,0xCC489FB4);
        addColor(BORDER, c);
        addColor(ELEMENT_BORDER, c);
        c = new Color(0x4DE0F7FA, 0x66E0F7FA,0x80E0F7FA);
        addColor(BACKGROUND, c);
        addColor(ELEMENT_BACKGROUND, c);
        c = new Color(0x80334466, 0x99334466,0xCC334466);
        addColor(TEXT, c);
        addColor(ELEMENT_TEXT, c);
    }
}