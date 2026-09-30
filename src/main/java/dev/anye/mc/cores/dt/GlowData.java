package dev.anye.mc.cores.dt;

import dev.anye.core.color._ColorSupport;

public class GlowData {
	boolean enable = false;
	/**
	 * 发光强度
	 */
	float intensity = 0.5F;
	/**
	 * 平滑度
	 */
	float smoothness = 1.0F;
	/**
	 * 内部发光范围，范围小/等于0时，不渲染
	 */
	float innerGlowRange = 3F;
	/**
	 * 内发光颜色，未指定时(0)则从边框取值
	 */
	FadeColorData innerGlowColor = FadeColorData.EMPTY;
	/**
	 * 外部发光范围，范围小/等于0时，不渲染
	 */
	float outerGlowRange = 3F;
	/**
	 * 外发光颜色，未指定时(0)则从边框取值
	 */
	FadeColorData outerGlowColor = FadeColorData.EMPTY;

	public boolean enable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public float intensity() {
		return intensity;
	}
	public float smoothness() {
		return smoothness;
	}
	public float innerGlowRange() {
		return innerGlowRange;
	}
	public FadeColorData innerGlowColor() {
		return innerGlowColor;
	}
	public float outerGlowRange() {
		return outerGlowRange;
	}
	public FadeColorData outerGlowColor() {
		return outerGlowColor;
	}

	public GlowData setIntensity(float intensity) {
		this.intensity = intensity;
		return this;
	}

	public GlowData setSmoothness(float smoothness) {
		this.smoothness = smoothness;
		return this;
	}

	public GlowData setInnerGlowRange(float innerGlowRange) {
		this.innerGlowRange = innerGlowRange;
		return this;
	}

	public GlowData setInnerGlowColor(FadeColorData innerGlowColor){
		this.innerGlowColor = innerGlowColor;
		return this;
	}
	public GlowData setInnerGlowColor(int innerGlowStartColor, int innerGlowEndColor) {
		return setInnerGlowColor(new FadeColorData(innerGlowStartColor, innerGlowStartColor, innerGlowEndColor, innerGlowEndColor));
	}

	public GlowData setOuterGlowRange(float outerGlowRange) {
		this.outerGlowRange = outerGlowRange;
		return this;
	}

	public GlowData setOuterGlowColor(FadeColorData outerGlowColor) {
		this.outerGlowColor = outerGlowColor;
		return this;
	}
	public GlowData setOuterGlowColor(int outerGlowStartColor, int outerGlowEndColor) {
		return setOuterGlowColor(new FadeColorData(outerGlowStartColor, outerGlowStartColor, outerGlowEndColor, outerGlowEndColor));
	}

	public GlowData setColor(int color, boolean alpha) {
		int c = _ColorSupport.fade(color, intensity, alpha);
		int e = c & 0x00FFFFFF;
		setInnerGlowColor(c, e);
		setOuterGlowColor(c, e);
		return this;
	}

	public GlowData setColor(int color) {
		return setColor(color, false);
	}
}
