package dev.anye.mc.cores.screen.bs;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.dt._BoundingBox;
import net.minecraft.client.gui.GuiGraphics;

public abstract class BorderStyle {
    protected final int top,bottom,left,right;
    protected BorderStyle(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public abstract void render(GuiGraphics guiGraphics, _BoundingBox boundingBox,_ColorScheme colorScheme, int mouseX, int mouseY);
    public void renderElement(GuiGraphics guiGraphics, _BoundingBox boundingBox,_ColorScheme colorScheme, int mouseX, int mouseY){
        render(guiGraphics,boundingBox,colorScheme,mouseX,mouseY);
    }

    public boolean isHover(_BoundingBox boundingBox, int mouseX, int mouseY){
        return mouseX > boundingBox.getX() && mouseX < boundingBox.getMaxX() && mouseY > boundingBox.getY() && mouseY < boundingBox.getMaxY();
    }

    public int w(){
        return left() + right();
    }
    public int h(){
        return top() + bottom();
    }
    public int top(){
        return top;
    }
    public int bottom(){
        return bottom;
    }
    public int left(){
        return left;
    }
    public int right(){
        return right;
    }
}
