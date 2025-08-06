package dev.anye.mc.cores.screen.widget.c;

import dev.anye.core.dt._BoundingBox;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.screen.bs.BorderStyle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CWidgetLabel extends CWidgetBaseCore<CWidgetLabel>{
    private final boolean centerText;
    public CWidgetLabel(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage) {
        this(boundingBox, borderStyle, pMessage,true,true);
    }
    public CWidgetLabel(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage,boolean centerText,boolean autoW) {
        super(autoW ? AutoWidth(boundingBox,pMessage.getString(),borderStyle.w()) : boundingBox,borderStyle, pMessage);
        this.centerText = centerText;
    }

    @Override
    protected void renderContent(GuiGraphics guiGraphics, _BoundingBox boundingBox, int mouseX, int mouseY, float partialTick) {

        int tc = getTextUsualColor();
        if (isMouseOver(mouseX,mouseY)){
            tc = getTextHoverColor();
            if (getCustomTooltip() != null) {
                guiGraphics.renderComponentTooltip(getFont(), getCustomTooltip(),mouseX, mouseY);
            }
        }
        if (isCenterText()){
            guiGraphics.drawString(font,
                    getMessage(),
                    CenterX(boundingBox.getX(),boundingBox.getW()),
                    CenterY(boundingBox.getY(),boundingBox.getH()),
                    tc,false);
            //guiGraphics.drawCenteredString(font,getMessage(),getDrawX(),getDrawY(), tc);
        }else {
            guiGraphics.drawString(font,getMessage(),boundingBox.getX(),boundingBox.getY(), tc,false);
        }
    }

    public boolean isCenterText() {
        return centerText;
    }
}
