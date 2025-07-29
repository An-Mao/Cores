package dev.anye.mc.cores.screen.bs;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.dt._BoundingBox;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.render.DrawSector;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

public class RoundedBorderStyle extends BorderStyle{
    private int radius;
    public RoundedBorderStyle(_ColorScheme colorScheme, int radius){
        super(colorScheme);
        setRadius(radius);
    }
    public RoundedBorderStyle(int radius){
        super();
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

        renderShape(guiGraphics.pose(),boundingBox.getX(),boundingBox.getY(),boundingBox.getW(),boundingBox.getH(),radius,borderColor,fillColor);
        boundingBox.retraction(radius);
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
        BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        poseStack.pushPose();
        Matrix4f matrix = poseStack.last().pose();
        addVertex(buffer,matrix, x + radius, y + radius, fillColor);
        addVertex(buffer,matrix, x + width - radius, y + radius, fillColor);
        addVertex(buffer,matrix, x + width - radius, y + height - radius, fillColor);
        addVertex(buffer,matrix, x + radius, y + height - radius, fillColor);
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
        DrawSector.draw(poseStack.last().pose(),0,radius, _Math.ARC_180,_Math.ARC_270,color);
        poseStack.translate(width-2*radius, 0, 0);
        DrawSector.draw(poseStack.last().pose(),0,radius,_Math.ARC_270,_Math.ARC_360,color);
        poseStack.translate(0, height-2 * radius, 0);
        DrawSector.draw(poseStack.last().pose(),0,radius,0,_Math.ARC_90,color);
        poseStack.translate(-(width-2*radius), 0, 0);
        DrawSector.draw(poseStack.last().pose(),0,radius,_Math.ARC_90,_Math.ARC_180,color);
        poseStack.popPose();
    }
    protected void addVertex(BufferBuilder buffer, int x, int y, int color) {
        buffer.addVertex(x, y, 0).setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);//.endVertex();
    }
    protected void addVertex(BufferBuilder buffer, Matrix4f matrix, int x, int y, int color) {
        buffer.addVertex(matrix,x, y, 0).setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);//.endVertex();
    }
}
