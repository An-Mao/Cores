package dev.anye.mc.cores.screen.widget.square;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.anye.mc.cores.screen.widget.DT_XYWH;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SquareLabels extends SquareWidgetCore<SquareLabels> {
    public final int color;
    private  int dx = getX()+width/2;
    private  int dy = getY()+height/ 2 - font.lineHeight/2;
    public SquareLabels(int x, int y, int w, int h, Component pMessage, int color , int textColor) {
        super(x,y,w,h, pMessage);
        this.color = color;
        this.backgroundUsualColor = textColor;
    }
    public SquareLabels(DT_XYWH dt_xywh, Component pMessage, int color , int textColor) {
        this(dt_xywh.x(),dt_xywh.y(),dt_xywh.width(),dt_xywh.height(), pMessage,color,textColor);
    }
    public void setDx(int dx) {
        this.dx = dx;
    }
    public void setDy(int dy) {
        this.dy = dy;
    }
    @Override
    protected void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        drawSquare(guiGraphics,color);
        //RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        //System.out.println("wait render");
        //guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
        guiGraphics.drawCenteredString(font,getMessage(),dx,dy,color);
    }
}
