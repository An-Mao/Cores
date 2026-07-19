package dev.anye.mc.cores.am.color.scheme;


import dev.anye.core.color.scheme._ColorScheme;

public class BlueFresh extends _ColorScheme {
    public BlueFresh() {
    }
    @Override
    public void pushColor() {
        addColor(BORDER,
                new Color(0x8044CCFF, 0xA63399CC,0xCC2266CC));
        addColor(BACKGROUND,
                new Color(0x4DE6F0FF, 0x66CCE0FF,0x8099C2FF));
        addColor(TEXT,
                new Color(0x99113399, 0xB2000D66,0xCC000A33));
        addColor(ELEMENT_BORDER,
                new Color(0xA63366FF, 0xCC2266CC,0xFF113399));
        addColor(ELEMENT_BACKGROUND,
                new Color(0x66D6E0FF, 0x80B3C2FF,0x998099FF));
        addColor(ELEMENT_TEXT,
                new Color(0xB2002666, 0xCC113399,0xFF000A1A));
    }
}