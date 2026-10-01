package dev.anye.mc.cores.am.config.general;

public final class GeneralConfigData {
	boolean showTipGui = false;
	boolean simpleWidgetBackground = true;
	boolean simpleWidgetRounded = false;
	boolean simpleWidgetGlow = false;
	boolean simpleInnerGlow = true;
	boolean simpleOuterGlow = true;

	public boolean simpleWidgetBackground() {
		return simpleWidgetBackground;
	}

	public boolean simpleWidgetRounded() {
		return simpleWidgetRounded;
	}

	public boolean simpleWidgetGlow() {
		return simpleWidgetGlow;
	}

	public boolean simpleOuterGlow() {
		return simpleOuterGlow;
	}

	public boolean simpleInnerGlow() {
		return simpleInnerGlow;
	}

	public void setSimpleWidgetBackground(boolean background) {
		this.simpleWidgetBackground = background;
	}

	public void setSimpleWidgetRounded(boolean rounded) {
		this.simpleWidgetRounded = rounded;
	}

	public void setSimpleWidgetGlow(boolean glow) {
		this.simpleWidgetGlow = glow;
	}

	public void setSimpleOuterGlow(boolean simpleOuterGlow) {
		this.simpleOuterGlow = simpleOuterGlow;
	}

	public void setSimpleInnerGlow(boolean simpleInnerGlow) {
		this.simpleInnerGlow = simpleInnerGlow;
	}
}