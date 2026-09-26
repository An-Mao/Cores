package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.joml.Matrix3x2f;

import javax.annotation.Nullable;

/**
 * 圆角矩形边框。
 *
 * <p>
 * 特点：
 * <ul>
 * <li>主体边框颜色始终保持 borderColor 原值</li>
 * <li>外发光/内发光使用连续圆角环带</li>
 * <li>所有位置均使用 float</li>
 * <li>发光渐变只改变 Alpha，不修改 RGB</li>
 * <li>未指定发光颜色时，从 borderColor 派生</li>
 * <li>intensity 只控制派生颜色向白色的偏移</li>
 * <li>显式指定 innerGlowColor / outerGlowColor 时不进行白化</li>
 * <li>主体边框最后绘制，避免发光污染边框</li>
 * <li>内发光保留最小圆角，避免最终退化成直角</li>
 * </ul>
 */
public record RoundedRectBorderRenderStateA(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2f pose,
											float x, float y, float width, float height, float radius, int borderColor,
											float borderThickness, boolean glow, float intensity, float innerGlowRange,
											float outerGlowRange, int innerGlowColor, int outerGlowColor,
											float smoothness, @Nullable ScreenRectangle scissorArea,
											@Nullable ScreenRectangle bounds) implements GuiElementRenderState {
	private static final float EPSILON = 0.0001F;
	/**
	 * 默认边框宽度。
	 */
	private static final float DEFAULT_BORDER_THICKNESS = 1.0F;
	/**
	 * 发光最少分段。
	 */
	private static final int MIN_ARC_SEGMENTS = 6;

	/**
	 * 发光最多分段。
	 */
	private static final int MAX_ARC_SEGMENTS = 48;

	/**
	 * 发光渐变最少层。
	 */
	private static final int MIN_GLOW_BANDS = 2;

	/**
	 * 发光渐变最多层。
	 *
	 * <p>
	 * 采用不重叠的相邻环带，因此不需要几十层。
	 */
	private static final int MAX_GLOW_BANDS = 8;

	/**
	 * 内发光圆角最小保留值。
	 *
	 * <p>
	 * 防止内发光逐渐侵蚀到完全直角。
	 */
	private static final float MIN_INNER_CORNER_RADIUS = 0.75F;

	public RoundedRectBorderRenderStateA(Matrix3x2f pose, float x, float y, float width, float height, float radius, int borderColor, boolean glow, float intensity, float innerGlowRange, float outerGlowRange, int innerGlowColor, int outerGlowColor, float smoothness, @Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) {
		this(RenderPipelines.GUI, TextureSetup.noTexture(), pose, x, y, width, height, radius, borderColor, DEFAULT_BORDER_THICKNESS, glow, intensity, innerGlowRange, outerGlowRange, innerGlowColor, outerGlowColor, smoothness, scissorArea, bounds);
	}

	@Override
	public void buildVertices(VertexConsumer vertexConsumer) {
		if (width <= EPSILON || height <= EPSILON) {
			return;
		}
		final float safeWidth = width;
		final float safeHeight = height;
		/*
		 * 基础圆角。
		 */
		final float maxRadius = Math.min(safeWidth, safeHeight) * 0.5F;

		final float safeRadius = clamp(radius, 0.0F, maxRadius);
		/*
		 * 边框厚度。
		 */
		final float maxThickness = Math.min(safeWidth, safeHeight) * 0.5F;

		final float safeThickness = clamp(borderThickness, 0.0F, maxThickness);

		/*
		 * intensity 只负责 glowColor 派生颜色。
		 */
		final float safeIntensity = clamp(intensity, 0.0F, 1.0F);

		/*
		 * smoothness。
		 */
		final float safeSmoothness = clamp(smoothness, 0.0F, 1.0F);

		/*
		 * --------------------------------------------------------------
		 * 1. 先解析发光颜色
		 * --------------------------------------------------------------
		 */

		final int actualInnerGlowColor = resolveGlowColor(innerGlowColor, borderColor, safeIntensity);

		final int actualOuterGlowColor = resolveGlowColor(outerGlowColor, borderColor, safeIntensity);

		/*
		 * --------------------------------------------------------------
		 * 2. 外发光
		 *
		 * 必须最先绘制。
		 * --------------------------------------------------------------
		 */

		if (glow && outerGlowRange > EPSILON) {
			drawOuterGlow(vertexConsumer, safeRadius, Math.max(0.0F, outerGlowRange), actualOuterGlowColor, safeSmoothness);
		}

		/*
		 * --------------------------------------------------------------
		 * 3. 内发光
		 * --------------------------------------------------------------
		 */

		if (glow && innerGlowRange > EPSILON && safeThickness < maxThickness) {
			drawInnerGlow(vertexConsumer, safeRadius, safeThickness, Math.max(0.0F, innerGlowRange), actualInnerGlowColor, safeSmoothness);
		}

		/*
		 * --------------------------------------------------------------
		 * 4. 主体边框
		 *
		 * 最后绘制。
		 *
		 * 这是保证：
		 *
		 * borderColor 永远不受 glow 影响
		 *
		 * 的关键。
		 * --------------------------------------------------------------
		 */

		if (safeThickness > EPSILON) {

			drawBorder(vertexConsumer,

					safeRadius, safeThickness,

					borderColor,

					safeSmoothness);
		}
	}

	/*
	 * ------------------------------------------------------------------
	 * 主体边框
	 * ------------------------------------------------------------------
	 */

	private void drawBorder(VertexConsumer consumer,

							float radius, float thickness,

							int color,

							float smoothness) {
		/*
		 * 外边界：
		 *
		 * inset = 0
		 */
		final float outerInset = 0.0F;

		/*
		 * 内边界：
		 *
		 * inset = thickness
		 */
		final float innerInset = thickness;

		/*
		 * 圆角边框的正确内半径：
		 *
		 * R_in = max(R - thickness, 0)
		 */
		final float outerRadius = radius;

		final float innerRadius = Math.max(0.0F, radius - thickness);

		drawRoundedRing(consumer,

				outerInset, innerInset,

				outerRadius, innerRadius,

				color, color,

				smoothness);
	}

	/*
	 * ------------------------------------------------------------------
	 * 外发光
	 * ------------------------------------------------------------------
	 */

	private void drawOuterGlow(VertexConsumer consumer, float radius, float range, int color, float smoothness) {
		if (range <= EPSILON) return;
		final int bands = getGlowBandCount(range, smoothness);
		for (int i = 0; i < bands; i++) {
			/*
			 * 0 = 靠近边框
			 * 1 = 最外侧
			 */
			final float t0 = (float) i / bands;
			final float t1 = (float) (i + 1) / bands;
			/*
			 * 外轮廓。
			 */
			final float outerInset = -range * t1;
			/*
			 * 内轮廓。
			 */
			final float innerInset = -range * t0;
			/*
			 * Alpha 从内向外衰减。
			 */
			final float alphaInner = glowFalloff(t0, smoothness);

			final float alphaOuter = glowFalloff(t1, smoothness);

			/*
			 * 只改变 Alpha。
			 *
			 * RGB 保持原始 glowColor。
			 */
			final int innerColor = withAlpha(color, alphaInner);

			final int outerColor = withAlpha(color, alphaOuter);

			/*
			 * 对圆角矩形做 offset。
			 *
			 * 外扩 d：
			 *
			 * radius' = radius + d
			 *
			 * 这样圆角会向外自然扩散。
			 */
			final float outerRadius = radius - outerInset;
			final float innerRadius = radius - innerInset;

			drawRoundedRing(consumer, outerInset, innerInset, outerRadius, innerRadius, outerColor, innerColor, smoothness);
		}
	}

	/*
	 * ------------------------------------------------------------------
	 * 内发光
	 * ------------------------------------------------------------------
	 */

	private void drawInnerGlow(VertexConsumer consumer, float radius, float borderThickness, float requestedRange, int color, float smoothness) {
		if (requestedRange <= EPSILON) {
			return;
		}
		/*
		 * 内边框当前圆角半径。
		 */
		final float borderInnerRadius = Math.max(0.0F, radius - borderThickness);

		/*
		 * 如果这里本身已经没有圆角，
		 * 那么内部继续做圆角发光已经没有几何意义。
		 */
		if (borderInnerRadius <= EPSILON) {
			return;
		}

		/*
		 * 保留一个最小圆角。
		 *
		 * 这样不会出现：
		 *
		 * ╭──────╮
		 * │ │
		 * ╰──────╯
		 *
		 * 在向内扩散后突然变成：
		 *
		 * ┌──────┐
		 * │ │
		 * └──────┘
		 */
		final float minRadius = Math.min(MIN_INNER_CORNER_RADIUS, borderInnerRadius * 0.35F);

		/*
		 * 最多只能向内侵蚀到 minRadius。
		 */
		final float maxAdditionalRange = Math.max(0.0F, borderInnerRadius - minRadius);

		final float actualRange = Math.min(requestedRange, maxAdditionalRange);

		if (actualRange <= EPSILON) {
			return;
		}

		final int bands = getGlowBandCount(actualRange, smoothness);

		for (int i = 0; i < bands; i++) {
			final float t0 = (float) i / bands;
			final float t1 = (float) (i + 1) / bands;

			/*
			 * 内发光从边框内缘开始。
			 */
			final float outerInset = borderThickness + actualRange * t0;

			final float innerInset = borderThickness + actualRange * t1;

			/*
			 * 对应的圆角半径。
			 *
			 * 注意：
			 *
			 * radius - inset
			 *
			 * 是真正的 offset geometry。
			 */
			final float outerRadius = radius - outerInset;

			final float innerRadius = radius - innerInset;

			/*
			 * 内发光 Alpha。
			 */
			final float alphaOuter = glowFalloff(t0, smoothness);

			final float alphaInner = glowFalloff(t1, smoothness);

			final int outerColor = withAlpha(color, alphaOuter);

			final int innerColor = withAlpha(color, alphaInner);

			drawRoundedRing(consumer, outerInset, innerInset, outerRadius, innerRadius, outerColor, innerColor, smoothness);
		}
	}

	/*
	 * ------------------------------------------------------------------
	 * 连续圆角环
	 * ------------------------------------------------------------------
	 */

	private void drawRoundedRing(VertexConsumer consumer, float outerInset, float innerInset, float outerRadius, float innerRadius, int outerColor, int innerColor, float smoothness) {
		final float outerWidth = width - outerInset * 2.0F;
		final float outerHeight = height - outerInset * 2.0F;
		final float innerWidth = width - innerInset * 2.0F;
		final float innerHeight = height - innerInset * 2.0F;

		if (outerWidth <= EPSILON || outerHeight <= EPSILON) return;
		if (innerWidth <= EPSILON || innerHeight <= EPSILON) return;
		/*
		 * 安全半径。
		 */
		final float safeOuterRadius = clamp(outerRadius, 0.0F, Math.min(outerWidth, outerHeight) * 0.5F);
		final float safeInnerRadius = clamp(innerRadius, 0.0F, Math.min(innerWidth, innerHeight) * 0.5F);

		/*
		 * 根据最大的圆角决定细分。
		 */
		final int arcSegments = getArcSegments(Math.max(safeOuterRadius, safeInnerRadius), smoothness);

		/*
		 * 一个完整路径由：
		 *
		 * 4 条直线
		 * +
		 * 4 个圆弧
		 *
		 * 组成。
		 *
		 * 每一个 quad 只覆盖相邻路径段之间的环带。
		 *
		 * 因此：
		 *
		 * 不重叠
		 * 不重复覆盖
		 * 不累计 Alpha
		 */
		final int pointCount = 8 + arcSegments * 4;

		final float[] outerX = new float[pointCount];

		final float[] outerY = new float[pointCount];

		final float[] innerX = new float[pointCount];

		final float[] innerY = new float[pointCount];

		buildRoundedPath(outerX, outerY, outerInset, safeOuterRadius, arcSegments);

		buildRoundedPath(innerX, innerY, innerInset, safeInnerRadius, arcSegments);

		/*
		 * 逐边生成环带。
		 */
		for (int i = 0; i < pointCount; i++) {
			final int next = (i + 1 == pointCount) ? 0 : i + 1;
			addQuad(consumer,
					outerX[i], outerY[i], outerColor,
					innerX[i], innerY[i], innerColor,
					innerX[next], innerY[next], innerColor,
					outerX[next], outerY[next], outerColor);
		}
	}

	/*
	 * ------------------------------------------------------------------
	 * 构造圆角矩形路径
	 * ------------------------------------------------------------------
	 */

	private void buildRoundedPath(float[] outX, float[] outY, float inset, float cornerRadius, int arcSegments) {
		final float left = x + inset;
		final float top = y + inset;
		final float right = x + width - inset;
		final float bottom = y + height - inset;

		final float r = clamp(cornerRadius, 0.0F, Math.min(right - left, bottom - top) * 0.5F);

		final float ctlX = left + r;
		final float ctlY = top + r;

		final float ctrX = right - r;
		final float ctrY = top + r;

		final float cbrX = right - r;
		final float cbrY = bottom - r;

		final float cblX = left + r;
		final float cblY = bottom - r;

		int index = 0;

		// Top-left -> Top-right
		outX[index] = ctlX;
		outY[index] = top;
		index++;

		outX[index] = ctrX;
		outY[index] = top;
		index++;

		// Top-right arc: -90 -> 0
		for (int i = 1; i <= arcSegments; i++) {
			final float t = (float) i / arcSegments;
			final double a = -Math.PI * 0.5D + Math.PI * 0.5D * t;

			outX[index] = ctrX + r * (float) Math.cos(a);
			outY[index] = ctrY + r * (float) Math.sin(a);
			index++;
		}

		// Right
		outX[index] = right;
		outY[index] = cbrY;
		index++;

		// Bottom-right arc: 0 -> 90
		for (int i = 1; i <= arcSegments; i++) {
			final float t = (float) i / arcSegments;
			final double a = Math.PI * 0.5D * t;

			outX[index] = cbrX + r * (float) Math.cos(a);
			outY[index] = cbrY + r * (float) Math.sin(a);
			index++;
		}

		// Bottom
		outX[index] = cblX;
		outY[index] = bottom;
		index++;

		// Bottom-left arc: 90 -> 180
		for (int i = 1; i <= arcSegments; i++) {
			final float t = (float) i / arcSegments;
			final double a = Math.PI * 0.5D + Math.PI * 0.5D * t;

			outX[index] = cblX + r * (float) Math.cos(a);
			outY[index] = cblY + r * (float) Math.sin(a);
			index++;
		}

		// Left
		outX[index] = left;
		outY[index] = ctlY;
		index++;

		// Top-left arc: 180 -> 270
		for (int i = 1; i <= arcSegments; i++) {
			final float t = (float) i / arcSegments;
			final double a = Math.PI + Math.PI * 0.5D * t;

			outX[index] = ctlX + r * (float) Math.cos(a);
			outY[index] = ctlY + r * (float) Math.sin(a);
			index++;
		}
	}

	/*
	 * ------------------------------------------------------------------
	 * 发光颜色解析
	 * ------------------------------------------------------------------
	 */

	private static int resolveGlowColor(int configuredColor, int borderColor, float intensity) {

		/*
		 * 显式指定颜色：
		 *
		 * 完全尊重用户配置。
		 *
		 * intensity 不修改它。
		 */
		if (configuredColor != 0) {
			return configuredColor;
		}

		/*
		 * 未指定：
		 *
		 * 从 borderColor 获取。
		 */
		final int source = borderColor;

		final int alpha = source & 0xFF000000;

		final int red = (source >>> 16) & 0xFF;

		final int green = (source >>> 8) & 0xFF;

		final int blue = source & 0xFF;

		/*
		 * intensity：
		 *
		 * 0 -> 原始颜色
		 * 1 -> 白色
		 */
		final int outRed = lerp(red, 255, intensity);

		final int outGreen = lerp(green, 255, intensity);

		final int outBlue = lerp(blue, 255, intensity);

		/*
		 * 最重要：
		 *
		 * Alpha 完全来自 borderColor。
		 *
		 * 不进行任何白化。
		 */
		return alpha | (outRed << 16) | (outGreen << 8) | outBlue;
	}

	private static int lerp(int a, int b, float t) {
		return Math.round(a + (b - a) * clamp(t, 0.0F, 1.0F));
	}

	/*
	 * ------------------------------------------------------------------
	 * Alpha
	 * ------------------------------------------------------------------
	 */

	private static int withAlpha(int color, float factor) {

		factor = clamp(factor, 0.0F, 1.0F);

		final int sourceAlpha = (color >>> 24) & 0xFF;

		final int alpha = Math.round(sourceAlpha * factor);

		/*
		 * RGB 一字不动。
		 */
		return (alpha << 24) | (color & 0x00FFFFFF);
	}

	/*
	 * ------------------------------------------------------------------
	 * 发光衰减
	 * ------------------------------------------------------------------
	 */

	private static float glowFalloff(float t, float smoothness) {

		t = clamp(t, 0.0F, 1.0F);

		/*
		 * smoothstep：
		 *
		 * 0 -> 0
		 * 1 -> 1
		 */
		final float s = t * t * (3.0F - 2.0F * t);

		/*
		 * smoothness 越高，
		 * 光晕会越集中在边缘附近。
		 */
		final float power = 1.5F + smoothness * 1.5F;

		return (float) Math.pow(1.0F - s, power);
	}

	/*
	 * ------------------------------------------------------------------
	 * 发光层数
	 * ------------------------------------------------------------------
	 */

	private static int getGlowBandCount(float range, float smoothness) {

		if (range <= EPSILON) {
			return MIN_GLOW_BANDS;
		}

		/*
		 * 范围越大，层数越多。
		 *
		 * smoothness 再额外提高一点。
		 */
		final int bands = 2 + (int) Math.ceil(Math.min(range * 0.5F, 4.0F)) + Math.round(smoothness * 2.0F);

		return clampInt(bands, MIN_GLOW_BANDS, MAX_GLOW_BANDS);
	}

	/*
	 * ------------------------------------------------------------------
	 * 圆角细分
	 * ------------------------------------------------------------------
	 */

	private static int getArcSegments(float radius, float smoothness) {

		if (radius <= EPSILON) {
			return 1;
		}

		/*
		 * 半径越大，需要越多圆弧点。
		 *
		 * smoothness 再提高细分。
		 */
		final int segments = 6 + Math.round(radius * 0.45F) + Math.round(smoothness * 14.0F);

		return clampInt(segments, MIN_ARC_SEGMENTS, MAX_ARC_SEGMENTS);
	}

	/*
	 * ------------------------------------------------------------------
	 * Quad
	 * ------------------------------------------------------------------
	 */

	private void addQuad(VertexConsumer consumer,

						 float x1, float y1, int c1,

						 float x2, float y2, int c2,

						 float x3, float y3, int c3,

						 float x4, float y4, int c4) {

		consumer.addVertexWith2DPose(pose, x1, y1).setColor(c1);

		consumer.addVertexWith2DPose(pose, x2, y2).setColor(c2);

		consumer.addVertexWith2DPose(pose, x3, y3).setColor(c3);

		consumer.addVertexWith2DPose(pose, x4, y4).setColor(c4);
	}

	/*
	 * ------------------------------------------------------------------
	 * 工具
	 * ------------------------------------------------------------------
	 */

	private static float clamp(float value, float min, float max) {
		return Math.max(min, Math.min(max, value));
	}

	private static int clampInt(int value, int min, int max) {
		return Math.max(min, Math.min(max, value));
	}
}