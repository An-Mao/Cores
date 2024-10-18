package nws.mc.cores.amlib.color.scheme;

import nws.dev.core.color.scheme._ColorScheme;

public class GoldenSands  extends _ColorScheme {
    @Override
    protected void pushColor() {
        _ColorScheme.Color c = new _ColorScheme.Color(0xFFFFB300, 0xFFFFA000, 0xFFFF8F00);
        addColor("border", c);
        addColor("element_border", c);
        c = new _ColorScheme.Color(0xFFFFF3E0, 0xFFFFE0B2, 0xFFFFCC80);
        addColor("background", c);
        addColor("element_background", c);
        c = new _ColorScheme.Color(0xFF795548, 0xFF6D4C41, 0xFF5D4037);
        addColor("text", c);
        addColor("element_text", c);
    }
}
