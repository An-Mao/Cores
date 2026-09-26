package dev.anye.mc.cores.dt;

import dev.anye.core.color._ColorSupport;

/**
 * 发光数据
 * @param intensity        发光强度
 * @param smoothness       平滑度
 * @param innerGlowRange   内部发光范围，范围小/等于0时，不渲染
 * @param innerGlowColor   内发光颜色，未指定时(0)则从边框取值
 * @param outerGlowRange   外部发光范围，范围小/等于0时，不渲染
 * @param outerGlowColor   外发光颜色，未指定时(0)则从边框取值
 */
public record GlowData(
		float intensity, float smoothness,
		float innerGlowRange,
		FadeColorData innerGlowColor,

		float outerGlowRange,
		FadeColorData outerGlowColor
) {
	public static Builder Builder(){
		return new Builder();
	}
	public static class Builder {
		float intensity;
		float smoothness;

		float innerGlowRange;
		FadeColorData innerGlowColor;

		float outerGlowRange;
		FadeColorData outerGlowColor;
		public Builder() {
			this.intensity = 0.5F;
			this.smoothness = 1.0F;
			this.innerGlowRange = 3F;
			this.innerGlowColor = new FadeColorData(0x00000000,0x00000000,0xffffffff,0xffffffff);

			this.outerGlowRange = 3F;
			this.outerGlowColor = new FadeColorData(0x00000000,0x00000000,0xffffffff,0xffffffff);
		}

		public GlowData build(){
			return new GlowData(intensity,smoothness,innerGlowRange,innerGlowColor,outerGlowRange,outerGlowColor);
		}

		public Builder setIntensity(float intensity) {
			this.intensity = intensity;
			return this;
		}

		public Builder setSmoothness(float smoothness) {
			this.smoothness = smoothness;
			return this;
		}

		public Builder setInnerGlowRange(float innerGlowRange) {
			this.innerGlowRange = innerGlowRange;
			return this;
		}

		public Builder setInnerGlowColor(int innerGlowStartColor,int innerGlowEndColor) {
			this.innerGlowColor = new FadeColorData(innerGlowStartColor,innerGlowStartColor,innerGlowEndColor,innerGlowEndColor);
			return this;
		}

		public Builder setOuterGlowRange(float outerGlowRange) {
			this.outerGlowRange = outerGlowRange;
			return this;
		}

		public Builder setOuterGlowColor(int outerGlowStartColor,int outerGlowEndColor) {
			this.outerGlowColor = new FadeColorData(outerGlowStartColor,outerGlowStartColor,outerGlowEndColor,outerGlowEndColor);
			return this;
		}
		public Builder setColor(int color,boolean alpha){
			int c = _ColorSupport.fade(color,intensity,alpha);
			int e = c & 0x00FFFFFF;
			setInnerGlowColor(c,e);
			setOuterGlowColor(c,e);
			return this;
		}
		public Builder setColor(int color){
			return setColor(color,false);
		}

	}
}
