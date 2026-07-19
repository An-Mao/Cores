package dev.anye.mc.cores.am.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class MidnightBlue extends _ColorScheme {
    @Override
    protected void pushColor() {
        Color c = new Color(0xFF1A237E, 0xFF283593, 0xFF303F9F);
        addColor(BORDER, c);
        addColor(ELEMENT_BORDER, c);
        c = new Color(0xFFE8EAF6, 0xFFC5CAE9, 0xFF9FA8DA);
        addColor(BACKGROUND, c);
        addColor(ELEMENT_BACKGROUND, c);
        c = new Color(0xFFFFFFFF, 0xFFFAFAFA, 0xFFF5F5F5);
        addColor(TEXT, c);
        addColor(ELEMENT_TEXT, c);
    }
}
