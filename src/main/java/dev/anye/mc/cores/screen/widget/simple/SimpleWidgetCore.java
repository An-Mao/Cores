package dev.anye.mc.cores.screen.widget.simple;

import com.mojang.logging.LogUtils;
import dev.anye.core.color._ColorSupport;
import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.am.config.Configs;
import dev.anye.mc.cores.am.config.general.GeneralConfigData;
import dev.anye.mc.cores.dt.FadeColorData;
import dev.anye.mc.cores.dt.GlowData;
import dev.anye.mc.cores.render.GuiGraphicsHelper;
import dev.anye.mc.cores.render.SimpleBorderRender;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;
import org.slf4j.Logger;


public abstract class SimpleWidgetCore<T extends SimpleWidgetCore<T>> extends RenderWidgetCore<T> {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static boolean Background = Configs.GENERAL.fetch(GeneralConfigData::simpleWidgetBackground,true);
	private static boolean Rounded = Configs.GENERAL.fetch(GeneralConfigData::simpleWidgetRounded,false);
	private static boolean Glow = Configs.GENERAL.fetch(GeneralConfigData::simpleWidgetGlow,false);

	protected int radius;
	protected int borderUsualColor;
	protected int borderHoverColor;
	protected int borderSelectColor;
	protected int contentX;
	protected int contentY;
	protected int contentW;
	protected int contentH;
	protected int contentEndX;
	protected int contentEndY;

	protected GlowData glowData = GlowData.Builder().setInnerGlowRange(2).setOuterGlowRange(2).setIntensity(2).setColor(0xffffffff).build();
	protected GlowData usualGlowColor = null;
	protected GlowData hoverGlowColor = null;

	protected final SimpleBorderRender borderRender = new SimpleBorderRender()
			.setBackgroundState(Background)
			.setRounded(Rounded)
			.setGlow(Glow ? glowData : null);

	public static void setBackground() {
		Background = !Background;
		Configs.GENERAL.update(generalConfigData -> generalConfigData.setSimpleWidgetBackground(Background));
		Configs.GENERAL.save();
	}

	public static void setRounded() {
		Rounded = !Rounded;
		Configs.GENERAL.update(generalConfigData -> generalConfigData.setsimpleWidgetRounded(Rounded));
		Configs.GENERAL.save();
	}

	public static void setGlow() {
		Glow = !Glow;
		Configs.GENERAL.update(generalConfigData -> generalConfigData.setsimpleWidgetGlow(Glow));
		Configs.GENERAL.save();
	}

	protected SimpleWidgetCore(int x, int y, int w, int h, Component pMessage) {
		this(x, y, w, h, 2, pMessage);
	}

	protected SimpleWidgetCore(int x, int y, int w, int h, int r, Component pMessage) {
		super(x, y, w, h, pMessage);
		setRadius(r);
		usualGlowColor = GlowData.Builder().setInnerGlowRange(2).setOuterGlowRange(2).setIntensity(2).setColor(borderUsualColor).build();
		hoverGlowColor = GlowData.Builder().setInnerGlowRange(2).setOuterGlowRange(2).setIntensity(2).setColor(borderHoverColor).build();
	}

	@Override
	public T setColorScheme(_ColorScheme colorScheme) {
		super.setColorScheme(colorScheme);
		setBorderUsualColor(colorScheme.getColor("border").UsualColor());
		setBorderHoverColor(colorScheme.getColor("border").HoverColor());
		setBorderSelectColor(colorScheme.getColor("border").SelectColor());
		return self();
	}

	public void refreshRender() {
		borderRender.set(getX(),getY(),getWidth(),getHeight(),getRadius());
		setContentW((int) borderRender.cW());
		setContentH((int) borderRender.cH());

		setContentX((int) borderRender.cX());
		setContentY((int) borderRender.cY());
	}
	@Override
	public void setX(int x) {
		super.setX(x);
		refreshRender();
	}

	@Override
	public void setY(int y) {
		super.setY(y);
		refreshRender();
	}


	@Override
	public void setWidth(int pWidth) {
		super.setWidth(pWidth);
		setRadius(getRadius());
		refreshRender();
	}

	@Override
	public void setHeight(int pHeight) {
		super.setHeight(pHeight);
		setRadius(getRadius());
		refreshRender();
	}

	//-------------------------------------
	public T setBorderSelectColor(int borderSelectColor) {
		this.borderSelectColor = borderSelectColor;
		return self();
	}

	public T setBorderUsualColor(int borderUsualColor) {
		this.borderUsualColor = borderUsualColor;
		LOGGER.debug("borderUsualColor {}", _ColorSupport.intToHexColor(borderUsualColor));
		usualGlowColor = GlowData.Builder().setInnerGlowRange(2).setOuterGlowRange(2).setIntensity(2).setColor(borderUsualColor).build();
		return self();
	}

	public int getBorderUsualColor() {
		return borderUsualColor;
	}

	public T setBorderHoverColor(int borderHoverColor) {
		this.borderHoverColor = borderHoverColor;
		hoverGlowColor = GlowData.Builder().setInnerGlowRange(2).setOuterGlowRange(2).setIntensity(2).setColor(borderHoverColor).build();
		return self();
	}

	public int getBorderHoverColor() {
		return borderHoverColor;
	}

	public T setRadius(int radius) {
		this.radius = radius;
		refreshRender();
		return self();
	}

	public int getRadius() {
		return radius;
	}

	public T setContentH(int contentH) {
		this.contentH = contentH;
		setContentEndY(getContentY() + this.contentH);
		return self();
	}

	public int getContentH() {
		return contentH;
	}

	public T setContentW(int contentW) {
		this.contentW = contentW;
		setContentEndX(getContentX() + this.contentW);
		return self();
	}

	public int getContentW() {
		return contentW;
	}

	public T setContentX(int contentX) {
		this.contentX = contentX;
		setContentEndX(this.contentX + getContentW());
		return self();
	}

	public int getContentX() {
		return contentX;
	}

	public T setContentY(int contentY) {
		this.contentY = contentY;
		setContentEndY(this.contentY + getContentH());
		return self();
	}

	public int getContentY() {
		return contentY;
	}

	public T setContentEndX(int contentEndX) {
		this.contentEndX = contentEndX;
		return self();
	}

	public int getContentEndX() {
		return contentEndX;
	}

	public T setContentEndY(int contentEndY) {
		this.contentEndY = contentEndY;
		return self();
	}

	public int getContentEndY() {
		return contentEndY;
	}

	//-------------------------------------
	protected void renderShape(GuiGraphicsExtractor poseStack, int borderColor, int fillColor) {
		renderShape(poseStack, getX(), getY(), getWidth(), getHeight(), getRadius(), borderColor, fillColor);
	}

	protected void renderShape(GuiGraphicsExtractor poseStack, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
		GuiGraphicsHelper.RoundedRect(poseStack, x, y, width, height, radius, borderColor, fillColor);
	}

	@Override
	protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		if (this.visible) {
			int borderColor;
			int fillColor;
			GlowData glow;
			if (hoverColor && isMouseOver(pMouseX, pMouseY)) {
				borderColor = getBorderHoverColor();
				fillColor = getBackgroundHoverColor();
				glow = hoverGlowColor;
			} else {
				borderColor = getBorderUsualColor();
				fillColor = getBackgroundUsualColor();
				glow = usualGlowColor;
			}
			Matrix3x2fStack poseStack = guiGraphics.pose();

			/*guiGraphics.submitGuiElementRenderState(
					new SimpleBorderRender(poseStack,
							x,y,
							width, height,
							radius,
							borderColor
					)
							.setBackgroundState(true)
							.setBackground(fillColor)
							.setRounded(true)
							.setGlow(GlowData.Builder().setIntensity(2).setColor(borderColor).build())
			);*/

			//LOGGER.debug("usualGlowColor => {},{},{},{}",usualGlowColor.innerGlowColor(),usualGlowColor.outerGlowColor(),outerFade,innerFade);

			poseStack.pushMatrix();
			guiGraphics.submitGuiElementRenderState(
					borderRender.set(poseStack).setBackground(fillColor).setBorderColor(borderColor).setGlowColor(Glow ? glow : null)
			);
			//poseStack.translate(getX(), getY());
			//renderShape(guiGraphics, borderColor, fillColor);
			renderContent(guiGraphics, pMouseX, pMouseY, pPartialTick);
			poseStack.popMatrix();

		}
	}


	protected abstract void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick);
}
