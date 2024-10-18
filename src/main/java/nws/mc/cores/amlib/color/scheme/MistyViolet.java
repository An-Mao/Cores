package nws.mc.cores.amlib.color.scheme;

import nws.dev.core.color.scheme._ColorScheme;

public class MistyViolet  extends _ColorScheme {
    @Override
    public void pushColor() {
        _ColorScheme.Color c = new _ColorScheme.Color(0x804A148C, 0x994A148C,0xCC4A148C);
        addColor("border",c);
        addColor("element_border",c);
        c = new _ColorScheme.Color(0xFFEDE7F6, 0xFFC5CAE9,0xFFA5A4E4);
        addColor("background",c);
        addColor("element_background",c);
        c = new _ColorScheme.Color(0xFF311B92, 0x99311B92,0xCC311B92);
        addColor("text",c);
        addColor("element_text",c);
    }
}
