package nws.mc.cores.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import nws.dev.core.math._MathCDT;
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
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        //buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        for (double angle = startAngle; angle <= endAngle - arc; angle += arc) {
            float x2 = (float) (Math.cos(angle) *  outerRadius);
            float y2 = (float) (Math.sin(angle) * outerRadius);
            buffer.addVertex(x2, y2, 0).setColor(color);//.endVertex();
            float x1 = (float) (Math.cos(angle) * innerRadius);
            float y1 = (float) (Math.sin(angle) * innerRadius);
            buffer.addVertex(x1, y1, 0).setColor(color);//.endVertex();
        }

        BufferUploader.drawWithShader(buffer.buildOrThrow());
        //buffer.build().close();
        //tesselator.end();
    }

    public static void drawSector(Matrix4f matrix4f,int innerRadius,int outerRadius,double startArc,double endArc,int color){
        Tesselator tesselator = Tesselator.getInstance();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        for (double a = startArc; a < endArc; a += _MathCDT.ARC) {
            double x2 =  Math.cos(a) * outerRadius;
            double y2 =  Math.sin(a) * outerRadius;
            buffer.addVertex(matrix4f, (float) x2, (float) y2, 0).setColor(color);//endVertex();
            double x1 =  Math.cos(a) * innerRadius;
            double y1 =  Math.sin(a) * innerRadius;
            buffer.addVertex(matrix4f, (float) x1, (float) y1, 0).setColor(color);//endVertex();

        }
        BufferUploader.drawWithShader(buffer.buildOrThrow());
        //tesselator.end();
    }
}
