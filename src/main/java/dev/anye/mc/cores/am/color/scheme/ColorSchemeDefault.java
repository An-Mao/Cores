package dev.anye.mc.cores.am.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class ColorSchemeDefault extends _ColorScheme {

    public ColorSchemeDefault() {
    }
    @Override
    public void pushColor() {
        addColor("border",
                new Color(0xFF000000, 0xFF000000));
        addColor("text",
                new Color(0xFFFFFFFF, 0xFF0000FF));
        addColor("background",
                new Color(0x77000000, 0x77000000));
        addColor("element_border",
                new Color(0xFF000000, 0xFF000000));
        addColor("element_text",
                new Color(0xFFFFFFFF, 0xFF0000FF));
        addColor("element_background",
                new Color(0x77000000, 0x77000000));
    }

}
