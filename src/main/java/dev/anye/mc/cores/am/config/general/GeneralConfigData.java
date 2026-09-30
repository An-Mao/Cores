package dev.anye.mc.cores.am.config.general;

public final class GeneralConfigData {
	boolean showTipGui = false;
	boolean simpleWidgetBackground = true;
	boolean simpleWidgetRounded = false;
	boolean simpleWidgetGlow = false;

	public boolean simpleWidgetBackground() {
		return simpleWidgetBackground;
	}

	public boolean simpleWidgetRounded() {
		return simpleWidgetRounded;
	}

	public boolean simpleWidgetGlow() {
		return simpleWidgetGlow;
	}

	public void setSimpleWidgetBackground(boolean background) {
		this.simpleWidgetBackground = background;
	}

	public void setsimpleWidgetRounded(boolean rounded) {
		this.simpleWidgetRounded = rounded;
	}

	public void setsimpleWidgetGlow(boolean glow) {
		this.simpleWidgetGlow = glow;
	}
}