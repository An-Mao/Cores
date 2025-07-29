package dev.anye.mc.cores.screen.bs;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.dt._BoundingBox;
import dev.anye.mc.cores.am.color.ColorSchemes;
import net.minecraft.client.gui.GuiGraphics;

public abstract class BorderStyle {
    protected BorderStyle(_ColorScheme colorScheme) {
    }
    protected BorderStyle() {
    }
    public abstract void render(GuiGraphics guiGraphics, _BoundingBox boundingBox,_ColorScheme colorScheme, int mouseX, int mouseY);
    public void renderElement(GuiGraphics guiGraphics, _BoundingBox boundingBox,_ColorScheme colorScheme, int mouseX, int mouseY){
        render(guiGraphics,boundingBox,colorScheme,mouseX,mouseY);
    }

    public boolean isHover(_BoundingBox boundingBox, int mouseX, int mouseY){
        return mouseX > boundingBox.getX() && mouseX < boundingBox.getMaxX() && mouseY > boundingBox.getY() && mouseY < boundingBox.getMaxY();
    }
}
