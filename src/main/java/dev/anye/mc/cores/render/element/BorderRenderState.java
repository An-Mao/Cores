package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;

import org.joml.Matrix3x2f;

import javax.annotation.Nullable;

/**
 * 边框渲染
 * @param pipeline
 * @param textureSetup
 * @param pose
 * @param x
 * @param y
 * @param width
 * @param height
 * @param radius
 * @param borderColor
 * @param scissorArea
 * @param bounds
 */
public record BorderRenderState(
		RenderPipeline pipeline,
		TextureSetup textureSetup,
		Matrix3x2f pose, float x, float y, float width, float height, float radius, int borderColor,
		@Nullable ScreenRectangle scissorArea,
		@Nullable ScreenRectangle bounds) implements GuiElementRenderState {

	@Override
	public void buildVertices(VertexConsumer vertexConsumer) {
		// Top border
		vertexConsumer.addVertexWith2DPose(pose, x + radius, y).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + radius, y + radius).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + width - radius, y + radius).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + width - radius, y).setColor(borderColor);
		// Bottom border
		vertexConsumer.addVertexWith2DPose(pose, x + radius, y + height - radius).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + radius, y + height).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + width - radius, y + height).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + width - radius, y + height - radius).setColor(borderColor);
		// Left border
		vertexConsumer.addVertexWith2DPose(pose, x, y + radius).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x, y + height - radius).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + radius, y + height - radius).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + radius, y + radius).setColor(borderColor);
		// Right border
		vertexConsumer.addVertexWith2DPose(pose, x + width - radius, y + radius).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + width - radius, y + height - radius).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + width, y + height - radius).setColor(borderColor);
		vertexConsumer.addVertexWith2DPose(pose, x + width, y + radius).setColor(borderColor);
	}
}
