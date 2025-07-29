package dev.anye.mc.cores.screen.widget.c;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.anye.core.dt._BoundingBox;
import dev.anye.mc.cores.screen.bs.BorderStyle;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public abstract class CWidgetBaseCore<T extends CWidgetBaseCore<T>> extends RenderWidgetCore<T> {
    protected final _BoundingBox boundingBox;
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
            _BoundingBox newBoundingBox = boundingBox.copy();
            poseStack.pushPose();
            poseStack.translate(0, 0, layerZ);
            borderStyle.render(pGuiGraphics,newBoundingBox,this.colorScheme(),pMouseX,pMouseY);
            renderContent(pGuiGraphics,newBoundingBox, pMouseX, pMouseY, pPartialTick);
            poseStack.popPose();
        }
    }

    protected abstract void renderContent(GuiGraphics guiGraphics,_BoundingBox boundingBox,int mouseX, int mouseY, float partialTick);
}
