package dev.anye.mc.cores.screen.widget.simple;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class SimpleHotbar extends SimpleSlot {
	protected int inventoryHeight;
	public SimpleHotbar(int x, int y, Component pMessage) {
		super(x, y,100,20, pMessage);
		borderRender.updateGlow(glowData -> glowData.setInner(false));
		setHeight((int) (slotHeight + borderRender.borderTotalHeight() * 2));
		setWidth((int) (2 * borderRender.borderTotalWidth() + 8 * borderRender.borderThickness() + 9 * slotWidth));
		inventoryHeight = ((int) borderRender.cH());
	}

	@Override
	protected void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
		for (int r = 0; r < 8; r++) {
			int x = (int) (getContentX() + (r + 1) * slotWidth + r * borderRender.borderThickness());
			int y = getContentY();
			guiGraphics.fill(x, y, (int) (x + borderRender.borderThickness()), y + inventoryHeight, getBorderUsualColor());
		}
	}
}
