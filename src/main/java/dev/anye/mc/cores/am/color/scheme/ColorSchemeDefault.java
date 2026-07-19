package dev.anye.mc.cores.am.color.scheme;

import dev.anye.core.color.scheme._ColorScheme;

public class ColorSchemeDefault extends _ColorScheme {

    public ColorSchemeDefault() {
    }
    @Override
    public void pushColor() {
        addColor(BORDER,
                new Color(0xFF000000, 0xFF000000));
        addColor(TEXT,
                new Color(0xFFFFFFFF, 0xFF0000FF));
        addColor(BACKGROUND,
                new Color(0x77000000, 0x77000000));
        addColor(ELEMENT_BORDER,
                new Color(0xFF000000, 0xFF000000));
        addColor(ELEMENT_TEXT,
                new Color(0xFFFFFFFF, 0xFF0000FF));
        addColor(ELEMENT_BACKGROUND,
                new Color(0x77000000, 0x77000000));
    }

}
