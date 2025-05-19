package dev.anye.mc.cores.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RouletteMenu extends RenderWidgetCore<RouletteMenu> {
    private int sectors = 9;
    private int outerRadius = 80;
    private int highlightColor = 0x50646464;
    private int normalColor = 0x50898989;
    private double addAngle = Math.PI / (5 * outerRadius);
    public RouletteMenu(int x, int y, int w, int h, Component pMessage) {
        super(x, y, w, h, pMessage);
    }

    public void setAddAngle(double addAngle) {
        this.addAngle = addAngle;
    }

    public void setHighlightColor(int highlightColor) {
        this.highlightColor = highlightColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public void setOuterRadius(int outerRadius) {
        this.outerRadius = outerRadius;
    }

    public void setSectors(int sectors) {
        this.sectors = sectors;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (visible){
            int centerX = getX();
            int centerY = getY();
            double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
            if (angle < 0) {
                angle += Math.PI * 2;
            }
            double sectorAngle =  2 * Math.PI / sectors;
            Tesselator tesselator = Tesselator.getInstance();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            buffer.addVertex(centerX, centerY, 0.0f).setColor(normalColor);//.endVertex();

            for (int i = 0; i < sectors; i++) {
                double startAngle = i * sectorAngle;
                double endAngle = (i + 1) * sectorAngle;
                int bgColor = (angle >= startAngle && angle < endAngle) ? highlightColor : normalColor;
                for (double a = startAngle; a < endAngle; a += Math.PI / 180) {
                    float x2 = (float) (centerX + Math.cos(a) * outerRadius);
                    float y2 = (float) (centerY + Math.sin(a) * outerRadius);
                    buffer.addVertex(x2, y2, 0).setColor(bgColor);//.endVertex();
                }
            }
            BufferUploader.drawWithShader(buffer.buildOrThrow());
            //tesselator.end();
        }
    }
}
