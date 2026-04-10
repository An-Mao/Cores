package dev.anye.mc.cores.am.color.scheme;


import dev.anye.core.color.scheme._ColorScheme;

public class ColdGray extends _ColorScheme {
    public ColdGray() {
    }
    @Override
    public void pushColor() {
        addColor("border",
                new Color(0x80CCCCCC, 0xA6999999,0xCC666666));
        addColor("background",
                new Color(0x4DFFFFFF, 0x66E6E6E6,0x80CCCCCC));
        addColor("text",
                new Color(0x99333333, 0xB2000000,0xCC000000));
        addColor("element_border",
                new Color(0xA6999999, 0xCC666666,0xFF333333));
        addColor("element_background",
                new Color(0x66F2F2F2, 0x80D9D9D9,0x99BFBFBF));
        addColor("element_text",
                new Color(0xB2666666, 0xCC333333,0xFF000000));
    }
}
