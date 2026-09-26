package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import dev.anye.core.math._Arc;
import dev.anye.core.math._MathCDT;
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
 * 圆角矩形，含背景填充，如果不需要填充背景可以使用{@link RoundedRectBorderRenderState}
 * @param pipeline
 * @param textureSetup
 * @param pose
 * @param width
 * @param height
 * @param radius
 * @param borderColor
 * @param fillColor        内部填充颜色
 * @param scissorArea      裁剪区域
 * @param bounds           渲染边界
 */
public record RoundedRectRenderState(
		RenderPipeline pipeline,
		TextureSetup textureSetup,
		Matrix3x2fStack pose, float x, float y, float width, float height, float radius, int borderColor,int fillColor,
		@Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) implements GuiElementRenderState {
	private static final Logger LOGGER = LogUtils.getLogger();

	public RoundedRectRenderState(
			Matrix3x2fStack pose, float x, float y,float width, float height, float radius, int borderColor,int fillColor,
			@Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) {
		this(RenderPipelines.GUI, TextureSetup.noTexture(), pose,x,y, width, height, radius, borderColor,fillColor, scissorArea, bounds);
	}





	@Override
	public void buildVertices(@NonNull VertexConsumer vertexConsumer) {
		if ( width < 0 || height < 0) return;
		pose.pushMatrix();
		pose.translate(x,y);
		if (radius > 0) {
			pose.pushMatrix();
			Render2DHelper.border(vertexConsumer,pose,width,height,radius,borderColor);
			pose.translate(radius,radius);
			Render2DHelper.fan(vertexConsumer,pose,_Arc.c(180), _Arc.c(270),radius,0,borderColor);
			pose.translate(width - radius - radius,0);
			Render2DHelper.fan(vertexConsumer,pose,_Arc.c(270), _Arc.c(360),radius,0,borderColor);
			pose.translate(0,height - radius - radius);
			Render2DHelper.fan(vertexConsumer,pose, _Arc.c(0), _Arc.c(90),radius,0,borderColor);
			pose.translate(- width + radius + radius,0);
			Render2DHelper.fan(vertexConsumer,pose,_Arc.c(90), _Arc.c(180),radius,0,borderColor);
			pose.popMatrix();
		}
		pose.translate(radius,radius);
		Render2DHelper.rect(vertexConsumer,pose,width - radius - radius ,height - radius - radius,fillColor);
		pose.popMatrix();
	}
}