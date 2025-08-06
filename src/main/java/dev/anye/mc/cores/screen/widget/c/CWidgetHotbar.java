package dev.anye.mc.cores.screen.widget.c;

import dev.anye.core.dt._BoundingBox;
import dev.anye.mc.cores.screen.bs.BorderStyle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CWidgetHotbar extends CWidgetBaseCore<CWidgetHotbar>{
    private final int  slotHeight,slotWidth,lineSize;
    public CWidgetHotbar(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage) {
        this(boundingBox, borderStyle, 16,16,2,pMessage);
    }
    public CWidgetHotbar(_BoundingBox boundingBox, BorderStyle borderStyle,int slotHeight,int slotWidth,int lineSize, Component pMessage) {
        super(boundingBox, borderStyle, pMessage);
        this.slotHeight = slotHeight;
        this.slotWidth = slotWidth;
        this.lineSize = lineSize;
    }

    @Override
    protected void renderContent(GuiGraphics guiGraphics, _BoundingBox boundingBox, int mouseX, int mouseY, float partialTick) {
        for (int r = 0; r < 8; r++) {
            int x = boundingBox.getX() + (r +1)* slotWidth + r * lineSize;
            int y = boundingBox.getY();
            guiGraphics.fill(x, y, x +lineSize, y + slotHeight, this.borderColor.UsualColor());
        }

    }
}
