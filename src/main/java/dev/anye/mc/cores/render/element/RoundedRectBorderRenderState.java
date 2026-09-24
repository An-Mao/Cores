package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import dev.anye.core.math._MathCDT;
import dev.anye.mc.cores.dt.GlowData;
import dev.anye.mc.cores.render.GuiGraphicsX;
import dev.anye.mc.cores.render.Render2DHelper;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/**
 * 带发光的圆角矩形边框
 * @param pipeline
 * @param textureSetup
 * @param pose
 * @param width
 * @param height
 * @param radius
 * @param borderColor
 * @param borderThickness  边框厚度 (根据你的调用新增此参数)
 * @param scissorArea      裁剪区域
 * @param bounds           渲染边界
 */
public record RoundedRectBorderRenderState(
		RenderPipeline pipeline,
		TextureSetup textureSetup,
		Matrix3x2fStack pose, float width, float height, float radius, int borderColor, float borderThickness,
		GlowData glowData,
		@Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) implements GuiElementRenderState {
	private static final Logger LOGGER = LogUtils.getLogger();
			
	public RoundedRectBorderRenderState(
			Matrix3x2fStack pose,float width, float height, float radius, int borderColor,
			float borderThickness,
			@Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) {
		this(RenderPipelines.GUI, TextureSetup.noTexture(), pose, width, height, radius, borderColor, borderThickness,new GlowData.Builder().setColor(borderColor).build(), scissorArea, bounds);
	}





	@Override
	public void buildVertices(@NonNull VertexConsumer vertexConsumer) {
		if ( width < 0 || height < 0) return;

		/*if (glowData != null && glowData.outerGlowRange() > 0) {
			drawBox(vertexConsumer, pose,
					0, 0,
					width + glowData.outerGlowRange() * 2, height + glowData.outerGlowRange() * 2,
					radius + glowData.outerGlowRange(), glowData.outerGlowRange(), glowData().outerGlowColor(), glowData().outerGlowColor(), glowData().smoothness());
		}*/

		float borderX = glowData != null ? glowData.outerGlowColor() : 0;
		float borderY = glowData != null ? glowData.outerGlowColor() : 0;
		float w = width - borderX * 2;
		float h = height - borderY * 2;

		if (borderThickness > 0) {
			pose.pushMatrix();
			pose.translate(borderX,borderY);
			Render2DHelper.border(vertexConsumer,pose,w,h,radius,borderColor);
//
//			Render2DHelper.fan(vertexConsumer,pose,_MathCDT.ARC_180, _MathCDT.ARC_270,borderColor,radius);
//			Render2DHelper.fan(vertexConsumer,pose,_MathCDT.ARC_270, _MathCDT.ARC_360,borderColor,radius);
//			Render2DHelper.fan(vertexConsumer,pose,0, _MathCDT.ARC_90,borderColor,radius);
//			Render2DHelper.fan(vertexConsumer,pose,_MathCDT.ARC_90, _MathCDT.ARC_180,borderColor,radius);
			pose.popMatrix();
		}

		/*if (glowData != null && glowData.innerGlowRange() > 0) {
			float inR = Math.max(0, radius - borderThickness);
			drawBox(vertexConsumer, pose,
					borderX + borderThickness, borderY + borderThickness,
					width - borderThickness * 2, height - borderThickness * 2,
					inR, glowData.innerGlowRange(),
					glowData.innerGlowColor(), glowData.innerGlowColor(), glowData.smoothness()); // 外边缘为实体色，内边缘为透明
		}*/
	}

	/**
	 * 绘制零重叠的梯形/扇形闭合边框。
	 * 该算法通过内、外半径精确计算顶点，确保圆角与直边完美拼接，彻底避免Alpha重叠导致的颜色加深。
	 */
	private void drawBox(VertexConsumer vertexConsumer, Matrix3x2fStack pose,
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


		// 绘制四个圆角扇形面[cite: 5]
		drawCornerFan(vertexConsumer, pose, cxTL, cyTL, r, cxInTL, cyInTL, rIn, Math.PI, Math.PI * 1.5, outColor, inColor, smoothness);
		drawCornerFan(vertexConsumer, pose, cxTR, cyTR, r, cxInTR, cyInTR, rIn, Math.PI * 1.5, Math.PI * 2, outColor, inColor, smoothness);
		drawCornerFan(vertexConsumer, pose, cxBR, cyBR, r, cxInBR, cyInBR, rIn, 0, Math.PI * 0.5, outColor, inColor, smoothness);
		drawCornerFan(vertexConsumer, pose, cxBL, cyBL, r, cxInBL, cyInBL, rIn, Math.PI * 0.5, Math.PI, outColor, inColor, smoothness);
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