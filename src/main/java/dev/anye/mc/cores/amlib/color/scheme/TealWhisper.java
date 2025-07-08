package dev.anye.mc.cores.amlib.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class TealWhisper extends _ColorScheme {
    @Override
    public void pushColor() {
        Color c = new Color(0x992E7D32, 0xCC388E3C,0xFF43A047);
        addColor("border",c);
        addColor("element_border",c);
        c = new Color(0xFFB2DFDB, 0xFF80CBC4,0xFF4DB6AC);
        addColor("background",c);
        addColor("element_background",c);
        c = new Color(0xCC004D40, 0x99004D40,0xFF004D40);
        addColor("text",c);
        addColor("element_text",c);
    }
}
