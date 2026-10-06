package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import dev.anye.mc.cores.render.Render2DHelper;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;

import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.NonNull;

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
		Matrix3x2fStack pose, float x, float y, float width, float height, float radius, int borderColor,
		@Nullable ScreenRectangle scissorArea,
		@Nullable ScreenRectangle bounds) implements GuiElementRenderState {

	@Override
	public void buildVertices(@NonNull VertexConsumer vertexConsumer) {
		pose.pushMatrix();
		pose.translate(x,y);
		Render2DHelper.border(vertexConsumer,pose,width,height,radius,borderColor);
		pose.popMatrix();
	}
}
