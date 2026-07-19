package dev.anye.mc.cores.screen.widget.simple;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.render.GuiGraphicsX;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;


public abstract class SimpleWidgetCore<T extends SimpleWidgetCore<T>> extends RenderWidgetCore<T> {
    protected int radius;
    protected int borderUsualColor,
            borderHoverColor,
            borderSelectColor;
    protected int contentX,
            contentY,
            contentW,
            contentH,
            contentEndX,
            contentEndY;
    protected SimpleWidgetCore(int x, int y, int w, int h, Component pMessage) {
        this(x, y, w, h,2, pMessage);
    }
    protected SimpleWidgetCore(int x, int y, int w, int h,int r, Component pMessage) {
        super(x, y, w, h, pMessage);
        setRadius(r);
    }
    protected SimpleWidgetCore(int x, int y, int w, int h, int radius, int borderUsualColor, int borderHoverColor,int textUsualColor,int textHoverColor, Component pMessage) {
        super(x, y, w, h, pMessage);

        setTextUsualColor(textUsualColor);
        setTextHoverColor(textHoverColor);
        setBorderUsualColor(borderUsualColor);
        setBorderHoverColor(borderHoverColor);

        setRadius(radius);

    }
    protected SimpleWidgetCore(int x, int y, int w, int h, int radius, int borderUsualColor, int borderHoverColor,int textUsualColor,int textHoverColor,int backgroundUsualColor,int backgroundHoverColor,  Component pMessage) {
        super(x, y, w, h, pMessage);

        setTextUsualColor(textUsualColor);
        setTextHoverColor(textHoverColor);
        setBorderUsualColor(borderUsualColor);
        setBorderHoverColor(borderHoverColor);
        setBackgroundUsualColor(backgroundUsualColor);
        setBackgroundHoverColor(backgroundHoverColor);

        setRadius(radius);

    }

    @Override
    public T setColorScheme(_ColorScheme colorScheme) {
        super.setColorScheme(colorScheme);
        setBorderUsualColor(colorScheme.getColor("border").UsualColor());
        setBorderHoverColor(colorScheme.getColor("border").HoverColor());
        setBorderSelectColor(colorScheme.getColor("border").SelectColor());
        return self();
    }

    @Override
    public void setWidth(int pWidth) {
        super.setWidth(pWidth);
        setRadius(getRadius());
    }

    @Override
    public void setHeight(int pHeight) {
        super.setHeight(pHeight);
        setRadius(getRadius());
    }

    //-------------------------------------
    public T setBorderSelectColor(int borderSelectColor) {
        this.borderSelectColor = borderSelectColor;
        return self();
    }
    public T setBorderUsualColor(int borderUsualColor) {
        this.borderUsualColor = borderUsualColor;
        return self();
    }

    public int getBorderUsualColor() {
        return borderUsualColor;
    }

    public T setBorderHoverColor(int borderHoverColor) {
        this.borderHoverColor = borderHoverColor;
        return self();
    }

    public int getBorderHoverColor() {
        return borderHoverColor;
    }

    public T setRadius(int radius) {
        this.radius = radius;
        setContentX(getX() + radius);
        setContentY(getY() + radius);

        setContentH(getHeight() - 2 * radius);
        setContentW(getWidth() - 2 * radius);
        return self();
    }

    public int getRadius() {
        return radius;
    }

    public T setContentH(int contentH) {
        this.contentH = contentH;
        setContentEndY(getContentY() + this.contentH);
        return self();
    }

    public int getContentH() {
        return contentH;
    }

    public T setContentW(int contentW) {
        this.contentW = contentW;
        setContentEndX(getContentX() + this.contentW);
        return self();
    }

    public int getContentW() {
        return contentW;
    }

    public T setContentX(int contentX) {
        this.contentX = contentX;
        setContentEndX(this.contentX + getContentW());
        return self();
    }

    public int getContentX() {
        return contentX;
    }
    public T setContentY(int contentY) {
        this.contentY = contentY;
        setContentEndY(this.contentY + getContentH());
        return self();
    }

    public int getContentY() {
        return contentY;
    }

    public T setContentEndX(int contentEndX) {
        this.contentEndX = contentEndX;
        return self();
    }

    public int getContentEndX() {
        return contentEndX;
    }

    public T setContentEndY(int contentEndY) {
        this.contentEndY = contentEndY;
        return self();
    }

    public int getContentEndY() {
        return contentEndY;
    }

    //-------------------------------------
    protected void renderShape(GuiGraphicsExtractor poseStack, int borderColor, int fillColor) {
        renderShape(poseStack,getX(), getY(), getWidth(), getHeight(), getRadius(), borderColor, fillColor);
    }
    protected void renderShape(GuiGraphicsExtractor poseStack, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        GuiGraphicsX.RoundedRect(poseStack,x, y, width, height, radius, borderColor, fillColor);
    }

    @Override
    protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.visible) {
            int borderColor;
            int fillColor;
            if (isMouseOver(pMouseX, pMouseY)) {
                borderColor = getBorderHoverColor();
                fillColor = getBackgroundHoverColor();
            }else {
                borderColor = getBorderUsualColor();
                fillColor = getBackgroundUsualColor();
            }
            Matrix3x2fStack poseStack = guiGraphics.pose();
            poseStack.pushMatrix();
            poseStack.translate(0, 0);
            renderShape(guiGraphics,borderColor,fillColor);
            renderContent(guiGraphics, pMouseX, pMouseY, pPartialTick);
            poseStack.popMatrix();
        }
    }
    protected abstract void renderContent(GuiGraphicsExtractor guiGraphics,int mouseX, int mouseY, float partialTick);
}
