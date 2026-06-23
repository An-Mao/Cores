package dev.anye.mc.cores.screen.widget.simple;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class SimpleButton extends SimpleLabel {
	private final OnPress onPress;

	public SimpleButton(int x, int y, int w, int h, Component pMessage, OnPress onPress) {
		this(x, y, w, h, pMessage, true, false, true, onPress);
	}

	public SimpleButton(int x, int y, int w, int h, Component pMessage, boolean AutoWidth, boolean AutoHeight, boolean centerText, OnPress onPress) {
		super(x, y, w, h, pMessage, AutoWidth, AutoHeight, centerText);
		this.onPress = onPress;
	}

	public SimpleButton(int x, int y, int w, int h, Component pMessage, int borderColor, int fillColor, int textColor, boolean AutoWidth, boolean AutoHeight, boolean centerText, OnPress onPress) {
		super(x, y, w, h, pMessage, borderColor, fillColor, textColor, AutoWidth, AutoHeight, centerText);
		this.onPress = onPress;
	}

	@Override
	protected void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.renderContent(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void onClick(MouseButtonEvent p_446284_, boolean p_434599_) {
		this.onPress.onPress();
	}
}
