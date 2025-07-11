package dev.anye.mc.cores.am.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class EmeraldGreen extends _ColorScheme {
    @Override
    protected void pushColor() {
        Color c = new Color(0xFF2E7D32, 0xFF388E3C, 0xFF43A047);
        addColor("border", c);
        addColor("element_border", c);
        c = new Color(0xFFE8F5E9, 0xFFC8E6C9, 0xFFA5D6A7);
        addColor("background", c);
        addColor("element_background", c);
        c = new Color(0xFF004D40, 0xFF00695C, 0xFF00796B);
        addColor("text", c);
        addColor("element_text", c);
    }
}
