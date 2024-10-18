package nws.mc.cores.amlib.color.scheme;

import nws.dev.core.color.scheme._ColorScheme;

public class SoftLavender extends _ColorScheme {
    @Override
    protected void pushColor() {
        _ColorScheme.Color c = new _ColorScheme.Color(0xFF9575CD, 0xFF7E57C2, 0xFF673AB7);
        addColor("border", c);
        addColor("element_border", c);
        c = new _ColorScheme.Color(0xFFEDE7F6, 0xFFD1C4E9, 0xFFB39DDB);
        addColor("background", c);
        addColor("element_background", c);
        c = new _ColorScheme.Color(0xFF311B92, 0xFF4527A0, 0xFF512DA8);
        addColor("text", c);
        addColor("element_text", c);
    }
}
