package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import dev.anye.mc.cores.dt.FadeColorData;
import dev.anye.mc.cores.dt.GlowData;
import dev.anye.mc.cores.dt.Quad;
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
 * 圆角矩形发光边框，无背景填充，如果需要填充背景可以使用{@link RoundedRectRenderState}
 * @param pipeline
 * @param textureSetup
 * @param pose
 * @param width
 * @param height
 * @param radius 边框宽度/圆角半径
 * @param borderColor
 * @param scissorArea      裁剪区域
 * @param bounds           渲染边界
 */
public record RoundedRectGlowDataBorderRenderState(
		RenderPipeline pipeline,
		TextureSetup textureSetup,
		Matrix3x2fStack pose, float x, float y, float width, float height, float radius, int borderColor,
		GlowData glowData,
		@Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) implements GuiElementRenderState {
	private static final Logger LOGGER = LogUtils.getLogger();

	public RoundedRectGlowDataBorderRenderState(
			Matrix3x2fStack pose, float x, float y,float width, float height, float radius, int borderColor,
			@Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) {
		this(RenderPipelines.GUI, TextureSetup.noTexture(), pose,x,y, width, height, radius, borderColor, new GlowData().setIntensity(2).setColor(borderColor), scissorArea, bounds);
	}





	@Override
	public void buildVertices(@NonNull VertexConsumer vertexConsumer) {
		if ( width < 0 || height < 0 || glowData == null) return;
		pose.pushMatrix();
		pose.translate(x,y);
		//实际边框 X
		float outerBorderX = glowData.outerGlowRange();
		//实际边框 Y
		float outerBorderY = glowData.outerGlowRange();
		//不含外发光的总体宽度
		float w = width - outerBorderX - outerBorderX;
		//不含外发光的总体高度
		float h = height - outerBorderY - outerBorderY;

		//不含外发光以及边框大小的宽度
		float borderW = w - radius - radius;
		//不含外发光以及边框大小的高度
		float borderH = h - radius - radius;
		//外发光
		if (glowData.outerGlowRange() > 0){
			pose.pushMatrix();

			//left
			pose.translate(0,outerBorderY + radius);
			FadeColorData fade = glowData.outerGlowColor().right();
			Render2DHelper.rect(vertexConsumer,pose,glowData.outerGlowRange(),borderH, fade);
			pose.translate(w + outerBorderX,0);

			//right
			fade = glowData.outerGlowColor();
			Render2DHelper.rect(vertexConsumer,pose,glowData.outerGlowRange(),borderH, fade);
			pose.popMatrix();

			pose.pushMatrix();
			pose.translate(outerBorderX + radius,0);

			//top
			fade = glowData.outerGlowColor().up();
			Render2DHelper.rect(vertexConsumer,pose,borderW,glowData.innerGlowRange(),fade);
			pose.translate(0,h + outerBorderY);

			//bottom
			fade = glowData.outerGlowColor().down();
			Render2DHelper.rect(vertexConsumer,pose,borderW,glowData.outerGlowRange(),fade);
			pose.popMatrix();

			fade = glowData.outerGlowColor().up();
			Render2DHelper.fan4(vertexConsumer,pose,glowData.outerGlowRange() + radius,radius,borderW, borderH,fade);
		}
		//边框
		if (radius > 0) {
			pose.pushMatrix();
			pose.translate(outerBorderX,outerBorderY);
			Render2DHelper.border(vertexConsumer,pose,w,h,radius,borderColor);
			Render2DHelper.fan4(vertexConsumer,pose,radius,0,borderW,borderH,borderColor);

			//pose.translate(radius,radius);
			//Render2DHelper.fan(vertexConsumer,pose, _Arc.cc(180), _Arc.cc(270),radius,0,borderColor);
//			pose.translate(w - radius - radius,0);
//			Render2DHelper.fan(vertexConsumer,pose,_MathCDT.ARC_270, _MathCDT.ARC_360,radius,0,borderColor);
//			pose.translate(0,h - radius - radius);
//			Render2DHelper.fan(vertexConsumer,pose,0, _MathCDT.ARC_90,radius,0,borderColor);
//			pose.translate(- w + radius + radius,0);
//			Render2DHelper.fan(vertexConsumer,pose,_MathCDT.ARC_90, _MathCDT.ARC_180,radius,0,borderColor);
			pose.popMatrix();
		}
		//内发光
		if (glowData.innerGlowRange() > 0){
			float innerX = outerBorderX + radius;
			float innerY = outerBorderY + radius;
			float innerW = width - 2 * glowData().outerGlowRange() - 2 * radius;
			float innerH = height - 2 * glowData().outerGlowRange() - 2 * radius;


			float innerSpaceWidth = innerW - glowData.innerGlowRange() * 2;
			float innerSpaceHeight = innerH - glowData.innerGlowRange() * 2;

			pose.translate(innerX,innerY);
			FadeColorData fade = glowData.innerGlowColor();
			Render2DHelper.rect(vertexConsumer,pose,Quad.normalRightTrapezoid(innerH,glowData.innerGlowRange(),innerSpaceHeight, glowData().innerGlowRange()),fade);

			fade = glowData.innerGlowColor().down();
			Render2DHelper.rect(vertexConsumer,pose,Quad.normalDownTrapezoid(innerW,glowData.innerGlowRange(),innerSpaceWidth, glowData().innerGlowRange()),fade);
			pose.pushMatrix();
			pose.translate(0,innerH - glowData.innerGlowRange());
			fade = glowData.innerGlowColor().up();
			Render2DHelper.rect(vertexConsumer,pose,Quad.normalUpTrapezoid(innerSpaceWidth,glowData.innerGlowRange(),innerW, glowData().innerGlowRange()),fade);
			pose.popMatrix();

			pose.translate(innerW - glowData.innerGlowRange(),0);
			fade = glowData.innerGlowColor().right();
			Render2DHelper.rect(vertexConsumer,pose,Quad.normalLeftTrapezoid(innerSpaceHeight,glowData.innerGlowRange(),innerH, glowData().innerGlowRange()),fade);
/*
			pose.translate(innerX,innerY);
			pose.pushMatrix();
			//left
			pose.translate(0,glowData.innerGlowRange());
			FadeColorData fade = glowData.innerGlowColor();
			Render2DHelper.rect(vertexConsumer,pose,glowData.innerGlowRange(),innerSpaceHeight, fade);
			//right
			pose.translate(innerSpaceWidth + glowData.innerGlowRange(),0);
			fade = glowData.innerGlowColor().lr();
			Render2DHelper.rect(vertexConsumer,pose,glowData.innerGlowRange(),innerSpaceHeight, fade);
			pose.popMatrix();

			pose.pushMatrix();
			//top
			pose.translate(glowData.innerGlowRange(),0);
			fade = glowData.innerGlowColor().down();
			Render2DHelper.rect(vertexConsumer,pose,innerSpaceWidth,glowData.innerGlowRange(),fade);

			//bottom
			pose.translate(0,innerSpaceHeight + glowData.innerGlowRange());
			fade = glowData.innerGlowColor().up();
			Render2DHelper.rect(vertexConsumer,pose,innerSpaceWidth,glowData.innerGlowRange(),fade);
			pose.popMatrix();

			pose.pushMatrix();
			fade = new FadeColorData(glowData.innerGlowColor().leftTopColor(),glowData.innerGlowColor().leftBottomColor(),glowData.innerGlowColor().leftTopColor(),glowData.innerGlowColor().rightTopColor());
			Render2DHelper.rect(vertexConsumer,pose, glowData().innerGlowRange(), glowData.innerGlowRange(),fade.tb());

			pose.translate(0,innerSpaceHeight + glowData.innerGlowRange());

			pose.pushMatrix();
			pose.translate(0,glowData.innerGlowRange());
			pose.rotate((float) _Arc.ccn(90));
			Render2DHelper.rect(vertexConsumer,pose, glowData().innerGlowRange(), glowData.innerGlowRange(),fade.tb());
			pose.popMatrix();

			pose.translate(innerSpaceWidth + glowData.innerGlowRange(),0);
			Render2DHelper.rect(vertexConsumer,pose, glowData().innerGlowRange(), glowData.innerGlowRange(),fade.up());

			pose.translate(0,- innerSpaceHeight - glowData.innerGlowRange());
			pose.pushMatrix();
			pose.translate(glowData.innerGlowRange(),0);
			pose.rotate((float) _Arc.cc(90));
			Render2DHelper.rect(vertexConsumer,pose, glowData().innerGlowRange(), glowData.innerGlowRange(),fade.tb());
			pose.popMatrix();
			pose.popMatrix();*/
		}

		pose.popMatrix();
	}
}