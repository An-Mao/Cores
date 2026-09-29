package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import dev.anye.mc.cores.render.Render2DHelper;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/**
 * 圆角矩形边框，无背景填充，如果需要填充背景可以使用{@link RoundedRectRenderState}
 * @param pipeline
 * @param textureSetup
 * @param pose
 * @param width
 * @param height
 * @param radius
 * @param borderColor
 * @param scissorArea      裁剪区域
 * @param bounds           渲染边界
 */
public record RoundedRectBorderRenderState(
		RenderPipeline pipeline,
		TextureSetup textureSetup,
		Matrix3x2fStack pose, float x, float y, float width, float height, float radius, int borderColor,
		@Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) implements GuiElementRenderState {
	private static final Logger LOGGER = LogUtils.getLogger();
			
	public RoundedRectBorderRenderState(
			Matrix3x2fStack pose, float x, float y,float width, float height, float radius, int borderColor,
			@Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) {
		this(RenderPipelines.GUI, TextureSetup.noTexture(), pose,x,y, width, height, radius, borderColor, scissorArea, bounds);
	}





	@Override
	public void buildVertices(@NonNull VertexConsumer vertexConsumer) {
		if ( width < 0 || height < 0) return;
		pose.pushMatrix();
		pose.translate(x,y);
		if (radius > 0) {
			pose.pushMatrix();
			Render2DHelper.border(vertexConsumer,pose,width,height,radius,borderColor);
			Render2DHelper.fan4(vertexConsumer,pose,radius,0,width - radius - radius, height - radius - radius,borderColor);
			pose.popMatrix();
		}
		pose.popMatrix();
	}
}