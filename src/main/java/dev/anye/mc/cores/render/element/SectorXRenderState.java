package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;

import javax.annotation.Nullable;

public record SectorXRenderState(
        RenderPipeline pipeline,
        TextureSetup textureSetup,
        Matrix3x2fStack pose, int x, int y, int innerRadiusX, int innerRadiusY, int outerRadiusX, int outerRadiusY, double startArc, double endArc, int color,
        @Nullable ScreenRectangle scissorArea,
        @Nullable ScreenRectangle bounds) implements GuiElementRenderState {

    public SectorXRenderState(
            Matrix3x2fStack pose,int x,int y, int innerRadiusX, int innerRadiusY, int outerRadiusX, int outerRadiusY, double startArc, double endArc, int color,
            @Nullable ScreenRectangle scissorArea,
            @Nullable ScreenRectangle bounds){
        this(RenderPipelines.DEBUG_QUADS,TextureSetup.noTexture(),pose,x,y,innerRadiusX,innerRadiusY,outerRadiusX,outerRadiusY,startArc,endArc,color,scissorArea,bounds);
    }


    @Override
    public void buildVertices(@NotNull VertexConsumer vertexConsumer) {
        pose.pushMatrix();
        pose.translate(x,y);
        double arc = endArc - startArc;
        int segments = (int) Math.ceil(Math.abs(arc) / SectorRenderState.ANGLE_RESOLUTION);
        if (segments < 1) segments = 1;
        for (int i = 0; i < segments; i++) {
            double currentArc = startArc + (arc) * ((double) i / segments);
            double nextArc = startArc + arc* ((double) (i + 1) / segments);
            float x1_inner = Mth.cos((float)currentArc) * innerRadiusX;
            float y1_inner = Mth.sin((float)currentArc) * innerRadiusY;
            float x1_outer = Mth.cos((float)currentArc) * outerRadiusX;
            float y1_outer = Mth.sin((float)currentArc) * outerRadiusY;
            float x2_inner = Mth.cos((float)nextArc) * innerRadiusX;
            float y2_inner = Mth.sin((float)nextArc) * innerRadiusY;
            float x2_outer = Mth.cos((float)nextArc) * outerRadiusX;
            float y2_outer = Mth.sin((float)nextArc) * outerRadiusY;
            vertexConsumer.addVertexWith2DPose(pose, x1_inner, y1_inner).setColor(color);
            vertexConsumer.addVertexWith2DPose(pose, x1_outer, y1_outer).setColor(color);
            vertexConsumer.addVertexWith2DPose(pose, x2_outer, y2_outer).setColor(color);
            vertexConsumer.addVertexWith2DPose(pose, x2_inner, y2_inner).setColor(color);
        }
        pose.popMatrix();
    }
}
