package dev.anye.mc.cores.screen.widget;

import com.mojang.blaze3d.platform.InputConstants;
import dev.anye.core.debug._DeBug;
import dev.anye.core.math._MathCDT;
import dev.anye.mc.cores.render.GuiGraphicsHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;

import java.util.List;


public class CircularWidget extends RenderWidgetCore<CircularWidget> {
	protected FlipMode flipMode;
	protected int sectors;
	protected int innerRadius;
	protected int outerRadius;
	protected double fanAngle;
	protected double fanArc;
	protected double halfFanArc;
	protected List<SimpleListBoxData> data;
	protected int index = -1;
	protected int startIndex = 0;
	protected int fanTextInnerSpace;

	public CircularWidget(int x, int y, int w, int h, Component message, SimpleListBoxData... data) {
		this(x, y, w, h, message, List.of(data));
	}

	public CircularWidget(int x, int y, int w, int h, Component message, List<SimpleListBoxData> data) {
		this(x, y, w, h, 9, 20, 80, message, data);
	}

	public CircularWidget(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, Component message, SimpleListBoxData... data) {
		this(x, y, w, h, sectors, innerRadius, outerRadius, message, List.of(data));
	}

	public CircularWidget(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, Component message, List<SimpleListBoxData> data) {
		super(x, y, w, h, message);
		if (sectors < 1) {
			_DeBug.ThrowError("error sectors");
		}
		setData(data);
		setSectors(sectors);
		setInnerRadius(innerRadius);
		setOuterRadius(outerRadius);
		setFlipMode(FlipMode.TIRE);
		setFanTextInnerSpace(10);
	}

	public CircularWidget(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, int highlightColor, int normalColor, Component message, SimpleListBoxData... data) {
		this(x, y, w, h, sectors, innerRadius, outerRadius, highlightColor, normalColor, message, List.of(data));
	}

	public CircularWidget(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, int highlightColor, int normalColor, Component message, List<SimpleListBoxData> data) {
		this(x, y, w, h, sectors, innerRadius, outerRadius, message, data);
		setBackgroundHoverColor(highlightColor);
		setBackgroundUsualColor(normalColor);
	}

	public void setData(List<SimpleListBoxData> data) {
		this.data = data;
	}

	public void setFanTextInnerSpace(int fanTextInnerSpace) {
		this.fanTextInnerSpace = fanTextInnerSpace;
	}

	public void setFlipMode(FlipMode flipMode) {
		this.flipMode = flipMode;
	}


	public void setFanArc(double fanArc) {
		this.fanArc = fanArc;
		setHalfFanArc(fanArc / 2d);
	}

	public void setHalfFanArc(double halfFanArc) {
		this.halfFanArc = halfFanArc;
	}


	public int getFanTextInnerSpace() {
		return Math.max(fanTextInnerSpace, innerRadius);
	}

	public void setFanAngle(double fanAngle) {
		this.fanAngle = fanAngle;
	}

	public void setOuterRadius(int outerRadius) {
		this.outerRadius = outerRadius;
	}

	public void setSectors(int sectors) {
		this.sectors = sectors;
		setFanAngle(360d / sectors);
		setFanArc(_MathCDT.TWICE_PI / sectors);
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	public SimpleListBoxData getData() {
		return getData(index);
	}

	public SimpleListBoxData getData(int index) {
		if (isValidIndex(index)) {
			return data.get(index);
		}
		return null;
	}

	public boolean isValidIndex() {
		return isValidIndex(index);
	}

	public boolean isValidIndex(int index) {
		return index >= 0 && index < data.size();
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {

		return mouseClicked(mouseButtonEvent.x(), mouseButtonEvent.y(), mouseButtonEvent.button())
						|| super.mouseClicked(mouseButtonEvent, doubleClick);

	}

	public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
		if (this.active && this.visible && this.flipMode == FlipMode.BUTTON) {
			if (pButton == InputConstants.MOUSE_BUTTON_LEFT) {
				if (startIndex >= sectors) {
					startIndex -= sectors;
					return true;
				}
			} else if (pButton == InputConstants.MOUSE_BUTTON_RIGHT) {
				if (data.size() > startIndex + sectors) {
					startIndex += sectors;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onClick(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
		onClick(mouseButtonEvent.x(), mouseButtonEvent.y(), mouseButtonEvent.button());
	}

	public void onClick(double pMouseX, double pMouseY, int button) {
		if (flipMode == FlipMode.TIRE) {
			SimpleListBoxData dtListBoxData = getData();
			if (dtListBoxData != null) {
				dtListBoxData.onPress(dtListBoxData.value());
			}
		}
	}

	@Override
	public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
		if (this.flipMode == FlipMode.TIRE) {
			if (pScrollY > 0) {
				if (startIndex >= sectors) {
					startIndex -= sectors;
					return true;
				}
			} else {
				if (data.size() > startIndex + sectors) {
					startIndex += sectors;
					return true;
				}
			}
			return false;
		}
		return super.mouseScrolled(pMouseX, pMouseY, pScrollX, pScrollY);
	}

	@Override
	protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float v) {
		if (visible) {
			int centerX = getX();
			int centerY = getY();
			double angle = Math.atan2(mouseY - centerY, mouseX - centerX) + halfFanArc;
			if (angle < 0) {
				angle += _MathCDT.TWICE_PI;
			}
			for (int i = 0; i < sectors; i++) {
				int sIndex = startIndex + i;
				double startAngle = i * fanArc;
				double endAngle = (i + 1) * fanArc;

				int bgc = getBackgroundUsualColor(), tc = getTextUsualColor();
				float size = 1;
				if (angle >= startAngle && angle < endAngle) {
					bgc = getBackgroundHoverColor();
					tc = getTextHoverColor();
					size = 1.3f;
					this.index = sIndex;
				}
				Matrix3x2fStack poseStack = guiGraphics.pose();
				poseStack.pushMatrix();
				//poseStack.translate(centerX,centerY);
				/*
				 * TODO
				 *  mul
				 */
				//poseStack.mu(Axis.ZP.rotation((float) (startAngle)));
				//poseStack.mul(Axis.ZP.rotation((float) (startAngle)).);
				GuiGraphicsHelper.SectorX(guiGraphics, centerX, centerY, innerRadius + 10, outerRadius, -halfFanArc, halfFanArc, bgc);
				if (isValidIndex(sIndex)) {
					SimpleListBoxData boxData = getData(sIndex);
					drawName(guiGraphics, startAngle, boxData.name().getString(), tc, size);
				}
				poseStack.popMatrix();
			}
		}
	}

	public void drawTextName(GuiGraphicsExtractor guiGraphics, String name, int color) {
		name = font.plainSubstrByWidth(name, outerRadius - getFanTextInnerSpace());
		guiGraphics.text(font, name, getFanTextInnerSpace(), -font.lineHeight / 2, color, false);
	}

	protected void drawName(GuiGraphicsExtractor guiGraphics, double rad, String name, int color, float size) {
		Identifier res = Identifier.tryParse(name);
		Matrix3x2fStack pose = guiGraphics.pose();
		pose.pushMatrix();
		if (res != null) {
			float r = (float) (getFanTextInnerSpace() + (outerRadius - innerRadius) / 2d);
			float x2 = (float) (Math.cos(rad) * r);
			float y2 = (float) (Math.sin(rad) * r);
			//pose.mulPose(Axis.ZP.rotation((float) -rad));
			pose.translate(x2, y2);
			pose.scale(size, size);
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, res, -8, -8, 0, 0, 0, 16, 16, 16, 16);
		} else {
			pose.scale(size, size);
			drawTextName(guiGraphics, name, color);
		}
		pose.popMatrix();
	}

	public enum FlipMode {
		TIRE,
		BUTTON
	}
}
