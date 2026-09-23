package dev.anye.mc.cores.dt;

import dev.anye.core.color._ColorCDT;
import dev.anye.core.color._ColorSupport;

import java.awt.*;

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
		float innerGlowRange, int innerGlowColor,
		float outerGlowRange, int outerGlowColor
) {
	public static class Builder {
		float intensity;
		float smoothness;

		float innerGlowRange;
		int innerGlowColor;

		float outerGlowRange;
		int outerGlowColor;

		public Builder() {
			this.intensity = 0.5F;
			this.smoothness = 1.0F;
			this.innerGlowRange = 3F;
			this.innerGlowColor = 0xFFFFFFFF;
			this.outerGlowRange = 3F;
			this.outerGlowColor = 0xFFFFFFFF;
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

		public Builder setInnerGlowColor(int innerGlowColor) {
			this.innerGlowColor = innerGlowColor;
			return this;
		}

		public Builder setOuterGlowRange(float outerGlowRange) {
			this.outerGlowRange = outerGlowRange;
			return this;
		}

		public Builder setOuterGlowColor(int outerGlowColor) {
			this.outerGlowColor = outerGlowColor;
			return this;
		}
		public Builder setColor(int color,float ratio,boolean alpha){
			int c = _ColorSupport.fade(color,ratio,alpha);
			setInnerGlowColor(c);
			setOuterGlowColor(c);
			return this;
		}
		public Builder setColor(int color){
			return setColor(color,0.5F,false);
		}
	}
}
