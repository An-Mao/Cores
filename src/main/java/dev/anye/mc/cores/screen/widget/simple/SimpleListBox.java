package dev.anye.mc.cores.screen.widget.simple;

import com.mojang.logging.LogUtils;
import dev.anye.core.color.IStateColor;
import dev.anye.core.color._StateColors;
import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.debug._DeBug;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.dt.FadeColorData;
import dev.anye.mc.cores.dt.GlowData;
import dev.anye.mc.cores.render.GuiGraphicsHelper;
import dev.anye.mc.cores.render.SimpleBorderRender;
import dev.anye.mc.cores.screen.widget.SimpleListBoxData;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.joml.Matrix3x2fStack;
import org.slf4j.Logger;

import java.util.List;

public class SimpleListBox extends SimpleWidgetCore<SimpleListBox> {
	private static final Logger LOGGER = LogUtils.getLogger();
	protected List<SimpleListBoxData> data;
	protected int dataSize;
	protected int line;
	protected int row;
	protected int index;
	protected int startIndex;
	protected int elementalWidth;
	protected int elementalHeight;
	protected int widthSpace;
	protected int heightSpace;
	protected int strX = 1;
	protected int strY;
	protected int elementalBorderUsualColor;
	protected int elementalBorderHoverColor;
	protected int elementalTextUsualColor;
	protected int elementalTextHoverColor;
	protected int elementalBackgroundUsualColor;
	protected int elementalBackgroundHoverColor;
	protected final GlowData elementGlowData;

	public SimpleListBox(int x, int y, int w, int h, int elementalWidth, int elementalHeight, List<SimpleListBoxData> data) {
		super(x, y, w, h, Component.empty());
		elementGlowData = new GlowData().setEnable(Glow).setOuter(OuterGlow).setInner(InnerGlow).setInnerGlowRange(1).setOuterGlowRange(1);
		this.data = data;
		this.dataSize = this.data.size();
		this.elementalWidth = elementalWidth;
		this.elementalHeight = elementalHeight;
		this.widthSpace = 4;
		this.heightSpace = 4;
		this.index = -1;
		this.startIndex = 0;
		resetAutoSpace();
		setStrY();
		//setColorScheme(ColorSchemes.getGlobal());
	}

	@Override
	public SimpleListBox setColorScheme(_ColorScheme colorScheme) {
		super.setColorScheme(colorScheme);
		IStateColor color = colorScheme.elementBorder();
		this.elementalBorderHoverColor = color.hover().leftTopColor();
		this.elementalBorderUsualColor = color.normal().leftTopColor();
		color = colorScheme.elementText();
		this.elementalTextHoverColor = color.hover().leftTopColor();
		this.elementalTextUsualColor = color.normal().leftTopColor();
		color = colorScheme.elementBackground();
		this.elementalBackgroundHoverColor = color.hover().leftTopColor();
		this.elementalBackgroundUsualColor = color.normal().leftTopColor();
		return self();
	}

	public SimpleListBox setElementalTextHoverColor(int elementalTextHoverColor) {
		this.elementalTextHoverColor = elementalTextHoverColor;
		return self();
	}

	public int getElementalTextHoverColor() {
		return elementalTextHoverColor;
	}

	public SimpleListBox setElementalTextUsualColor(int elementalTextUsualColor) {
		this.elementalTextUsualColor = elementalTextUsualColor;
		return self();
	}

	public int getElementalTextUsualColor() {
		return elementalTextUsualColor;
	}

	public SimpleListBox setElementalBorderHoverColor(int elementalBorderHoverColor) {
		this.elementalBorderHoverColor = elementalBorderHoverColor;
		return self();
	}

	public int getElementalBorderHoverColor() {
		return elementalBorderHoverColor;
	}

	public SimpleListBox setElementalBorderUsualColor(int elementalBorderUsualColor) {
		this.elementalBorderUsualColor = elementalBorderUsualColor;
		return self();
	}

	public int getElementalBorderUsualColor() {
		return elementalBorderUsualColor;
	}

	public SimpleListBox setElementalBackgroundHoverColor(int elementalBackgroundHoverColor) {
		this.elementalBackgroundHoverColor = elementalBackgroundHoverColor;
		return self();
	}

	public int getElementalBackgroundHoverColor() {
		return elementalBackgroundHoverColor;
	}

	public SimpleListBox setElementalBackgroundUsualColor(int elementalBackgroundUsualColor) {
		this.elementalBackgroundUsualColor = elementalBackgroundUsualColor;
		return self();
	}

	public int getElementalBackgroundUsualColor() {
		return elementalBackgroundUsualColor;
	}

	public SimpleListBox setData(List<SimpleListBoxData> data) {
		this.data = data;
		this.dataSize = this.data.size();
		this.index = -1;
		this.startIndex = 0;
		return self();
	}

	@Override
	public SimpleListBox setFont(Font font) {
		super.setFont(font);
		setStrY();
		return self();
	}

	public void resetAutoSpace() {
		//计算宽度
		int i =  getWidthSpace() + getElementalWidth() + getWidthSpace();
		//计算每行数量
		this.row = getContentW() / i;
		//计算间距
		float space = (float) (getContentW() - row * getElementalWidth()) / row;
		//重置间距
		this.widthSpace = (int) (space / 2);

		//计算高度
		int hi = getElementalHeight() + getHeightSpace() * 2;
		this.line = getContentH() / hi;
		int hs = (getContentH() - line * hi) / line;
		this.heightSpace += _Math.half1(hs);
	}

	public void setStrY() {
		this.strY = _Math.half1(getElementalContentHeight() - font.lineHeight) + 1;
	}

	public int getStrY() {
		return strY;
	}

	public void setElementalHeight(int elementalHeight) {
		this.elementalHeight = elementalHeight;
		resetAutoSpace();
	}

	public int getElementalHeight() {
		return elementalHeight;
	}

	public void setElementalWidth(int elementalWidth) {
		this.elementalWidth = elementalWidth;
		resetAutoSpace();
	}

	public int getElementalWidth() {
		return elementalWidth;
	}

	public int getWidthSpace() {
		return widthSpace;
	}

	public void setWidthSpace(int widthSpace) {
		this.widthSpace = widthSpace;
		resetAutoSpace();
	}

	public int getHeightSpace() {
		return heightSpace;
	}

	public void setHeightSpace(int heightSpace) {
		this.heightSpace = heightSpace;
		resetAutoSpace();
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setLine(int line) {
		this.line = line;
	}


	@Override
	protected void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
		int idex = -1;
		for (int i = 0; i < line; i++) {
			int elemY = getContentY() + getHeightSpace() + i * (getElementalHeight() + getHeightSpace() + getHeightSpace());
			for (int r = 0; r < row; r++) {
				if (startIndex < dataSize) {
					int elemIndex = startIndex + i * row + r;
					if (elemIndex < dataSize) {
						int elemX = getContentX() + getWidthSpace() + r *(getWidthSpace() +  getElementalWidth() + getWidthSpace());
						int borderColor = getElementalBorderUsualColor();
						int backgroundColor = getElementalBackgroundUsualColor();
						int txtColor = getElementalTextUsualColor();
						boolean mouseOver = mouseX > elemX
								&& mouseX < elemX + elementalWidth
								&& mouseY > elemY
								&& mouseY < elemY + elementalHeight;
						FadeColorData glow = usualGlowColor;
						if (mouseOver) {
							if (hoverColor) {
								borderColor = getElementalBorderHoverColor();
								backgroundColor = getElementalBackgroundHoverColor();
								txtColor = getElementalTextHoverColor();
								glow = hoverGlowColor;
							}
							idex = elemIndex;
						}
						Matrix3x2fStack poseStack = guiGraphics.pose();
						SimpleBorderRender simpleBorderRender = new SimpleBorderRender(elementGlowData)
								.setBackgroundState(Background)
								.setRounded(Rounded)
								.set(elemX, elemY, elementalWidth, elementalHeight,getRadius())
								.set(poseStack)
								.setBackground(backgroundColor)
								.setBorderColor(borderColor)
								.setGlowColor(glow);
						guiGraphics.submitGuiElementRenderState(simpleBorderRender);
						drawString(guiGraphics, (int) (elemX + simpleBorderRender.borderTotalWidth() + strX), (int) (elemY + simpleBorderRender.borderTotalWidth() + getStrY()), txtColor, FixStrWidth(getDataComponent(elemIndex), (int) simpleBorderRender.cW()));
						//renderShape(guiGraphics, elemX, elemY, elementalWidth, elementalHeight, getRadius(), borderColor, backgroundColor);
					}
				} else {
					break;
				}
			}
		}
		if (idex != -1)
			GuiGraphicsHelper.renderTooltip(guiGraphics, font, getData(idex).tooltip(), mouseX, mouseY);
		index = idex;
	}

	public SimpleListBoxData getData(int index) {
		if (index < this.data.size()) {
			return this.data.get(index);
		}
		_DeBug.ThrowError("error index");
		return null;
	}

	public Component getDataComponent(int index) {
		SimpleListBoxData d = getData(index);
		if (d != null) {
			return d.name();
		}
		_DeBug.ThrowError("error data");
		return Component.literal("Error");
	}

	@Override
	public boolean mouseScrolled(double pMouseX, double pMouseY, double sx, double sy) {
		if (sy < 0 && startIndex < dataSize - row) {
			startIndex = startIndex + row;
		} else if (startIndex >= row) {
			startIndex = startIndex - row;
		}
		index = -1;
		return super.mouseScrolled(pMouseX, pMouseY, sx, sy);
	}

	@Override
	public void onClick(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
		onClick(mouseButtonEvent.x(), mouseButtonEvent.y());
	}

	public void onClick(double pMouseX, double pMouseY) {
		if (isMouseOver(pMouseX, pMouseY) && index >= 0) {
			SimpleListBoxData d = getData(index);
			if (d != null) {
				d.onPress(d.value());
			}
		}
	}

	public String FixStrWidth(String s,int w) {
		return font.plainSubstrByWidth(s, w);
	}

	public String FixStrWidth(Component s,int w) {
		return FixStrWidth(s.getString(),w);
	}

	public int getElementalContentWidth() {
		return getElementalWidth() - 2 * getRadius();
	}

	public int getElementalContentHeight() {
		if (elementGlowData == null) return getElementalHeight() - 2 * getRadius();
		return (int) (getElementalHeight() - 2 * (elementGlowData.innerGlowRange() + elementGlowData.outerGlowRange() + getRadius()));
	}
}
