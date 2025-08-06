package dev.anye.mc.cores.screen.widget.c;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.anye.core.dt._BoundingBox;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.screen.bs.BorderStyle;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public abstract class CWidgetBaseCore<T extends CWidgetBaseCore<T>> extends RenderWidgetCore<T> {
    protected final _BoundingBox boundingBox;
    protected _BoundingBox contentBox;
    protected final BorderStyle borderStyle;
    public CWidgetBaseCore(_BoundingBox boundingBox,BorderStyle borderStyle, Component pMessage) {
        super(boundingBox.getX(), boundingBox.getY(), boundingBox.getW(), boundingBox.getH(), pMessage);
        this.boundingBox = boundingBox;
        this.borderStyle = borderStyle;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.visible) {
            PoseStack poseStack = pGuiGraphics.pose();
            contentBox = boundingBox.copy();
            poseStack.pushPose();
            poseStack.translate(0, 0, layerZ);
            borderStyle.render(pGuiGraphics,contentBox,this.colorScheme(),pMouseX,pMouseY);
            renderContent(pGuiGraphics,contentBox, pMouseX, pMouseY, pPartialTick);
            poseStack.popPose();
        }
    }

    protected abstract void renderContent(GuiGraphics guiGraphics,_BoundingBox boundingBox,int mouseX, int mouseY, float partialTick);


    public  static _BoundingBox AutoWidth(_BoundingBox boundingBox, String s,int offset){
        boundingBox.setW(Minecraft.getInstance().font.width(s) + offset);
        return boundingBox;
    }
    public int CenterY(int y, int h){
        return y + ((h - font.lineHeight)>>1) + 1;
    }
    public int CenterX(int x, int w){
        return x + ((w - font.width(getMessage())) >> 1);
    }
}
