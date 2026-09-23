package dev.anye.mc.cores.screen.widget.simple;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.render.GuiGraphicsX;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;


public abstract class SimpleWidgetCoreN<T extends SimpleWidgetCoreN<T>> extends RenderWidgetCore<T> {
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

	// --- 发光效果配置 ---
	protected boolean enableGlow = true; // 发光开关，默认关闭
	protected int glowRadius = 8;         // 发光范围，默认 5 像素
	protected float glowIntensity = 1.2f; // 发光强度 (0.0 - 1.0)，默认 1.0
	protected boolean innerGlow = true;  // 内发光，默认关闭
	protected boolean outerGlow = true;   // 外发光，默认开启
	/**
	 * 发光颜色偏好。
	 * 如果为 null，则默认跟随当前的边框颜色 (borderUsualColor / borderHoverColor)。
	 */
	protected Integer preferredGlowColor = null;

	protected SimpleWidgetCoreN(int x, int y, int w, int h, Component pMessage) {
		this(x, y, w, h, 2, pMessage);
	}

	protected SimpleWidgetCoreN(int x, int y, int w, int h, int r, Component pMessage) {
		super(x, y, w, h, pMessage);
		setRadius(r);
	}

	protected SimpleWidgetCoreN(int x, int y, int w, int h, int radius, int borderUsualColor, int borderHoverColor, int textUsualColor, int textHoverColor, Component pMessage) {
		super(x, y, w, h, pMessage);

		setTextUsualColor(textUsualColor);
		setTextHoverColor(textHoverColor);
		setBorderUsualColor(borderUsualColor);
		setBorderHoverColor(borderHoverColor);

		setRadius(radius);

	}

	protected SimpleWidgetCoreN(int x, int y, int w, int h, int radius, int borderUsualColor, int borderHoverColor, int textUsualColor, int textHoverColor, int backgroundUsualColor, int backgroundHoverColor, Component pMessage) {
		super(x, y, w, h, pMessage);

		setTextUsualColor(textUsualColor);
		setTextHoverColor(textHoverColor);
		setBorderUsualColor(borderUsualColor);
		setBorderHoverColor(borderHoverColor);
		setBackgroundUsualColor(backgroundUsualColor);
		setBackgroundHoverColor(backgroundHoverColor);

		setRadius(radius);

	}



	//-------------------------------------
	// 发光效果配置方法
	//-------------------------------------
	public T setEnableGlow(boolean enableGlow) {
		this.enableGlow = enableGlow;
		return self();
	}

	public boolean isEnableGlow() {
		return enableGlow;
	}

	public T setGlowRadius(int glowRadius) {
		this.glowRadius = glowRadius;
		return self();
	}

	public int getGlowRadius() {
		return glowRadius;
	}

	public T setGlowIntensity(float glowIntensity) {
		this.glowIntensity = glowIntensity;
		return self();
	}

	public float getGlowIntensity() {
		return glowIntensity;
	}

	public T setInnerGlow(boolean innerGlow) {
		this.innerGlow = innerGlow;
		return self();
	}

	public boolean isInnerGlow() {
		return innerGlow;
	}

	public T setOuterGlow(boolean outerGlow) {
		this.outerGlow = outerGlow;
		return self();
	}

	public boolean isOuterGlow() {
		return outerGlow;
	}

	public T setPreferredGlowColor(Integer preferredGlowColor) {
		this.preferredGlowColor = preferredGlowColor;
		return self();
	}

	public Integer getPreferredGlowColor() {
		return preferredGlowColor;
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
	protected void renderShape(GuiGraphicsExtractor poseStack, int borderColor, int fillColor) {
		renderShape(poseStack, getX(), getY(), getWidth(), getHeight(), getRadius(), borderColor, fillColor);
	}

	protected void renderShape(GuiGraphicsExtractor poseStack, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
		GuiGraphicsX.RoundedRect(poseStack, x, y, width, height, radius, borderColor, fillColor);
	}

	@Override
	protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		if (this.visible) { //[cite: 1]
			int borderColor; //[cite: 1]
			int fillColor; //[cite: 1]
			if (isMouseOver(pMouseX, pMouseY)) { //[cite: 1]
				borderColor = getBorderHoverColor(); //[cite: 1]
				fillColor = getBackgroundHoverColor(); //[cite: 1]
			} else { //[cite: 1]
				borderColor = getBorderUsualColor(); //[cite: 1]
				fillColor = getBackgroundUsualColor(); //[cite: 1]
			}

			Matrix3x2fStack poseStack = guiGraphics.pose(); //[cite: 1]
			poseStack.pushMatrix(); //[cite: 1]
			poseStack.translate(0, 0); //[cite: 1]

			// 决定最终的发光颜色：如果有偏好颜色则使用，否则使用当前边框颜色
			int finalGlowColor = (this.preferredGlowColor != null) ? this.preferredGlowColor : borderColor;

			// 绘制外发光
			if (this.enableGlow && this.outerGlow) {
				renderGlow(guiGraphics, finalGlowColor, false);
			}

			// 绘制基础形状[cite: 1]
			renderShape(guiGraphics, borderColor, fillColor); //[cite: 1]

			// 绘制内发光
			if (this.enableGlow && this.innerGlow) {
				renderGlow(guiGraphics, finalGlowColor, true);
			}

			// 绘制内容[cite: 1]
			renderContent(guiGraphics, pMouseX, pMouseY, pPartialTick); //[cite: 1]

			poseStack.popMatrix(); //[cite: 1]
		}
	}

	/**
	 * 细腻的高品质发光渲染方法（支持霓虹灯效果）。
	 *
	 * @param guiGraphics 渲染上下文
	 * @param color 最终计算出的发光颜色
	 * @param isInner 是否为内发光
	 */
	protected void renderGlow(GuiGraphicsExtractor guiGraphics, int color, boolean isInner) {
		int a = (color >> 24) & 0xFF;
		if (a == 0) a = 255;

		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;

		int steps = this.glowRadius;
		if (steps <= 0 || this.glowIntensity <= 0.0f) {
			return;
		}

		float maxAlpha = (a / 255.0f) * this.glowIntensity;
		if (isInner) {
			maxAlpha = Math.min(1.0f, maxAlpha * 1.8f);
		}

		for (int i = 1; i <= steps; i++) {
			float fraction = (float) i / steps;
			float falloff = (float) Math.pow(1.0f - fraction, 2.5f);
			int currentAlpha = (int) (maxAlpha * falloff * 255);

			if (currentAlpha <= 2) continue;

			int currentColor = (currentAlpha << 24) | (r << 16) | (g << 8) | b;
			int currentX, currentY, currentW, currentH, currentR;

			if (isInner) {
				currentX = getX() + i;
				currentY = getY() + i;
				currentW = getWidth() - i * 2;
				currentH = getHeight() - i * 2;
				currentR = getRadius() - i;
			} else {
				currentX = getX() - i;
				currentY = getY() - i;
				currentW = getWidth() + i * 2;
				currentH = getHeight() + i * 2;
				currentR = getRadius() + i;
			}

			// 此时无需在外部繁琐地限制尺寸，GuiGraphicsX 底层会自动拦截非法数据
			GuiGraphicsX.RoundedRect(guiGraphics, currentX, currentY, currentW, currentH, currentR, currentColor, 0);
		}
	}

	/*
	 * 发光效果的实际绘制方法。
	 * 采用多层透明度衰减的圆角矩形叠加来模拟发光（Glow）效果。
	 *
	 * @param guiGraphics 渲染上下文
	 * @param color 最终计算出的发光颜色
	 * @param isInner 是否为内发光
	 */
	/*protected void renderGlow(GuiGraphicsExtractor guiGraphics, int color, boolean isInner) {
		// 1. 提取颜色的 ARGB 分量
		int a = (color >> 24) & 0xFF;
		if (a == 0) a = 255; // 如果传入的颜色没有Alpha通道，默认视为完全不透明

		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;

		// 2. 获取发光步长（层数）
		int steps = this.glowRadius;
		if (steps <= 0 || this.glowIntensity <= 0.0f) {
			return;
		}

		// 3. 根据发光强度计算最大起始透明度
		float maxAlpha = (a / 255.0f) * this.glowIntensity;

		// 4. 逐层绘制渐变发光轮廓
		for (int i = 1; i <= steps; i++) {
			// 计算当前层的透明度。使用二次方衰减（fraction * fraction）能让边缘更柔和自然
			float fraction = 1.0f - ((float) i / steps);
			int currentAlpha = (int) (maxAlpha * fraction * fraction * 255);

			// 如果透明度过低，直接跳过以节省性能
			if (currentAlpha <= 5) continue;

			// 合成当前层的颜色（带渐变 Alpha）
			int currentColor = (currentAlpha << 24) | (r << 16) | (g << 8) | b;

			int currentX, currentY, currentW, currentH, currentR;

			if (isInner) {
				// 内发光：向内收缩边界
				currentX = getX() + i;
				currentY = getY() + i;
				currentW = getWidth() - i * 2;
				currentH = getHeight() - i * 2;
				// 半径也要对应缩小，最小为0
				currentR = Math.max(0, getRadius() - i);
			} else {
				// 外发光：向外扩张边界
				currentX = getX() - i;
				currentY = getY() - i;
				currentW = getWidth() + i * 2;
				currentH = getHeight() + i * 2;
				// 半径对应放大
				currentR = getRadius() + i;
			}

			// 避免尺寸小于等于0时的非法渲染
			if (currentW <= 0 || currentH <= 0) continue;

			// 使用已有的 GuiGraphicsX 工具进行渲染
			// 将边框颜色设为渐变色，填充颜色设为 0 (透明)
			GuiGraphicsX.RoundedRect(guiGraphics, currentX, currentY, currentW, currentH, currentR, currentColor, 0);
		}
	}*/
	protected abstract void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick);
}
