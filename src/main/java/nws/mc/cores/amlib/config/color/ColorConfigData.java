package nws.mc.cores.amlib.config.color;

import nws.mc.cores.Cores;

public class ColorConfigData {
    private String colorScheme = Cores.MOD_ID +":default";
    public String getColorScheme() {
        return colorScheme;
    }
    public void setColorScheme(String colorScheme) {
        this.colorScheme = colorScheme;
    }
}
