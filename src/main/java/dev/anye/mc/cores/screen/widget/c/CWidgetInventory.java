package dev.anye.mc.cores.screen.widget.c;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.dt._BoundingBox;
import dev.anye.mc.cores.screen.bs.BorderStyle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CWidgetInventory extends CWidgetBaseCore<CWidgetInventory>{
    private int slotHeight = 16,slotWidth=16,lineSize = 2;
    public CWidgetInventory(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage) {
        super(boundingBox, borderStyle, pMessage);
    }

    @Override
    protected void renderContent(GuiGraphics guiGraphics, _BoundingBox boundingBox, int mouseX, int mouseY, float partialTick) {
        for (int l = 0; l < 2; l++) {
            int x = boundingBox.getX();
            int y = boundingBox.getY() + (l + 1) * slotHeight + l * lineSize;
            guiGraphics.fill(x, y, x + boundingBox.getW(), y + lineSize, this.borderColor.UsualColor());

        }
        for (int r = 0; r < 8; r++) {
            int x = boundingBox.getX() + (r +1)* slotWidth + r * lineSize;
            int y = boundingBox.getY();
            guiGraphics.fill(x, y, x + lineSize, y + boundingBox.getH(), this.borderColor.UsualColor());
        }

    }
}
