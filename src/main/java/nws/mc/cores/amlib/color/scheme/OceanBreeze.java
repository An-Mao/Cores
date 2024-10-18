package nws.mc.cores.amlib.color.scheme;


import nws.dev.core.color.scheme._ColorScheme;

public class OceanBreeze extends _ColorScheme {
    public OceanBreeze() {
    }
    @Override
    public void pushColor() {
        _ColorScheme.Color c = new _ColorScheme.Color(0x80489FB4, 0x99489FB4,0xCC489FB4);
        addColor("border", c);
        addColor("element_border", c);
        c = new _ColorScheme.Color(0x4DE0F7FA, 0x66E0F7FA,0x80E0F7FA);
        addColor("background",c);
        addColor("element_background",c);
        c = new _ColorScheme.Color(0x80334466, 0x99334466,0xCC334466);
        addColor("text",c);
        addColor("element_text",c);
    }
}