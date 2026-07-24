package dev.anye.mc.cores.cores.config.color;

import dev.anye.mc.cores.Cores;


public class ColorConfigData {
	public static final ColorConfigData DEFAULT = new ColorConfigData();
    private String colorScheme;

	public ColorConfigData(String colorScheme){
		this.colorScheme = colorScheme;
	}
	public ColorConfigData(){
		this(Cores.MOD_ID + ":default");
	}


    public String getColorScheme() {
        return colorScheme;
    }
    public void setColorScheme(String colorScheme) {
        this.colorScheme = colorScheme;
    }
}
