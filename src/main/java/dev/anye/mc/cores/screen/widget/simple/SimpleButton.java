package dev.anye.mc.cores.screen.widget.simple;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class SimpleButton extends SimpleLabel {
	private final OnPress onPress;

	public SimpleButton(int x, int y, int w, int h, Component pMessage, OnPress onPress) {
		this(x, y, w, h, pMessage, true, false, true, onPress);
	}

	public SimpleButton(int x, int y, int w, int h, Component pMessage, boolean AutoWidth, boolean AutoHeight, boolean centerText, OnPress onPress) {
		super(x, y, w, h, pMessage);
		setAutoWidth(AutoWidth);
		setAutoHeight(AutoHeight);
		setCenterText(centerText);
		setHoverColor(true);
		this.onPress = onPress;
	}


	@Override
	protected void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.renderContent(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void onClick(@NonNull MouseButtonEvent buttonEvent, boolean doubleClick) {
		this.onPress.onPress();
	}
}
