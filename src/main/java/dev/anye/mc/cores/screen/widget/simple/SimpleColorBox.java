package dev.anye.mc.cores.screen.widget.simple;

import dev.anye.core.color._ColorSupport;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class SimpleColorBox extends SimpleWidgetCore<SimpleColorBox> {
    private int showColor;
    public SimpleColorBox(int x, int y, int w, int h, Component pMessage) {
        super(x, y, w, h, pMessage);
    }
    public SimpleColorBox setShowColor(String showColor) {
        this.showColor = _ColorSupport.HexToColor(showColor);
        return self();
    }
    public SimpleColorBox setShowColor(int showColor) {
        this.showColor = showColor;
        return self();
    }

    public int getShowColor() {
        return showColor;
    }

    @Override
    protected void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(getContentX(), getContentY(), getContentEndX(), getContentEndY(), getShowColor());
    }

}
