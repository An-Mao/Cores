package dev.anye.mc.cores.screen.bs;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.dt._BoundingBox;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.render.BufferHelper;
import dev.anye.mc.cores.render.DrawSector;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

public class RoundedBorderStyle extends BorderStyle{
    private int radius;
    public RoundedBorderStyle(int radius){
        super(radius,radius,radius,radius);
        setRadius(radius);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void render(GuiGraphics guiGraphics, final _BoundingBox boundingBox,_ColorScheme colorScheme, int mouseX, int mouseY) {

        boolean hover = isHover(boundingBox,mouseX,mouseY);
        _ColorScheme.Color border = colorScheme.getColor(_ColorScheme.BORDER);
        int borderColor = hover ? border.HoverColor() : border.UsualColor();
        _ColorScheme.Color background = colorScheme.getColor(_ColorScheme.BACKGROUND);
        int fillColor = hover ? background.HoverColor() : background.UsualColor();

        drawRoundedRect(guiGraphics.pose(),boundingBox.getX(),boundingBox.getY(),boundingBox.getW(),boundingBox.getH(),radius,borderColor,fillColor);
        boundingBox.retraction(radius);
    }

    protected void drawRoundedRect(PoseStack poseStack, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        // Setup render state
        Tesselator tesselator = Tesselator.getInstance();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        poseStack.pushPose();
        Matrix4f matrix = poseStack.last().pose();
        BufferHelper.addColorVertex(buffer,matrix, x + radius, y + radius, fillColor);
        BufferHelper.addColorVertex(buffer,matrix, x + width - radius, y + radius, fillColor);
        BufferHelper.addColorVertex(buffer,matrix, x + width - radius, y + height - radius, fillColor);
        BufferHelper.addColorVertex(buffer,matrix, x + radius, y + height - radius, fillColor);
        BufferUploader.drawWithShader(buffer.buildOrThrow());
        poseStack.popPose();
        // Draw borders and corners
        poseStack.pushPose();
        matrix = poseStack.last().pose();
        buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        drawBorder(buffer, matrix,x, y, width, height, radius, borderColor);
        BufferUploader.drawWithShader(buffer.buildOrThrow());
        poseStack.popPose();

        drawCorners(poseStack, x, y, width, height, radius, borderColor);
        RenderSystem.disableBlend();
    }
    protected void drawBorder(BufferBuilder buffer,Matrix4f matrix, int x, int y, int width, int height, int radius, int borderColor) {
        // Top border
        BufferHelper.addColorVertex(buffer,matrix, x + radius, y, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + width - radius, y, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + width - radius, y + radius, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + radius, y + radius, borderColor);
        // Bottom border
        BufferHelper.addColorVertex(buffer,matrix, x + radius, y + height - radius, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + width - radius, y + height - radius, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + width - radius, y + height, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + radius, y + height, borderColor);
        // Left border
        BufferHelper.addColorVertex(buffer,matrix, x, y + radius, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + radius, y + radius, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + radius, y + height - radius, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x, y + height - radius, borderColor);
        // Right border
        BufferHelper.addColorVertex(buffer,matrix, x + width - radius, y + radius, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + width, y + radius, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + width, y + height - radius, borderColor);
        BufferHelper.addColorVertex(buffer,matrix, x + width - radius, y + height - radius, borderColor);
    }
    protected void drawCorners(PoseStack poseStack, int x, int y, int width, int height, int radius, int color) {
        //PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(x,y,0);
        poseStack.translate(radius, radius, 0);
        DrawSector.draw(poseStack.last().pose(),0,radius, _Math.ARC_180,_Math.ARC_270,color);
        poseStack.translate(width-2*radius, 0, 0);
        DrawSector.draw(poseStack.last().pose(),0,radius,_Math.ARC_270,_Math.ARC_360,color);
        poseStack.translate(0, height-2 * radius, 0);
        DrawSector.draw(poseStack.last().pose(),0,radius,0,_Math.ARC_90,color);
        poseStack.translate(-(width-2*radius), 0, 0);
        DrawSector.draw(poseStack.last().pose(),0,radius,_Math.ARC_90,_Math.ARC_180,color);
        poseStack.popPose();
    }
}
