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

public abstract class RenderWidgetCore<T extends RenderWidgetCore<T>> extends AbstractWidget {
	protected Font font;
	protected int messageWidth, singleCharacterWidth;
	protected int textUsualColor, textHoverColor, textSelectColor, backgroundUsualColor, backgroundHoverColor, backgroundSelectColor;
	protected int layerZ = 1000;
	protected int halfFontLine;
	protected List<ClientTooltipComponent> customToolTip = new ArrayList<>();

	public RenderWidgetCore(int x, int y, int w, int h, Component pMessage) {
		this(Minecraft.getInstance().font, x, y, w, h, pMessage);
	}

	public RenderWidgetCore(Font font, int x, int y, int w, int h, Component pMessage) {
		super(x, y, w, h, pMessage);
		setFont(font);
		setColorScheme(ColorSchemes.getGlobal());
	}

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

	protected T self() {
		return (T) this;
	}

	public Font getFont() {
		return font;
	}

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

	public void setTextUsualColor(int textUsualColor) {
		this.textUsualColor = textUsualColor;
	}

	public int getTextHoverColor() {
		return textHoverColor;
	}

	public void setTextHoverColor(int textHoverColor) {
		this.textHoverColor = textHoverColor;
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

	public void setCustomToolTip(List<ClientTooltipComponent> customToolTip) {
		this.customToolTip = customToolTip;
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
