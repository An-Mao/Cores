package dev.anye.mc.cores.screen.widget.simple;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.render.GuiGraphicsX;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;


public abstract class SimpleWidgetCoreNX<T extends SimpleWidgetCoreNX<T>> extends RenderWidgetCore<T> {
	protected int radius;
	protected int borderUsualColor,
			borderHoverColor,
			borderSelectColor;
	protected int contentX,
			contentY,
			contentW,
			contentH,
			contentEndX,
			contentEndY;

	/**
	 * 边框发光配置。
	 *
	 * 默认配置：
	 * enabled      = false
	 * intensity    = 0.65f
	 * range        = 6px
	 * innerGlow    = true
	 * outerGlow    = true
	 * preferredColor = null（跟随当前边框颜色）
	 *
	 * intensity 范围：0.0f ~ 1.0f
	 * range 单位：像素
	 */
	protected GlowConfig glowConfig = GlowConfig.defaultConfig();

	protected SimpleWidgetCoreNX(int x, int y, int w, int h, Component pMessage) {
		this(x, y, w, h, 2, pMessage);
	}

	protected SimpleWidgetCoreNX(int x, int y, int w, int h, int r, Component pMessage) {
		super(x, y, w, h, pMessage);
		setRadius(r);
	}

	protected SimpleWidgetCoreNX(int x, int y, int w, int h, int radius, int borderUsualColor, int borderHoverColor, int textUsualColor, int textHoverColor, Component pMessage) {
		super(x, y, w, h, pMessage);

		setTextUsualColor(textUsualColor);
		setTextHoverColor(textHoverColor);
		setBorderUsualColor(borderUsualColor);
		setBorderHoverColor(borderHoverColor);

		setRadius(radius);
	}

	protected SimpleWidgetCoreNX(int x, int y, int w, int h, int radius, int borderUsualColor, int borderHoverColor, int textUsualColor, int textHoverColor, int backgroundUsualColor, int backgroundHoverColor, Component pMessage) {
		super(x, y, w, h, pMessage);

		setTextUsualColor(textUsualColor);
		setTextHoverColor(textHoverColor);
		setBorderUsualColor(borderUsualColor);
		setBorderHoverColor(borderHoverColor);
		setBackgroundUsualColor(backgroundUsualColor);
		setBackgroundHoverColor(backgroundHoverColor);

		setRadius(radius);
	}

	@Override
	public T setColorScheme(_ColorScheme colorScheme) {
		super.setColorScheme(colorScheme);
		setBorderUsualColor(colorScheme.getColor("border").UsualColor());
		setBorderHoverColor(colorScheme.getColor("border").HoverColor());
		setBorderSelectColor(colorScheme.getColor("border").SelectColor());
		return self();
	}

	@Override
	public void setWidth(int pWidth) {
		super.setWidth(pWidth);
		setRadius(getRadius());
	}

	@Override
	public void setHeight(int pHeight) {
		super.setHeight(pHeight);
		setRadius(getRadius());
	}

	//-------------------------------------
	public T setBorderSelectColor(int borderSelectColor) {
		this.borderSelectColor = borderSelectColor;
		return self();
	}

	public T setBorderUsualColor(int borderUsualColor) {
		this.borderUsualColor = borderUsualColor;
		return self();
	}

	public int getBorderUsualColor() {
		return borderUsualColor;
	}

	public T setBorderHoverColor(int borderHoverColor) {
		this.borderHoverColor = borderHoverColor;
		return self();
	}

	public int getBorderHoverColor() {
		return borderHoverColor;
	}

	public T setRadius(int radius) {
		this.radius = radius;
		setContentX(getX() + radius);
		setContentY(getY() + radius);

		setContentH(getHeight() - 2 * radius);
		setContentW(getWidth() - 2 * radius);
		return self();
	}

	public int getRadius() {
		return radius;
	}

	public T setContentH(int contentH) {
		this.contentH = contentH;
		setContentEndY(getContentY() + this.contentH);
		return self();
	}

	public int getContentH() {
		return contentH;
	}

	public T setContentW(int contentW) {
		this.contentW = contentW;
		setContentEndX(getContentX() + this.contentW);
		return self();
	}

	public int getContentW() {
		return contentW;
	}

	public T setContentX(int contentX) {
		this.contentX = contentX;
		setContentEndX(this.contentX + getContentW());
		return self();
	}

	public int getContentX() {
		return contentX;
	}

	public T setContentY(int contentY) {
		this.contentY = contentY;
		setContentEndY(this.contentY + getContentH());
		return self();
	}

	public int getContentY() {
		return contentY;
	}

	public T setContentEndX(int contentEndX) {
		this.contentEndX = contentEndX;
		return self();
	}

	public int getContentEndX() {
		return contentEndX;
	}

	public T setContentEndY(int contentEndY) {
		this.contentEndY = contentEndY;
		return self();
	}

	public int getContentEndY() {
		return contentEndY;
	}

	//-------------------------------------
	/**
	 * 设置完整发光配置。
	 *
	 * 会复制一份配置，避免外部继续修改同一个配置对象后影响 Widget。
	 */
	public T setGlowConfig(GlowConfig glowConfig) {
		this.glowConfig = glowConfig == null
				? GlowConfig.defaultConfig()
				: glowConfig.copy();
		return self();
	}

	public GlowConfig getGlowConfig() {
		return glowConfig;
	}

	public T setGlowEnabled(boolean enabled) {
		glowConfig.setEnabled(enabled);
		return self();
	}

	public boolean isGlowEnabled() {
		return glowConfig.isEnabled();
	}

	/**
	 * 发光强度：0.0f ~ 1.0f。
	 */
	public T setGlowIntensity(float intensity) {
		glowConfig.setIntensity(intensity);
		return self();
	}

	public float getGlowIntensity() {
		return glowConfig.getIntensity();
	}

	/**
	 * 发光范围，单位为像素。
	 */
	public T setGlowRange(int range) {
		glowConfig.setRange(range);
		return self();
	}

	public int getGlowRange() {
		return glowConfig.getRange();
	}

	public T setInnerGlow(boolean innerGlow) {
		glowConfig.setInnerGlow(innerGlow);
		return self();
	}

	public boolean isInnerGlow() {
		return glowConfig.isInnerGlow();
	}

	public T setOuterGlow(boolean outerGlow) {
		glowConfig.setOuterGlow(outerGlow);
		return self();
	}

	public boolean isOuterGlow() {
		return glowConfig.isOuterGlow();
	}

	/**
	 * 设置偏好发光颜色。
	 *
	 * null 表示跟随当前边框颜色。
	 */
	public T setPreferredGlowColor(Integer preferredGlowColor) {
		glowConfig.setPreferredColor(preferredGlowColor);
		return self();
	}

	public Integer getPreferredGlowColor() {
		return glowConfig.getPreferredColor();
	}

	public T clearPreferredGlowColor() {
		glowConfig.setPreferredColor(null);
		return self();
	}

	//-------------------------------------
	protected void renderShape(GuiGraphicsExtractor poseStack, int borderColor, int fillColor) {
		renderShape(poseStack, getX(), getY(), getWidth(), getHeight(), getRadius(), borderColor, fillColor);
	}

	protected void renderShape(GuiGraphicsExtractor poseStack, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
		GuiGraphicsX.RoundedRect(poseStack, x, y, width, height, radius, borderColor, fillColor);
	}

	/**
	 * 绘制边框发光。
	 *
	 * <p>采用逐像素多层边框 + 平滑衰减曲线模拟软发光：
	 * 越靠近边框越亮，越远越柔和。与简单线性衰减相比，
	 * 这种方式能减少明显的“分层感”。</p>
	 */
	protected void renderGlow(GuiGraphicsExtractor guiGraphics, int glowColor, boolean inner) {
		final GlowConfig config = glowConfig;
		if (!config.isEnabled() || config.getIntensity() <= 0.0f || config.getRange() <= 0) {
			return;
		}

		final int x = getX();
		final int y = getY();
		final int width = getWidth();
		final int height = getHeight();
		final int baseRadius = getRadius();
		final int range = config.getRange();
		final float intensity = config.getIntensity();

		/*
		 * 从远到近绘制。
		 * 每一层只画 1px 边框，层与层连续叠加，避免大面积透明矩形造成发光区域发灰。
		 * 使用 smoothstep + 二次曲线，让边缘柔和，同时保留靠近边框的明显亮度。
		 */
		for (int distance = range; distance >= 1; distance--) {
			final int drawX;
			final int drawY;
			final int drawWidth;
			final int drawHeight;
			final int drawRadius;

			if (inner) {
				drawX = x + distance;
				drawY = y + distance;
				drawWidth = width - distance * 2;
				drawHeight = height - distance * 2;

				if (drawWidth <= 0 || drawHeight <= 0) {
					continue;
				}

				drawRadius = Math.max(0, baseRadius - distance);
			} else {
				drawX = x - distance;
				drawY = y - distance;
				drawWidth = width + distance * 2;
				drawHeight = height + distance * 2;
				drawRadius = baseRadius + distance;
			}

			final float t = (float) (distance - 1) / (float) range;

			/*
			 * smoothstep(1-t) 后再平方：
			 * - distance=1：最亮
			 * - 中间区域：平滑过渡
			 * - distance=range：接近透明
			 */
			final float near = 1.0f - t;
			final float smooth = near * near * (3.0f - 2.0f * near);
			final float alphaFactor = smooth * smooth * intensity;

			final int layerColor = multiplyAlpha(glowColor, alphaFactor);
			if ((layerColor >>> 24) == 0) {
				continue;
			}

			GuiGraphicsX.RoundedBorder(
					guiGraphics,
					drawX,
					drawY,
					drawWidth,
					drawHeight,
					drawRadius,
					layerColor
			);
		}

		/*
		 * 内发光额外补一层贴边高亮。
		 * 参考图的特点是边框内侧有明显的亮芯，而不是只有淡淡的雾。
		 */
		if (inner && range >= 1) {
			final float coreAlpha = Math.min(1.0f, intensity * 0.72f);
			final int coreColor = multiplyAlpha(glowColor, coreAlpha);

			final int coreX = x + 1;
			final int coreY = y + 1;
			final int coreW = width - 2;
			final int coreH = height - 2;
			if (coreW > 0 && coreH > 0 && (coreColor >>> 24) != 0) {
				GuiGraphicsX.RoundedBorder(
						guiGraphics,
						coreX,
						coreY,
						coreW,
						coreH,
						Math.max(0, baseRadius - 1),
						coreColor
				);
			}
		}
	}

	/**
	 * 将颜色本身的 Alpha 与发光强度相乘。
	 */
	private static int multiplyAlpha(int color, float alphaFactor) {
		alphaFactor = Math.max(0.0f, Math.min(1.0f, alphaFactor));

		final int sourceAlpha = (color >>> 24) & 0xFF;
		final int resultAlpha = Math.max(
				0,
				Math.min(255, Math.round(sourceAlpha * alphaFactor))
		);

		return (color & 0x00FFFFFF) | (resultAlpha << 24);
	}

	@Override
	protected void extractWidgetRenderState(
			@NotNull GuiGraphicsExtractor guiGraphics,
			int pMouseX,
			int pMouseY,
			float pPartialTick
	) {
		if (this.visible) {
			int borderColor;
			int fillColor;

			if (isMouseOver(pMouseX, pMouseY)) {
				borderColor = getBorderHoverColor();
				fillColor = getBackgroundHoverColor();
			} else {
				borderColor = getBorderUsualColor();
				fillColor = getBackgroundUsualColor();
			}

			Matrix3x2fStack poseStack = guiGraphics.pose();
			poseStack.pushMatrix();
			poseStack.translate(0, 0);

			final int finalGlowColor = glowConfig.getPreferredColor() != null
					? glowConfig.getPreferredColor()
					: borderColor;

			// 外发光必须先于主体绘制，否则会覆盖主体。
			if (glowConfig.isEnabled() && glowConfig.isOuterGlow()) {
				renderGlow(guiGraphics, finalGlowColor, false);
			}

			// 基础形状
			renderShape(guiGraphics, borderColor, fillColor);

			// 内发光绘制在主体之后，使发光贴合边框内侧。
			if (glowConfig.isEnabled() && glowConfig.isInnerGlow()) {
				renderGlow(guiGraphics, finalGlowColor, true);
			}

			// 内容
			renderContent(guiGraphics, pMouseX, pMouseY, pPartialTick);

			poseStack.popMatrix();
		}
	}

	protected abstract void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick);

	/**
	 * 发光效果配置。
	 *
	 * 示例：
	 * <pre>
	 * setGlowConfig(GlowConfig.defaultConfig()
	 *      .setEnabled(true)
	 *      .setIntensity(0.8f)
	 *      .setRange(8)
	 *      .setInnerGlow(true)
	 *      .setOuterGlow(true)
	 *      .setPreferredColor(0xFF66CCFF));
	 * </pre>
	 */
	public static class GlowConfig {
		private boolean enabled;
		private float intensity;
		private int range;
		private boolean innerGlow;
		private boolean outerGlow;
		private Integer preferredColor;

		public GlowConfig() {
			this(false, 0.65f, 6, true, true, null);
		}

		public GlowConfig(
				boolean enabled,
				float intensity,
				int range,
				boolean innerGlow,
				boolean outerGlow,
				Integer preferredColor
		) {
			this.enabled = enabled;
			setIntensity(intensity);
			setRange(range);
			this.innerGlow = innerGlow;
			this.outerGlow = outerGlow;
			this.preferredColor = preferredColor;
		}

		/**
		 * 创建默认配置。
		 *
		 * 默认关闭发光，以保持现有 Widget 的视觉表现不变。
		 */
		public static GlowConfig defaultConfig() {
			return new GlowConfig();
		}

		public GlowConfig copy() {
			return new GlowConfig(
					enabled,
					intensity,
					range,
					innerGlow,
					outerGlow,
					preferredColor
			);
		}

		public boolean isEnabled() {
			return enabled;
		}

		public GlowConfig setEnabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public float getIntensity() {
			return intensity;
		}

		public GlowConfig setIntensity(float intensity) {
			this.intensity = Math.max(0.0f, Math.min(1.0f, intensity));
			return this;
		}

		public int getRange() {
			return range;
		}

		public GlowConfig setRange(int range) {
			this.range = Math.max(0, range);
			return this;
		}

		public boolean isInnerGlow() {
			return innerGlow;
		}

		public GlowConfig setInnerGlow(boolean innerGlow) {
			this.innerGlow = innerGlow;
			return this;
		}

		public boolean isOuterGlow() {
			return outerGlow;
		}

		public GlowConfig setOuterGlow(boolean outerGlow) {
			this.outerGlow = outerGlow;
			return this;
		}

		public Integer getPreferredColor() {
			return preferredColor;
		}

		public GlowConfig setPreferredColor(Integer preferredColor) {
			this.preferredColor = preferredColor;
			return this;
		}
	}
}
