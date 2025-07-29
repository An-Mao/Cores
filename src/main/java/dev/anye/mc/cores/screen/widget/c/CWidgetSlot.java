package dev.anye.mc.cores.screen.widget.c;

import dev.anye.core.dt._BoundingBox;
import dev.anye.mc.cores.screen.bs.BorderStyle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CWidgetSlot extends CWidgetBaseCore<CWidgetSlot>{
    public CWidgetSlot(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage) {
        super(boundingBox, borderStyle, pMessage);
    }

    @Override
    protected void renderContent(GuiGraphics guiGraphics, _BoundingBox boundingBox, int mouseX, int mouseY, float partialTick) {

    }
}
