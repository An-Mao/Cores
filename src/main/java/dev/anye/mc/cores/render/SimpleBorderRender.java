package dev.anye.mc.cores.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import dev.anye.mc.cores.dt.FadeColorData;
import dev.anye.mc.cores.dt.GlowData;
import dev.anye.mc.cores.dt.Quad;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.util.function.Consumer;

public class SimpleBorderRender implements GuiElementRenderState {
	private static final Logger LOGGER = LogUtils.getLogger();

	protected float x = 0;
	protected float y = 0;
	protected float width = 0;
	protected float height = 0;
	protected ScreenRectangle screenRectangle;
	protected Matrix3x2fStack pose;
	protected float borderThickness;

	/**
	 * 是否圆角
	 */
	protected boolean rounded = false;
	protected boolean background = false;
	protected FadeColorData backgroundColor;

	protected final GlowData glowData;
	protected FadeColorData oLeftFade;
	protected FadeColorData oBottomFade;
	protected FadeColorData oRightFade;
	protected FadeColorData oTopFade;

	protected FadeColorData iLeftFade;
	protected FadeColorData iBottomFade;
	protected FadeColorData iRightFade;
	protected FadeColorData iTopFade;

	protected FadeColorData bLeftFade = FadeColorData.EMPTY;
	protected FadeColorData bBottomFade = FadeColorData.EMPTY;
	protected FadeColorData bRightFade = FadeColorData.EMPTY;
	protected FadeColorData bTopFade = FadeColorData.EMPTY;

	protected float oX = 0;
	protected float oY = 0;
	/**
	 * 外发光宽度
	 */
	protected float oW = 0;
	/**
	 * 外发光高度
	 */
	protected float oH = 0;
	/**
	 * 外发光净宽度
	 */
	protected float oSW = 0;
	/**
	 * 外发光净高度
	 */
	protected float oSH = 0;
	protected Quad oTQ;
	protected Quad oLQ;
	protected Quad oRQ;
	protected Quad oBQ;

	protected float bX = 0;
	protected float bY = 0;
	/**
	 * 边框宽度
	 */
	protected float bW = 0;
	/**
	 * 边框高度
	 */
	protected float bH = 0;
	/**
	 * 边框净宽度
	 */
	protected float bSW = 0;
	/**
	 * 边框净高度
	 */
	protected float bSH = 0;

	protected float iX = 0;
	protected float iY = 0;
	protected float iW = 0;
	protected float iH = 0;
	protected float iSW = 0;
	protected float iSH = 0;
	protected Quad iTQ;
	protected Quad iLQ;
	protected Quad iRQ;
	protected Quad iBQ;

	protected float cX = 0;
	protected float cY = 0;
	protected float cW = 0;
	protected float cH = 0;

	protected float borderTotalWidth = 0;
	protected float borderTotalHeight = 0;

	public SimpleBorderRender(){
		this(new GlowData());
	}
	public SimpleBorderRender(GlowData glowData){
		this.glowData = glowData;
	}

	public SimpleBorderRender setBackgroundState(boolean state){
		this.background = state;
		return this;
	}

	public SimpleBorderRender setBackground(int color){
		return setBackground(FadeColorData.create(color));
	}
	public SimpleBorderRender setBackground(FadeColorData fadeColorData){
		this.backgroundColor = fadeColorData;
		return this;
	}

	public SimpleBorderRender setBorderColor(int color){
		return setBorderColor(FadeColorData.create(color));
	}

	public SimpleBorderRender setBorderColor(FadeColorData fade){
		return setBorderColor(fade,fade,fade,fade);
	}

	public SimpleBorderRender setBorderColor(FadeColorData topFade,FadeColorData leftFade,FadeColorData rightFade,FadeColorData bottomFade){
		this.bTopFade = topFade;
		this.bLeftFade = leftFade;
		this.bRightFade = rightFade;
		this.bBottomFade = bottomFade;
		return this;
	}

	public SimpleBorderRender setRounded(boolean rounded) {
		this.rounded = rounded;
		return this;
	}

	public SimpleBorderRender setOuterGlowColor(FadeColorData fadeColorData){
		glowData.setOuterGlowColor(fadeColorData);
		this.oLeftFade = glowData.outerGlowColor().right();
		this.oBottomFade = glowData.outerGlowColor().down();
		this.oRightFade = glowData.outerGlowColor();
		this.oTopFade = glowData.outerGlowColor().up();
		return this;
	}
	public SimpleBorderRender setInnerGlowColor(FadeColorData fadeColorData){
		glowData.setInnerGlowColor(fadeColorData);
		this.iLeftFade = glowData.innerGlowColor();
		this.iBottomFade = glowData.innerGlowColor().up();
		this.iRightFade = glowData.innerGlowColor().right();
		this.iTopFade = glowData.innerGlowColor().down();
		return this;
	}
	public SimpleBorderRender setGlowColor(FadeColorData color){
		return setGlowColor(color,color);
	}
	public SimpleBorderRender setGlowColor(FadeColorData outer,FadeColorData inner){
		setOuterGlowColor(outer);
		setInnerGlowColor(inner);
		return this;
	}
	public SimpleBorderRender setGlowColor(GlowData glowData){
		setGlowColor(glowData.outerGlowColor(),glowData.innerGlowColor());
		return this;
	}
	public void reset(){
		resetOuterPos();
		resetBorderPos();
		if (!rounded) {
			oTQ = Quad.normalDownTrapezoid(oW, glowData.outerGlowRange(), oSW, glowData.outerGlowRange());
			oLQ = Quad.normalRightTrapezoid(oH, glowData.outerGlowRange(), oSH, glowData.outerGlowRange());
			oRQ = Quad.normalLeftTrapezoid(oSH, glowData.outerGlowRange(), oH, glowData.outerGlowRange());
			oBQ = Quad.normalUpTrapezoid(oSW, glowData.outerGlowRange(), oW, glowData.outerGlowRange());
		}

		iTQ = Quad.normalDownTrapezoid(iW,glowData.innerGlowRange(),iSW, glowData.innerGlowRange());
		iLQ = Quad.normalRightTrapezoid(iH, glowData.innerGlowRange(), iSH, glowData.innerGlowRange());
		iRQ = Quad.normalLeftTrapezoid(iSH, glowData.innerGlowRange(), iH, glowData.innerGlowRange());
		iBQ = Quad.normalUpTrapezoid(iSW, glowData.innerGlowRange(), iW, glowData.innerGlowRange());
	}


	public SimpleBorderRender updateGlow(Consumer<GlowData> consumer){
		consumer.accept(this.glowData);
		reset();
		return this;
	}

	/**
	 * 重置内发光的尺寸，仅在glow不为null时生效
	 */
	public void resetInnerPos(){
		this.iX = this.bX + borderThickness;
		this.iY = this.bY + borderThickness;

		this.iW = this.bW - borderThickness - borderThickness;
		this.iH = this.bH - borderThickness - borderThickness;

		if (glowData.enable() && glowData.inner()){
			this.iSW = this.iW - glowData.innerGlowRange() - glowData.innerGlowRange();
			this.iSH = this.iH - glowData.innerGlowRange() - glowData.innerGlowRange();
			this.borderTotalWidth = this.iX + glowData.innerGlowRange();
			this.borderTotalHeight = this.iY + glowData.innerGlowRange();

			this.cX = this.x + this.borderTotalWidth;
			this.cY = this.y + this.borderTotalHeight;
			this.cW = this.iSW;
			this.cH = this.iSH;
		}else {
			this.borderTotalWidth = this.iX;
			this.borderTotalHeight = this.iY;

			this.cX = this.x + this.borderTotalWidth;
			this.cY = this.y + this.borderTotalHeight;
			this.cW = this.iW;
			this.cH = this.iH;
		}

	}


	/**
	 * 重置外发光的尺寸，仅在glow不为null时生效
	 */
	public void resetOuterPos(){
		if (glowData.enable() && glowData.outer()){
			this.oW = width;
			this.oH = height;
			this.oSW = this.oW - glowData.outerGlowRange() - glowData.outerGlowRange();
			this.oSH = this.oH - glowData.outerGlowRange() - glowData.outerGlowRange();
		}
	}


	/**
	 * 重置边框pos
	 */
	public void resetBorderPos(){
		if (glowData.enable() && glowData.outer()){
			this.bX = glowData.outerGlowRange();
			this.bY = glowData.outerGlowRange();

			this.bW = width - glowData.outerGlowRange() - glowData.outerGlowRange();
			this.bH = height - glowData.outerGlowRange() - glowData.outerGlowRange();
		}else {
			this.bX = 0;
			this.bY = 0;

			this.bW = width;
			this.bH = height;
		}

		this.bSW = bW - borderThickness - borderThickness;
		this.bSH = bH - borderThickness - borderThickness;
		resetInnerPos();
	}

	public SimpleBorderRender set(Matrix3x2fStack pose){
		this.pose = pose;
		return this;
	}

	public SimpleBorderRender set(float x, float y, float width, float height,float borderThickness){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setScreenRectangle();
		this.borderThickness = borderThickness;
		reset();
		return this;
	}
	public SimpleBorderRender setScreenRectangle(){
		return setScreenRectangle(new ScreenRectangle((int) x, (int) y, (int) width, (int) height));
	}

	public SimpleBorderRender setScreenRectangle(ScreenRectangle screenRectangle){
		this.screenRectangle = screenRectangle;
		return this;
	}

	@Override
	public void buildVertices(@NonNull VertexConsumer vertexConsumer) {
		if (pose == null || width < 0 || height < 0) return;
		pose.pushMatrix();
		pose.translate(x,y);
		background(vertexConsumer);
		outerGlow(vertexConsumer);

		border(vertexConsumer);

		innerGlow(vertexConsumer);
		pose.popMatrix();
	}

	public void background(VertexConsumer vertexConsumer){
		if (background && backgroundColor != null && iW > 0 && iH > 0){
			pose.pushMatrix();
			pose.translate(iX,iY);
			Render2DHelper.rect(vertexConsumer,pose,iW,iH,backgroundColor);
			pose.popMatrix();
		}
	}

	/**
	 * 渲染边框
	 * @param vertexConsumer 顶点
	 */
	public void border(VertexConsumer vertexConsumer){
		if (borderThickness > 0 && bW > 0 && bH > 0) {
			pose.pushMatrix();
			pose.translate(bX,bY);
			//top
			pose.pushMatrix();
			if (rounded) {
				pose.translate(borderThickness,0);
				Render2DHelper.rect(vertexConsumer,pose,bSW,borderThickness,bTopFade);
			}else Render2DHelper.rect(vertexConsumer,pose,bW,borderThickness,bTopFade);
			pose.popMatrix();
			//left
			pose.pushMatrix();
			pose.translate(0,borderThickness);
			Render2DHelper.rect(vertexConsumer,pose,borderThickness,bSH,bLeftFade);
			pose.popMatrix();
			//right
			pose.pushMatrix();
			pose.translate(borderThickness + bSW,borderThickness);
			Render2DHelper.rect(vertexConsumer,pose,borderThickness,bSH,bRightFade);
			pose.popMatrix();
			//bottom
			pose.pushMatrix();

			if (rounded){
				pose.translate(borderThickness,borderThickness + bSH);
				Render2DHelper.rect(vertexConsumer,pose,bSW,borderThickness,bBottomFade);
			} else {
				pose.translate(0,borderThickness + bSH);
				Render2DHelper.rect(vertexConsumer,pose,bW,borderThickness,bBottomFade);
			}
			pose.popMatrix();

			if (rounded) Render2DHelper.fan4(vertexConsumer,pose,borderThickness,0,bSW,bSH,bTopFade);

			pose.popMatrix();
		}
	}
	public void outerGlow(VertexConsumer vertexConsumer){
		if (glowData.enable() && glowData.outer() && glowData.outerGlowRange() > 0) {
			pose.pushMatrix();
			pose.translate(oX, oY);
			if (rounded) {
				float or = glowData.outerGlowRange() + borderThickness;
				float orW = oSW - borderThickness - borderThickness;
				float orH = oSH - borderThickness - borderThickness;
				//top
				pose.pushMatrix();
				pose.translate(or, 0);
				Render2DHelper.rect(vertexConsumer, pose, orW, glowData.outerGlowRange(), oTopFade);
				pose.popMatrix();
				//left
				pose.pushMatrix();
				pose.translate(0, or);
				Render2DHelper.rect(vertexConsumer, pose, glowData.outerGlowRange(), orH, oLeftFade);
				pose.popMatrix();
				//right
				pose.pushMatrix();
				pose.translate(oSW + glowData.outerGlowRange(), or);
				Render2DHelper.rect(vertexConsumer, pose, glowData.outerGlowRange(), orH, oRightFade);
				pose.popMatrix();
				//bottom
				pose.pushMatrix();
				pose.translate(or, oSH + glowData.outerGlowRange());
				Render2DHelper.rect(vertexConsumer, pose, orW, glowData.outerGlowRange(), oBottomFade);
				pose.popMatrix();
				//fan
				Render2DHelper.fan4(vertexConsumer, pose, or, borderThickness, orW, orH, oTopFade);
			} else {
				//top
				Render2DHelper.rect(vertexConsumer, pose, oTQ, oTopFade);
				//left
				Render2DHelper.rect(vertexConsumer, pose, oLQ, oLeftFade);
				//right
				pose.pushMatrix();
				pose.translate(oSW + glowData.outerGlowRange(), 0);
				Render2DHelper.rect(vertexConsumer, pose, oRQ, oRightFade);
				pose.popMatrix();
				//bottom
				pose.pushMatrix();
				pose.translate(0, oSH + glowData.outerGlowRange());
				Render2DHelper.rect(vertexConsumer, pose, oBQ, oBottomFade);
				pose.popMatrix();
			}
			pose.popMatrix();
		}
	}
	public void innerGlow(VertexConsumer vertexConsumer){
		if (glowData.enable() && glowData.inner() && glowData.innerGlowRange() > 0){
			pose.pushMatrix();
			pose.translate(iX,iY);
			//top
			Render2DHelper.rect(vertexConsumer,pose,iTQ,iTopFade);
			//left
			Render2DHelper.rect(vertexConsumer,pose,iLQ,iLeftFade);
			//right
			pose.pushMatrix();
			pose.translate(iW - glowData.innerGlowRange(),0);
			Render2DHelper.rect(vertexConsumer,pose,iRQ,iRightFade);
			pose.popMatrix();
			//bottom
			pose.translate(0,iH - glowData.innerGlowRange());
			Render2DHelper.rect(vertexConsumer,pose,iBQ,iBottomFade);
			pose.popMatrix();
		}
	}

	public void innerHorizontalLine(VertexConsumer vertexConsumer,float width){
		Render2DHelper.rect(vertexConsumer,pose,width,borderThickness,bTopFade);
	}

	/**
	 * 横向发光线条
	 * @param vertexConsumer
	 * @param width
	 */
	public void innerGlowHorizontalLine(VertexConsumer vertexConsumer,float width){
		if (glowData.enable() && glowData.innerGlowRange() > 0){
			//top
			Render2DHelper.rect(vertexConsumer,pose,width,glowData.innerGlowRange(),iBottomFade);
			//center
			Render2DHelper.rect(vertexConsumer,pose,width,borderThickness,bTopFade);
			//top
			Render2DHelper.rect(vertexConsumer,pose,width,glowData.innerGlowRange(),iTopFade);
		} else innerHorizontalLine(vertexConsumer,width);
	}
	/**
	 * 纵向发光线条
	 * @param vertexConsumer 顶点缓存
	 * @param height 高度
	 */
	public void innerGlowVerticalLine(VertexConsumer vertexConsumer,float height){
		if (glowData.enable() && glowData.innerGlowRange() > 0){
			//left
			Render2DHelper.rect(vertexConsumer,pose,glowData.innerGlowRange(),height,iRightFade);
			//center
			Render2DHelper.rect(vertexConsumer,pose,borderThickness,height,bTopFade);
			//top
			Render2DHelper.rect(vertexConsumer,pose,glowData.innerGlowRange(),height,iLeftFade);
		} else innerVerticalLine(vertexConsumer,height);
	}

	public void innerVerticalLine(VertexConsumer vertexConsumer,float height){
		Render2DHelper.rect(vertexConsumer,pose,borderThickness,height,bTopFade);
	}

	public SimpleBorderRender setY(float y) {
		this.y = y;
		setScreenRectangle();
		return this;
	}

	public SimpleBorderRender setX(float x) {
		this.x = x;
		setScreenRectangle();
		return this;
	}

	public float borderThickness() {
		return borderThickness;
	}
	public float borderTotalWidth() {
		return borderTotalWidth;
	}
	public float borderTotalHeight() {
		return borderTotalHeight;
	}
	public float iX() {
		return iX;
	}
	public float iY() {
		return iY;
	}
	public float iW() {
		return iW;
	}
	public float iH() {
		return iH;
	}
	public float iSW() {
		return iSW;
	}
	public float iSH() {
		return iSH;
	}
	public float cX() {
		return cX;
	}
	public float cY() {
		return cY;
	}
	public float cW() {
		return cW;
	}
	public float cH() {
		return cH;
	}

	@Override
	public @NonNull RenderPipeline pipeline() {
		return RenderPipelines.GUI;
	}
	@Override
	public @NonNull TextureSetup textureSetup() {
		return TextureSetup.noTexture();
	}
	@Override
	public @Nullable ScreenRectangle scissorArea() {
		return screenRectangle;
	}
	@Override
	public @Nullable ScreenRectangle bounds() {
		return screenRectangle;
	}
}
