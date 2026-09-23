package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2f;

import javax.annotation.Nullable;

public class testerr {
	public record RoundedRectBorderRenderState(
			RenderPipeline pipeline,
			TextureSetup textureSetup,
			Matrix3x2f pose, float x, float y, float width, float height, float radius, int borderColor, float borderThickness,
			boolean glow, float intensity, float innerGlowRange, float outerGlowRange, int innerGlowColor, int outerGlowColor, float smoothness,
			@Nullable ScreenRectangle scissorArea,
			@Nullable ScreenRectangle bounds) implements GuiElementRenderState
	{

		public RoundedRectBorderRenderState(
				Matrix3x2f pose, float x, float y, float width, float height, float radius, int borderColor,
				boolean glow, float intensity, float innerGlowRange, float outerGlowRange, int innerGlowColor, int outerGlowColor, float smoothness,
				@Nullable ScreenRectangle scissorArea,
				@Nullable ScreenRectangle bounds){
			this(RenderPipelines.GUI, TextureSetup.noTexture(), pose, x, y, width, height, radius, borderColor, radius, glow, intensity, innerGlowRange, outerGlowRange, innerGlowColor, outerGlowColor, smoothness, scissorArea, bounds);
		}

		@Override
		public void buildVertices(VertexConsumer vertexConsumer) {
			if (borderThickness <= 0 && (!glow || (innerGlowRange <= 0 && outerGlowRange <= 0))) return;

			// 1. 确定并计算发光颜色（结合 intensity 强度调整亮度和发白程度）
			int baseInnerColor = (innerGlowColor != 0) ? innerGlowColor : borderColor;
			int baseOuterColor = (outerGlowColor != 0) ? outerGlowColor : borderColor;

			int actualInnerGlowColor = adjustColorIntensity(baseInnerColor, intensity);
			int actualOuterGlowColor = adjustColorIntensity(baseOuterColor, intensity);

			// 提取完全透明的渐变边缘色（保留RGB，Alpha设为0），防止透明插值时变灰
			int transparentInner = actualInnerGlowColor & 0x00FFFFFF;
			int transparentOuter = actualOuterGlowColor & 0x00FFFFFF;

			// 2. 自动内缩优化：如果启用了外发光，将整体坐标向内收缩，免去外部手动调整 bounds 的麻烦
			float renderX = x;
			float renderY = y;
			float renderW = width;
			float renderH = height;
			float renderRadius = radius;

			if (glow && outerGlowRange > 0) {
				renderX = x + outerGlowRange;
				renderY = y + outerGlowRange;
				renderW = Math.max(0, width - outerGlowRange * 2);
				renderH = Math.max(0, height - outerGlowRange * 2);
				renderRadius = Math.max(0, radius - outerGlowRange);
			}

			// 3. 渲染基础边框 (内外同色，无渐变)
			if (borderThickness > 0) {
				drawBox(vertexConsumer, pose, renderX, renderY, renderW, renderH, renderRadius, borderThickness, borderColor, borderColor, smoothness);
			}

			// 4. 渲染发光效果
			if (glow) {
				// 内发光 (紧贴边框内侧，向内渐变到透明)
				if (innerGlowRange > 0) {
					float inR = Math.max(0, renderRadius - borderThickness);
					drawBox(vertexConsumer, pose,
							renderX + borderThickness, renderY + borderThickness,
							renderW - borderThickness * 2, renderH - borderThickness * 2,
							inR, innerGlowRange,
							actualInnerGlowColor, transparentInner, smoothness);
				}

				// 外发光 (紧贴边框外侧，向外扩展并渐变到透明)
				if (outerGlowRange > 0) {
					drawBox(vertexConsumer, pose,
							renderX - outerGlowRange, renderY - outerGlowRange,
							renderW + outerGlowRange * 2, renderH + outerGlowRange * 2,
							renderRadius + outerGlowRange, outerGlowRange,
							transparentOuter, actualOuterGlowColor, smoothness);
				}
			}
		}

		/**
		 * 根据 intensity 调整发光颜色。
		 * 强度越高，颜色越向高亮（白色）过渡，模拟强烈的发光高光。
		 */
		private int adjustColorIntensity(int color, float intensity) {
			int a = (color >> 24) & 0xFF;
			int r = (color >> 16) & 0xFF;
			int g = (color >> 8) & 0xFF;
			int b = color & 0xFF;

			// 如果 intensity 小于等于 0，则设为默认 1.0
			float factor = intensity <= 0 ? 1.0f : intensity;

			// 强度大于 1 时，使颜色向白色(255)混合发光
			float mixWhite = Math.clamp((factor - 1.0f) * 0.6f, 0.0f, 1.0f);
			int newR = (int) Mth.lerp(mixWhite, r, 255);
			int newG = (int) Mth.lerp(mixWhite, g, 255);
			int newB = (int) Mth.lerp(mixWhite, b, 255);

			// 适当根据 intensity 提升透明度表现，确保低透明度时也能发光
			int newA = Math.min(255, (int) (a * Math.min(factor, 1.5f)));

			return (newA << 24) | (newR << 16) | (newG << 8) | newB;
		}

		/**
		 * 绘制同心圆角的闭合矩形框（严格保证内、外圆心一致，杜绝变形）
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

			// 核心修复：内层与外层圆角使用完全相同的圆心坐标（同心圆弧）
			float cxTL = x + r, cyTL = y + r;
			float cxTR = x + w - r, cyTR = y + r;
			float cxBR = x + w - r, cyBR = y + h - r;
			float cxBL = x + r, cyBL = y + h - r;

			// 绘制直边梯形
			// Top
			addQuad(consumer, pose,
					cxTL, y, outColor,
					cxTL, y + t, inColor,
					cxTR, y + t, inColor,
					cxTR, y, outColor);
			// Bottom
			addQuad(consumer, pose,
					cxBL, y + h - t, inColor,
					cxBL, y + h, outColor,
					cxBR, y + h, outColor,
					cxBR, y + h - t, inColor);
			// Left
			addQuad(consumer, pose,
					x, cyTL, outColor,
					x, cyBL, outColor,
					x + t, cyBL, inColor,
					x + t, cyTL, inColor);
			// Right
			addQuad(consumer, pose,
					x + w - t, cyTR, inColor,
					x + w - t, cyBR, inColor,
					x + w, cyBR, outColor,
					x + w, cyTR, outColor);

			// 绘制四个同心圆角扇形面[cite: 5]
			drawCornerFan(consumer, pose, cxTL,cyTL, r, rIn, Math.PI, Math.PI * 1.5, outColor, inColor, smoothness);
			drawCornerFan(consumer, pose, cxTR,cyTR, r, rIn, Math.PI * 1.5, Math.PI * 2.0, outColor, inColor, smoothness);
			drawCornerFan(consumer, pose, cxBR,cyBR, r, rIn, 0, Math.PI * 0.5, outColor, inColor, smoothness);
			drawCornerFan(consumer, pose, cxBL,cyBL, r, rIn, Math.PI * 0.5, Math.PI, outColor, inColor, smoothness);
		}

		private void drawCornerFan(VertexConsumer consumer, Matrix3x2f pose,
								   float cx, float cy, float rOut, float rIn,
								   double startArc, double endArc,
								   int outColor, int inColor, float smoothness) {
			double arc = endArc - startArc;
			float resolution = (float) (Math.PI / 18.0) / Math.max(0.1f, smoothness);
			int segments = (int) Math.ceil(Math.abs(arc) / resolution);
			if (segments < 1) segments = 1;

			for (int i = 0; i < segments; i++) {
				double a1 = startArc + arc * ((double) i / segments);
				double a2 = startArc + arc * ((double) (i + 1) / segments);

				float out1x = cx + (float) (Math.cos(a1) * rOut);
				float out1y = cy + (float) (Math.sin(a1) * rOut);
				float in1x  = cx + (float) (Math.cos(a1) * rIn);
				float in1y  = cy + (float) (Math.sin(a1) * rIn);

				float out2x = cx + (float) (Math.cos(a2) * rOut);
				float out2y = cy + (float) (Math.sin(a2) * rOut);
				float in2x  = cx + (float) (Math.cos(a2) * rIn);
				float in2y  = cy + (float) (Math.sin(a2) * rIn);

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

}
