package dev.anye.mc.cores.screen.widget.simple;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SimpleHotbar extends SimpleInventory {
    public SimpleHotbar(int x, int y, Component pMessage) {
        super(x, y, pMessage);
        setHeight(20);
        setInventoryHeight(16);
    }

    @Override
    protected void renderContent(GuiGraphics guiGraphics, int i, int i1, float v) {
        for (int r = 0; r < 8; r++) {
            int x = getContentX() + (r +1)* slotWidth + r * getRadius();
            int y = getContentY();
            guiGraphics.fill(x, y, x + getRadius(), y + inventoryHeight, getBorderUsualColor());
        }
    }
}
