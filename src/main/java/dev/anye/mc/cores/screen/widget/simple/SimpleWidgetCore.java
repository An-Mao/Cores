package dev.anye.mc.cores.screen.widget.simple;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.render.Draw;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;


@OnlyIn(Dist.CLIENT)
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
    protected void renderShape(PoseStack poseStack,int borderColor,int fillColor) {
        renderShape(poseStack,getX(), getY(), getWidth(), getHeight(), getRadius(), borderColor, fillColor);
    }
    protected void renderShape(PoseStack poseStack, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        drawRoundedRect(poseStack,x, y, width, height, radius, borderColor, fillColor);
    }

    protected void drawRoundedRect(PoseStack poseStack, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        // Setup render state
        Tesselator tesselator = Tesselator.getInstance();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        poseStack.pushPose();
        Matrix4f matrix = poseStack.last().pose();
        addVertex(buffer,matrix, x + radius, y + radius, fillColor);
        addVertex(buffer,matrix, x + width - radius, y + radius, fillColor);
        addVertex(buffer,matrix, x + width - radius, y + height - radius, fillColor);
        addVertex(buffer,matrix, x + radius, y + height - radius, fillColor);
        //BufferUploader.drawWithShader(buffer.buildOrThrow());
        tesselator.end();

        poseStack.popPose();
        // Draw borders and corners
        poseStack.pushPose();
        matrix = poseStack.last().pose();
        buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        drawBorder(buffer, matrix,x, y, width, height, radius, borderColor);
        tesselator.end();
        //BufferUploader.drawWithShader(buffer.buildOrThrow());
        poseStack.popPose();

        drawCorners(poseStack, x, y, width, height, radius, borderColor);
        RenderSystem.disableBlend();
    }
    protected void drawBorder(BufferBuilder buffer,Matrix4f matrix, int x, int y, int width, int height, int radius, int borderColor) {
        // Top border
        addVertex(buffer,matrix, x + radius, y, borderColor);
        addVertex(buffer,matrix, x + width - radius, y, borderColor);
        addVertex(buffer,matrix, x + width - radius, y + radius, borderColor);
        addVertex(buffer,matrix, x + radius, y + radius, borderColor);
        // Bottom border
        addVertex(buffer,matrix, x + radius, y + height - radius, borderColor);
        addVertex(buffer,matrix, x + width - radius, y + height - radius, borderColor);
        addVertex(buffer,matrix, x + width - radius, y + height, borderColor);
        addVertex(buffer,matrix, x + radius, y + height, borderColor);
        // Left border
        addVertex(buffer,matrix, x, y + radius, borderColor);
        addVertex(buffer,matrix, x + radius, y + radius, borderColor);
        addVertex(buffer,matrix, x + radius, y + height - radius, borderColor);
        addVertex(buffer,matrix, x, y + height - radius, borderColor);
        // Right border
        addVertex(buffer,matrix, x + width - radius, y + radius, borderColor);
        addVertex(buffer,matrix, x + width, y + radius, borderColor);
        addVertex(buffer,matrix, x + width, y + height - radius, borderColor);
        addVertex(buffer,matrix, x + width - radius, y + height - radius, borderColor);
    }
    protected void drawCorners(PoseStack poseStack, int x, int y, int width, int height, int radius, int color) {
        //PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(x,y,0);
        poseStack.translate(radius, radius, 0);
        Draw.drawSector(poseStack.last().pose(),0,radius, _Math.ARC_180,_Math.ARC_270,color);
        poseStack.translate(width-2*radius, 0, 0);
        Draw.drawSector(poseStack.last().pose(),0,radius,_Math.ARC_270,_Math.ARC_360,color);
        poseStack.translate(0, height-2 * radius, 0);
        Draw.drawSector(poseStack.last().pose(),0,radius,0,_Math.ARC_90,color);
        poseStack.translate(-(width-2*radius), 0, 0);
        Draw.drawSector(poseStack.last().pose(),0,radius,_Math.ARC_90,_Math.ARC_180,color);
        poseStack.popPose();
    }
    protected void addVertex(BufferBuilder buffer, int x, int y, int color) {
        buffer.vertex(x, y, 0).color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF).endVertex();
    }
    protected void addVertex(BufferBuilder buffer, Matrix4f matrix, int x, int y, int color) {
        buffer.vertex(matrix,x, y, 0).color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF).endVertex();
    }
    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.visible) {
            int borderColor = getBorderUsualColor();
            int fillColor = getBackgroundUsualColor();
            if (isMouseOver(pMouseX, pMouseY)) {
                borderColor = getBorderHoverColor();
                fillColor = getBackgroundHoverColor();
            }
            PoseStack poseStack = pGuiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(0, 0, layerZ);
            renderShape(poseStack,borderColor,fillColor);
            renderContent(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            poseStack.popPose();
        }
    }
    protected abstract void renderContent(GuiGraphics guiGraphics,int mouseX, int mouseY, float partialTick);
}
