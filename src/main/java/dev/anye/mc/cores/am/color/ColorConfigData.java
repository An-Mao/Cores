package dev.anye.mc.cores.am.color;

import dev.anye.mc.cores.Cores;

public class ColorConfigData {
	private String colorScheme;

	public ColorConfigData(){
		this(Cores.MOD_ID + ":default");
	}
	public ColorConfigData(String colorScheme){
		this.colorScheme = colorScheme;
	}

	public String getColorScheme() {
		return colorScheme;
	}

	public void setColorScheme(String colorScheme) {
		this.colorScheme = colorScheme;
	}
}
