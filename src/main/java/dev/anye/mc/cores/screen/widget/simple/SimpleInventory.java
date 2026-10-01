package dev.anye.mc.cores.screen.widget.simple;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class SimpleInventory extends SimpleSlot {
	protected int inventoryWidth;
	protected int inventoryHeight;

	public SimpleInventory(int x, int y, Component pMessage) {
		super(x, y, 164, 56, pMessage);
		borderRender.updateGlow(glowData -> glowData.setInner(false));
		setWidth((int) (2 * borderRender.borderTotalWidth() + 8 * borderRender.borderThickness() + 9 * slotWidth));
		setHeight((int) (2 * borderRender.borderTotalHeight() + 2 * borderRender.borderThickness()  + 3 * slotWidth));
		setInventoryHeight((int) borderRender.cH());
		setInventoryWidth((int) borderRender.cW());
	}

	public void setInventoryWidth(int inventoryWidth) {
		this.inventoryWidth = inventoryWidth;
	}

	public void setInventoryHeight(int inventoryHeight) {
		this.inventoryHeight = inventoryHeight;
	}

	@Override
	protected void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
		for (int l = 0; l < 2; l++) {
			int x = getContentX();
			int y = (int) (getContentY() + (l + 1) * slotHeight + l * borderRender.borderThickness());
			guiGraphics.fill(x, y, x + inventoryWidth, (int) (y + borderRender.borderThickness()), getBorderUsualColor());

		}
		for (int r = 0; r < 8; r++) {
			int x = (int) (getContentX() + (r + 1) * slotWidth + r * borderRender.borderThickness());
			int y = getContentY();
			guiGraphics.fill(x, y, (int) (x + borderRender.borderThickness()), y + inventoryHeight, getBorderUsualColor());
		}
	}

}
