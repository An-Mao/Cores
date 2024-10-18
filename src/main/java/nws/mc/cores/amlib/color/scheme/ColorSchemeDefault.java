package nws.mc.cores.amlib.color.scheme;

import nws.dev.core.color.scheme._ColorScheme;

public class ColorSchemeDefault extends _ColorScheme {

    public ColorSchemeDefault() {
    }
    @Override
    public void pushColor() {
        addColor("border",
                new _ColorScheme.Color(0xFF000000, 0xFF000000));
        addColor("text",
                new _ColorScheme.Color(0xFFFFFFFF, 0xFF0000FF));
        addColor("background",
                new _ColorScheme.Color(0x77000000, 0x77000000));
        addColor("element_border",
                new _ColorScheme.Color(0xFF000000, 0xFF000000));
        addColor("element_text",
                new _ColorScheme.Color(0xFFFFFFFF, 0xFF0000FF));
        addColor("element_background",
                new _ColorScheme.Color(0x77000000, 0x77000000));
    }

}
