package dev.anye.mc.cores.amlib.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class MidnightBlue extends _ColorScheme {
    @Override
    protected void pushColor() {
        Color c = new Color(0xFF1A237E, 0xFF283593, 0xFF303F9F);
        addColor("border", c);
        addColor("element_border", c);
        c = new Color(0xFFE8EAF6, 0xFFC5CAE9, 0xFF9FA8DA);
        addColor("background", c);
        addColor("element_background", c);
        c = new Color(0xFFFFFFFF, 0xFFFAFAFA, 0xFFF5F5F5);
        addColor("text", c);
        addColor("element_text", c);
    }
}
