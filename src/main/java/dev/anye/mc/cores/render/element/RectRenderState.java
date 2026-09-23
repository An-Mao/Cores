package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;

import org.joml.Matrix3x2f;

import javax.annotation.Nullable;

/**
 * 矩形渲染
 * @param pipeline
 * @param textureSetup
 * @param pose
 * @param x
 * @param y
 * @param width
 * @param height
 * @param radius
 * @param fillColor
 * @param scissorArea
 * @param bounds
 */
public record RectRenderState(
		RenderPipeline pipeline,
		TextureSetup textureSetup,
		Matrix3x2f pose, float x, float y, float width, float height, float radius, int fillColor,
		@Nullable ScreenRectangle scissorArea,
		@Nullable ScreenRectangle bounds) implements GuiElementRenderState {
	@Override
	public void buildVertices(VertexConsumer vertexConsumer) {
		vertexConsumer.addVertexWith2DPose(pose, x + radius, y + radius).setColor(fillColor);
		vertexConsumer.addVertexWith2DPose(pose, x + radius, y + height - radius).setColor(fillColor);
		vertexConsumer.addVertexWith2DPose(pose, x + width - radius, y + height - radius).setColor(fillColor);
		vertexConsumer.addVertexWith2DPose(pose, x + width - radius, y + radius).setColor(fillColor);
	}
}
