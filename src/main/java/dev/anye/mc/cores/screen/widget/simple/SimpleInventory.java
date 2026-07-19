package dev.anye.mc.cores.screen.widget.simple;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class SimpleInventory extends SimpleSlot {
    protected int
            inventoryWidth,
            inventoryHeight;
    public SimpleInventory(int x, int y,Component pMessage) {
        super(x, y, 164 , 56, pMessage);
        setInventoryHeight(52);
        setInventoryWidth(160);
    }
    public void setInventoryWidth(int inventoryWidth) {
        this.inventoryWidth = inventoryWidth;
    }
    public void setInventoryHeight(int inventoryHeight) {
        this.inventoryHeight = inventoryHeight;
    }

    @Override
    protected void renderContent(GuiGraphicsExtractor guiGraphics, int i, int i1, float v) {
        for (int l = 0; l < 2; l++) {
            int x = getContentX();
            int y = getContentY() + (l + 1) * slotHeight + l * getRadius();
            guiGraphics.fill(x, y, x + inventoryWidth, y + getRadius(), getBorderUsualColor());

        }
        for (int r = 0; r < 8; r++) {
            int x = getContentX() + (r +1)* slotWidth + r * getRadius();
            int y = getContentY();
            guiGraphics.fill(x, y, x + getRadius(), y + inventoryHeight, getBorderUsualColor());
        }


    }

}
