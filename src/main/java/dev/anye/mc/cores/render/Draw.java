package dev.anye.mc.cores.render;

import dev.anye.core.math._MathCDT;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

public class Draw{
    public static void drawSector(int outerRadius, double startAngle, double endAngle, int color){
        drawSector(0,outerRadius,startAngle,endAngle,color,_MathCDT.ARC);
    }
    public static void drawSector(int outerRadius, double startAngle, double endAngle, int color,double arc){
        drawSector(0,outerRadius,startAngle,endAngle,color,arc);
    }
    public static void drawSector(int innerRadius, int outerRadius, double startAngle, double endAngle, int color){
        drawSector(innerRadius,outerRadius,startAngle,endAngle,color,_MathCDT.ARC);
    }
    public static void drawSector(int innerRadius, int outerRadius, double startAngle, double endAngle, int color,double arc) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        for (double angle = startAngle; angle <= endAngle - arc; angle += arc) {
            double x2 = Math.cos(angle) * outerRadius;
            double y2 = Math.sin(angle) * outerRadius;
            buffer.vertex(x2, y2, 0).color(color).endVertex();
            double x1 = Math.cos(angle) * innerRadius;
            double y1 = Math.sin(angle) * innerRadius;
            buffer.vertex(x1, y1, 0).color(color).endVertex();

        }
        tesselator.end();
    }

    public static void drawSector(Matrix4f matrix4f,int innerRadius,int outerRadius,double startArc,double endArc,int color){
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        for (double a = startArc; a < endArc; a += _MathCDT.ARC) {
            double x2 =  Math.cos(a) * outerRadius;
            double y2 =  Math.sin(a) * outerRadius;
            buffer.vertex(matrix4f, (float) x2, (float) y2, 0).color(color).endVertex();
            double x1 =  Math.cos(a) * innerRadius;
            double y1 =  Math.sin(a) * innerRadius;
            buffer.vertex(matrix4f, (float) x1, (float) y1, 0).color(color).endVertex();

        }
        tesselator.end();
    }
}
