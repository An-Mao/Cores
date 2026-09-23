package dev.anye.mc.cores.screen.widget;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.am.color.ColorSchemes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用了{@link ColorSchemes}的渲染基类
 * @param <T> T 通过调用某些方法时返回自身所需要的类型
 */
public abstract class RenderWidgetCore<T extends RenderWidgetCore<T>> extends AbstractWidget {
	protected Font font;
	protected int messageWidth;
	protected int singleCharacterWidth;

	protected int textUsualColor;
	protected int textHoverColor;
	protected int textSelectColor;
	protected int backgroundUsualColor;
	protected int backgroundHoverColor;
	protected int backgroundSelectColor;

	//过小的z轴可能导致元素在某些元素的下面
	protected int layerZ = 1000;

	protected int halfFontLine;
	protected List<ClientTooltipComponent> customToolTip = new ArrayList<>();

	protected RenderWidgetCore(int x, int y, int w, int h, Component message) {
		this(Minecraft.getInstance().font, x, y, w, h, message);
	}

	protected RenderWidgetCore(Font font, int x, int y, int w, int h, Component message) {
		super(x, y, w, h, message);
		setFont(font);
		setColorScheme(ColorSchemes.getGlobal());
	}

	/**
	 * 依照配色方案设置颜色
	 * @param colorScheme 配色方案
	 * @return T
	 */
	public T setColorScheme(_ColorScheme colorScheme) {
		_ColorScheme.Color color = colorScheme.getColor("text");
		this.textHoverColor = color.HoverColor();
		this.textUsualColor = color.UsualColor();
		this.textSelectColor = color.SelectColor();
		color = colorScheme.getColor("background");
		this.backgroundHoverColor = color.HoverColor();
		this.backgroundUsualColor = color.UsualColor();
		this.backgroundSelectColor = color.SelectColor();
		return self();
	}

	/**
	 * 放回自身，方便链式设置
	 * @return T
	 */
	protected T self() {
		return (T) this;
	}

	public Font getFont() {
		return font;
	}

	/**
	 * 设置使用的字体，同时会自动重新设置一些依靠字体的内容，例如以下内容
	 * <li>消息文本的宽度</li>
	 * <li>单个字符的宽度</li>
	 * <li>文本一半的高度</li>
	 * @param font 要替换的字体
	 * @return T
	 */
	public T setFont(Font font) {
		this.font = font;
		setMessageWidth();
		setSingleCharacterWidth();
		halfFontLine = _Math.half(font.lineHeight);
		return self();
	}

	public int getMessageWidth() {
		return messageWidth;
	}

	/**
	 * 重置消息文本的长度
	 * @return T
	 */
	public T setMessageWidth() {
		this.messageWidth = font.width(getMessage());
		return self();
	}

	public int getSingleCharacterWidth() {
		return singleCharacterWidth;
	}

	public T setSingleCharacterWidth() {
		this.singleCharacterWidth = getFont().width("a");
		return self();
	}

	public int getLayerZ() {
		return layerZ;
	}

	public T setLayerZ(int layerZ) {
		this.layerZ = layerZ;
		return self();
	}

	public int getBackgroundUsualColor() {
		return backgroundUsualColor;
	}

	public T setBackgroundUsualColor(int backgroundUsualColor) {
		this.backgroundUsualColor = backgroundUsualColor;
		return self();
	}

	public int getBackgroundHoverColor() {
		return backgroundHoverColor;
	}

	public T setBackgroundHoverColor(int backgroundHoverColor) {
		this.backgroundHoverColor = backgroundHoverColor;
		return self();
	}

	public int getTextUsualColor() {
		return textUsualColor;
	}

	public T setTextUsualColor(int textUsualColor) {
		this.textUsualColor = textUsualColor;
		return self();
	}

	public int getTextHoverColor() {
		return textHoverColor;
	}

	public T setTextHoverColor(int textHoverColor) {
		this.textHoverColor = textHoverColor;
		return self();
	}

	protected void drawString(GuiGraphicsExtractor guiGraphics, Font font, int x, int y, int color, boolean shadow, Component component) {
		guiGraphics.text(font, component, x, y, color, shadow);
	}

	protected void drawString(GuiGraphicsExtractor guiGraphics, int x, int y, int color, boolean shadow, Component component) {
		drawString(guiGraphics, font, x, y, color, shadow, component);
	}

	protected void drawString(GuiGraphicsExtractor guiGraphics, int x, int y, int color, Component component) {
		drawString(guiGraphics, font, x, y, color, false, component);
	}

	protected void drawString(GuiGraphicsExtractor guiGraphics, int x, int y, int color, String s) {
		drawString(guiGraphics, font, x, y, color, false, Component.literal(s));
	}

	protected void drawString(GuiGraphicsExtractor guiGraphics, int x, int y, Component component) {
		drawString(guiGraphics, font, x, y, backgroundUsualColor, false, component);
	}

	public T setCustomToolTip(List<ClientTooltipComponent> customToolTip) {
		this.customToolTip = customToolTip;
		return self();
	}

	public List<ClientTooltipComponent> getCustomTooltip() {
		return customToolTip;
	}

	@Override
	protected abstract void extractWidgetRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float pPartialTick);

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
	}

	public interface OnPress {
		void onPress();
	}
}
