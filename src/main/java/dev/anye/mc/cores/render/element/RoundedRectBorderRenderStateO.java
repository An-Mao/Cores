package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.joml.Matrix3x2f;

import javax.annotation.Nullable;

/**
 * 圆角矩形边框 (修复重叠发黑与发光异常问题)
 * @param pipeline
 * @param textureSetup
 * @param pose
 * @param x
 * @param y
 * @param width
 * @param height
 * @param radius
 * @param borderColor
 * @param borderThickness  边框厚度 (根据你的调用新增此参数)
 * @param glow             是否渲染发光
 * @param intensity        发光强度
 * @param innerGlowRange   内部发光范围，范围小/等于0时，不渲染
 * @param outerGlowRange   外部发光范围，范围小/等于0时，不渲染
 * @param innerGlowColor   内发光颜色，未指定时(0)则从边框取值
 * @param outerGlowColor   外发光颜色，未指定时(0)则从边框取值
 * @param smoothness       平滑度
 * @param scissorArea      裁剪区域
 * @param bounds           渲染边界
 */
public record RoundedRectBorderRenderStateO(
		RenderPipeline pipeline,
		TextureSetup textureSetup,
		Matrix3x2f pose, float x, float y, float width, float height, float radius, int borderColor, float borderThickness,
		boolean glow, float intensity, float innerGlowRange, float outerGlowRange, int innerGlowColor, int outerGlowColor, float smoothness,
		@Nullable ScreenRectangle scissorArea,
		@Nullable ScreenRectangle bounds) implements GuiElementRenderState {

	@Override
	public void buildVertices(VertexConsumer vertexConsumer) {
		if (borderThickness <= 0 && (!glow || (innerGlowRange <= 0 && outerGlowRange <= 0))) return;

		// 确定发光颜色，如果未指定则使用边框色[cite: 3]
		int actualInnerGlowColor = (innerGlowColor != 0) ? innerGlowColor : borderColor;
		int actualOuterGlowColor = (outerGlowColor != 0) ? outerGlowColor : borderColor;

		// 提取完全透明的渐变边缘色（保留RGB，Alpha设为0），防止透明插值时变白/变灰
		int transparentInner = actualInnerGlowColor & 0x00FFFFFF;
		int transparentOuter = actualOuterGlowColor & 0x00FFFFFF;

		// 1. 渲染基础边框 (内外同色，无渐变)
		if (borderThickness > 0) {
			drawBox(vertexConsumer, pose, x, y, width, height, radius, borderThickness, borderColor, borderColor, smoothness);
		}

		if (glow) {
			// 2. 渲染内发光 (紧贴边框内侧，向内渐变到透明)
			if (innerGlowRange > 0) {
				float inR = Math.max(0, radius - borderThickness);
				drawBox(vertexConsumer, pose,
						x + borderThickness, y + borderThickness,
						width - borderThickness * 2, height - borderThickness * 2,
						inR, innerGlowRange,
						actualInnerGlowColor, transparentInner, smoothness); // 外边缘为实体色，内边缘为透明
			}

			// 3. 渲染外发光 (紧贴边框外侧，向外扩展并渐变到透明)
			if (outerGlowRange > 0) {
				drawBox(vertexConsumer, pose,
						x - outerGlowRange, y - outerGlowRange,
						width + outerGlowRange * 2, height + outerGlowRange * 2,
						radius + outerGlowRange, outerGlowRange,
						transparentOuter, actualOuterGlowColor, smoothness); // 外边缘为透明，内边缘为实体色
			}
		}
	}

	/**
	 * 绘制零重叠的梯形/扇形闭合边框。
	 * 该算法通过内、外半径精确计算顶点，确保圆角与直边完美拼接，彻底避免Alpha重叠导致的颜色加深。
	 */
	private void drawBox(VertexConsumer consumer, Matrix3x2f pose,
						 float x, float y, float w, float h,
						 float r, float t,
						 int outColor, int inColor, float smoothness) {
		if (t <= 0 || w <= 0 || h <= 0) return;

		float rIn = Math.max(0, r - t);

		// 限制半径不超过长宽的一半
		r = Math.min(r, Math.min(w / 2.0f, h / 2.0f));
		rIn = Math.min(rIn, Math.min((w - 2 * t) / 2.0f, (h - 2 * t) / 2.0f));
		if (rIn < 0) rIn = 0;

		// 外围圆角圆心
		float cxTL = x + r, cyTL = y + r;
		float cxTR = x + w - r, cyTR = y + r;
		float cxBR = x + w - r, cyBR = y + h - r;
		float cxBL = x + r, cyBL = y + h - r;

		// 内围圆角圆心 (如果厚度大于半径，内围圆心会与外围不同，自然形成准确的过渡弧度)
		float cxInTL = x + t + rIn, cyInTL = y + t + rIn;
		float cxInTR = x + w - t - rIn, cyInTR = y + t + rIn;
		float cxInBR = x + w - t - rIn, cyInBR = y + h - t - rIn;
		float cxInBL = x + t + rIn, cyInBL = y + h - t - rIn;

		// 绘制直边梯形 (严格遵循逆时针顶点顺序)
		// Top
		addQuad(consumer, pose,
				cxTL, y, outColor,
				cxInTL, y + t, inColor,
				cxInTR, y + t, inColor,
				cxTR, y, outColor);
		// Bottom
		addQuad(consumer, pose,
				cxInBL, y + h - t, inColor,
				cxBL, y + h, outColor,
				cxBR, y + h, outColor,
				cxInBR, y + h - t, inColor);
		// Left
		addQuad(consumer, pose,
				x, cyTL, outColor,
				x, cyBL, outColor,
				x + t, cyInBL, inColor,
				x + t, cyInTL, inColor);
		// Right
		addQuad(consumer, pose,
				x + w - t, cyInTR, inColor,
				x + w - t, cyInBR, inColor,
				x + w, cyBR, outColor,
				x + w, cyTR, outColor);

		// 绘制四个圆角扇形面[cite: 5]
		drawCornerFan(consumer, pose, cxTL, cyTL, r, cxInTL, cyInTL, rIn, Math.PI, Math.PI * 1.5, outColor, inColor, smoothness);
		drawCornerFan(consumer, pose, cxTR, cyTR, r, cxInTR, cyInTR, rIn, Math.PI * 1.5, Math.PI * 2, outColor, inColor, smoothness);
		drawCornerFan(consumer, pose, cxBR, cyBR, r, cxInBR, cyInBR, rIn, 0, Math.PI * 0.5, outColor, inColor, smoothness);
		drawCornerFan(consumer, pose, cxBL, cyBL, r, cxInBL, cyInBL, rIn, Math.PI * 0.5, Math.PI, outColor, inColor, smoothness);
	}

	private void drawCornerFan(VertexConsumer consumer, Matrix3x2f pose,
							   float cxOut, float cyOut, float rOut,
							   float cxIn, float cyIn, float rIn,
							   double startArc, double endArc,
							   int outColor, int inColor, float smoothness) {
		double arc = endArc - startArc;
		float resolution = (float) (Math.PI / 18.0) / Math.max(0.1f, smoothness);
		int segments = (int) Math.ceil(Math.abs(arc) / resolution);
		if (segments < 1) segments = 1;

		for (int i = 0; i < segments; i++) {
			double a1 = startArc + arc * ((double) i / segments);
			double a2 = startArc + arc * ((double) (i + 1) / segments);

			float out1x = cxOut + (float) (Math.cos(a1) * rOut);
			float out1y = cyOut + (float) (Math.sin(a1) * rOut);
			float in1x = cxIn + (float) (Math.cos(a1) * rIn);
			float in1y = cyIn + (float) (Math.sin(a1) * rIn);

			float out2x = cxOut + (float) (Math.cos(a2) * rOut);
			float out2y = cyOut + (float) (Math.sin(a2) * rOut);
			float in2x = cxIn + (float) (Math.cos(a2) * rIn);
			float in2y = cyIn + (float) (Math.sin(a2) * rIn);

			// 逆时针渲染四边形
			addQuad(consumer, pose, out1x, out1y, outColor, in1x, in1y, inColor, in2x, in2y, inColor, out2x, out2y, outColor);
		}
	}

	private void addQuad(VertexConsumer consumer, Matrix3x2f pose,
						 float x1, float y1, int c1,
						 float x2, float y2, int c2,
						 float x3, float y3, int c3,
						 float x4, float y4, int c4) {
		consumer.addVertexWith2DPose(pose, x1, y1).setColor(c1);
		consumer.addVertexWith2DPose(pose, x2, y2).setColor(c2);
		consumer.addVertexWith2DPose(pose, x3, y3).setColor(c3);
		consumer.addVertexWith2DPose(pose, x4, y4).setColor(c4);
	}
}