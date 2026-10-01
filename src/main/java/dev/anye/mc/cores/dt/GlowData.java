package dev.anye.mc.cores.dt;

import dev.anye.core.color._ColorSupport;
import org.jetbrains.annotations.Contract;

public final class GlowData {
	boolean enable = false;
	boolean inner = true;
	boolean outer = true;

	/**
	 * 发光强度
	 */
	float intensity = 2F;
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
	public GlowData setEnable(boolean enable) {
		this.enable = enable;
		return this;
	}

	public boolean inner() {
		return inner;
	}
	public GlowData setInner(boolean inner) {
		this.inner = inner;
		return this;
	}

	public boolean outer() {
		return outer;
	}

	public GlowData setOuter(boolean outer) {
		this.outer = outer;
		return this;
	}

	public float intensity() {
		return intensity;
	}
	public float smoothness() {
		return smoothness;
	}
	public float innerGlowRange() {
		return inner ? innerGlowRange : 0;
	}
	public FadeColorData innerGlowColor() {
		return innerGlowColor;
	}
	public float outerGlowRange() {
		return outer ? outerGlowRange : 0;
	}
	public FadeColorData outerGlowColor() {
		return outerGlowColor;
	}

	@Contract(value = "_ -> this", mutates = "this")
	public GlowData setIntensity(float intensity) {
		this.intensity = intensity;
		return this;
	}

	@Contract(value = "_ -> this", mutates = "this")
	public GlowData setSmoothness(float smoothness) {
		this.smoothness = smoothness;
		return this;
	}

	@Contract(value = "_ -> this", mutates = "this")
	public GlowData setInnerGlowRange(float innerGlowRange) {
		this.innerGlowRange = innerGlowRange;
		return this;
	}

	@Contract(value = "_ -> this", mutates = "this")
	public GlowData setInnerGlowColor(FadeColorData innerGlowColor){
		this.innerGlowColor = innerGlowColor;
		return this;
	}

	@Contract(value = "_,_ -> this", mutates = "this")
	public GlowData setInnerGlowColor(int innerGlowStartColor, int innerGlowEndColor) {
		return setInnerGlowColor(new FadeColorData(innerGlowStartColor, innerGlowStartColor, innerGlowEndColor, innerGlowEndColor));
	}

	@Contract(value = "_ -> this", mutates = "this")
	public GlowData setOuterGlowRange(float outerGlowRange) {
		this.outerGlowRange = outerGlowRange;
		return this;
	}

	@Contract(value = "_ -> this", mutates = "this")
	public GlowData setOuterGlowColor(FadeColorData outerGlowColor) {
		this.outerGlowColor = outerGlowColor;
		return this;
	}

	@Contract(value = "_,_ -> this", mutates = "this")
	public GlowData setOuterGlowColor(int outerGlowStartColor, int outerGlowEndColor) {
		return setOuterGlowColor(new FadeColorData(outerGlowStartColor, outerGlowStartColor, outerGlowEndColor, outerGlowEndColor));
	}

	@Contract(value = "_,_ -> this", mutates = "this")
	public GlowData setColor(int color, boolean alpha) {
		int c = _ColorSupport.fade(color, intensity, alpha);
		int e = c & 0x00FFFFFF;
		setInnerGlowColor(c, e);
		setOuterGlowColor(c, e);
		return this;
	}

	@Contract(value = "_ -> this", mutates = "this")
	public GlowData setColor(int color) {
		return setColor(color, false);
	}

	@Contract(value = "_ -> this", mutates = "this")
	public GlowData copyFrom(GlowData glowData){
		this.enable = glowData.enable;
		intensity = glowData.intensity;
		smoothness = glowData.smoothness;
		innerGlowRange = glowData.innerGlowRange;
		innerGlowColor = glowData.innerGlowColor;
		outerGlowRange = glowData.outerGlowRange;
		outerGlowColor = glowData.outerGlowColor;
		return this;
	}
}
