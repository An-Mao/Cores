package dev.anye.mc.cores.screen.bs;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.dt._BoundingBox;
import net.minecraft.client.gui.GuiGraphics;

public class LineBorderStyle extends BorderStyle{
    private final int size;
    public LineBorderStyle(){
        this(1);
    }
    public LineBorderStyle(int size) {
        super(size, size, size, size);
        this.size = size;
    }

    @Override
    public void render(GuiGraphics guiGraphics, _BoundingBox boundingBox, _ColorScheme colorScheme, int mouseX, int mouseY) {
        boolean hover = isHover(boundingBox,mouseX,mouseY);
        _ColorScheme.Color border = colorScheme.getColor(_ColorScheme.BORDER);
        int borderColor = hover ? border.HoverColor() : border.UsualColor();
        _ColorScheme.Color background = colorScheme.getColor(_ColorScheme.BACKGROUND);
        int fillColor = hover ? background.HoverColor() : background.UsualColor();
        guiGraphics.fill(boundingBox.getX(),boundingBox.getY(),boundingBox.getMaxX(),boundingBox.getY() + size,borderColor);

        guiGraphics.fill(boundingBox.getX(),boundingBox.getMaxY() - size,boundingBox.getMaxX(),boundingBox.getMaxY(),borderColor);
        guiGraphics.fill(boundingBox.getX(),boundingBox.getY() + size,boundingBox.getX() + size,boundingBox.getMaxY() - size,borderColor);
        guiGraphics.fill(boundingBox.getMaxX() - size,boundingBox.getY() +size,boundingBox.getMaxX(),boundingBox.getMaxY() -size,borderColor);

        guiGraphics.fill(boundingBox.getX() + size,boundingBox.getY() + size,boundingBox.getMaxX() - size ,boundingBox.getMaxY()  - size ,fillColor);

        boundingBox.retraction(size);
    }
}
