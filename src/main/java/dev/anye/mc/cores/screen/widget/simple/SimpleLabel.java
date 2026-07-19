package dev.anye.mc.cores.screen.widget.simple;

import dev.anye.core.math._Math;
import dev.anye.mc.cores.render.GuiGraphicsX;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class SimpleLabel extends SimpleWidgetCore<SimpleLabel> {
    private int drawX, drawY;
    private boolean AutoWidth, AutoHeight;
    private boolean centerText;
    public SimpleLabel(int x, int y, int w, int h, Component pMessage, int borderColor,int fillColor,  int textColor, boolean AutoWidth, boolean AutoHeight,boolean centerText) {
        super(x, y, w, h, pMessage);

        setAutoWidth(AutoWidth);
        setAutoHeight(AutoHeight);
        setCenterText(centerText);
        setBorderUsualColor(borderColor);
        setBorderHoverColor(borderColor);
        setBackgroundHoverColor(fillColor);
        setBackgroundUsualColor(fillColor);
        setTextUsualColor(textColor);
        setTextHoverColor(textColor);

    }
    public SimpleLabel(int x, int y, int w, int h, Component pMessage,  boolean AutoWidth, boolean AutoHeight,boolean centerText) {
        super(x, y, w, h,pMessage);
        setAutoWidth(AutoWidth);
        setAutoHeight(AutoHeight);
        setCenterText(centerText);
    }
    public SimpleLabel(int x, int y, int w, int h, Component pMessage, int borderColor,int fillColor, int textColor, boolean AutoWidth, boolean AutoHeight) {
        this(x, y, w, h, pMessage, borderColor,fillColor, textColor, AutoWidth, AutoHeight,false);
    }
    public SimpleLabel(int x, int y, int w, int h, Component pMessage, int borderColor, int fillColor,int textColor, boolean AutoWidth) {
        this(x, y, w, h, pMessage, borderColor,fillColor, textColor, AutoWidth,false);

    }
    public SimpleLabel(int x, int y, int w, int h, Component pMessage,int borderColor, int fillColor,int textColor) {
        this(x, y, w, h, pMessage, borderColor, fillColor, textColor, true);

    }

    @Override
    public void setMessage(Component pMessage) {
        super.setMessage(pMessage);
        setAutoWidth(isAutoWidth());
    }

    public boolean isAutoWidth() {
        return AutoWidth;
    }
    public SimpleLabel setAutoWidth(boolean AutoWidth) {
        this.AutoWidth = AutoWidth;
        if (this.AutoWidth) {
            setWidth(font.width(getMessage())+getRadius()*2+10);
            setCenterText(centerText);
        }
        return this;
    }
    public boolean isAutoHeight() {
        return AutoHeight;
    }
    public SimpleLabel setAutoHeight(boolean autoHeight) {
        this.AutoHeight = autoHeight;
        if (this.AutoHeight) {
            setHeight(font.lineHeight+getRadius()* 2 + 4);
            setCenterText(centerText);
        }
        return this;
    }
    public boolean isCenterText() {
        return centerText;
    }
    public SimpleLabel setCenterText(boolean centerText) {
        this.centerText = centerText;
        if (this.centerText) {

            setDrawX(getContentX() + _Math.half(getContentW()));
            setDrawY(getContentY() + _Math.half(getContentH()) - _Math.half(font.lineHeight));
        }else {
            setDrawX(getContentX());
            setDrawY(getContentY());
        }
        return this;
    }
    public SimpleLabel setDrawX(int drawX) {
        this.drawX = drawX;
        return this;
    }
    public int getDrawX() {
        return drawX;
    }
    public SimpleLabel setDrawY(int drawY) {
        this.drawY = drawY;
        return this;
    }
    public int getDrawY() {
        return drawY;
    }
    @Override
    protected void renderContent(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        //guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
        boolean mouseOver = isMouseOver(pMouseX, pMouseY);
        int tc = mouseOver ? getTextHoverColor() : getTextUsualColor();
        if (isCenterText()) {
            guiGraphics.text(font, getMessage(), getDrawX() - _Math.half(font.width(getMessage())), getDrawY(), tc, false);
            //guiGraphics.drawCenteredString(font,getMessage(),getDrawX(),getDrawY(), tc);
        } else {
            guiGraphics.text(font, getMessage(), getDrawX(), getDrawY(), tc, false);
        }
        if (mouseOver && getCustomTooltip() != null && !getCustomTooltip().isEmpty()) {
            GuiGraphicsX.renderTooltip(guiGraphics, getFont(), getCustomTooltip(), pMouseX, pMouseY);
        }

    }
}
