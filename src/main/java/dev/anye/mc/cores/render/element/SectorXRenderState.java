package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import dev.anye.mc.cores.render.Render2DHelper;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;

import javax.annotation.Nullable;

/**
 * 扇形渲染，可以设置内部弧形
 * @param pipeline
 * @param textureSetup
 * @param pose
 * @param x
 * @param y
 * @param innerRadiusX
 * @param innerRadiusY
 * @param outerRadiusX
 * @param outerRadiusY
 * @param startArc
 * @param endArc
 * @param color
 * @param scissorArea
 * @param bounds
 */
public record SectorXRenderState(
		RenderPipeline pipeline,
		TextureSetup textureSetup,
		Matrix3x2fStack pose, int x, int y, int innerRadiusX, int innerRadiusY, int outerRadiusX, int outerRadiusY,
		double startArc, double endArc, int color,
		@Nullable ScreenRectangle scissorArea,
		@Nullable ScreenRectangle bounds) implements GuiElementRenderState {

	public SectorXRenderState(
			Matrix3x2fStack pose, int x, int y, int innerRadiusX, int innerRadiusY, int outerRadiusX, int outerRadiusY, double startArc, double endArc, int color,
			@Nullable ScreenRectangle scissorArea,
			@Nullable ScreenRectangle bounds) {
		this(RenderPipelines.DEBUG_QUADS, TextureSetup.noTexture(), pose, x, y, innerRadiusX, innerRadiusY, outerRadiusX, outerRadiusY, startArc, endArc, color, scissorArea, bounds);
	}


	@Override
	public void buildVertices(@NotNull VertexConsumer vertexConsumer) {
		pose.pushMatrix();
		pose.translate(x, y);
		Render2DHelper.fan(vertexConsumer,pose,startArc,endArc,innerRadiusX,outerRadiusX,color);
		pose.popMatrix();
	}
}
