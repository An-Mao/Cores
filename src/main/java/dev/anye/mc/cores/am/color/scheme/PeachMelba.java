package dev.anye.mc.cores.am.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class PeachMelba extends _ColorScheme {
    @Override
    public void pushColor() {
        Color c = new Color(0xFFF8BBD0, 0xFFEC407A,0xFFE91E63);
        addColor("border",c);
        addColor("element_border",c);
        c = new Color(0xFFFFF3E0, 0xFFFFCC80,0xFFFFAB40);
        addColor("background",c);
        addColor("element_background",c);
        c = new Color(0xFF3E2723, 0xCC3E2723,0x993E2723);
        addColor("text",c);
        addColor("element_text",c);
    }
}
