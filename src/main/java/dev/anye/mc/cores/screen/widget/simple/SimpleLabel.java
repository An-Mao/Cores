package dev.anye.mc.cores.screen.widget.simple;

import dev.anye.core.math._Math;
import dev.anye.mc.cores.render.GuiGraphicsHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class SimpleLabel extends SimpleWidgetCore<SimpleLabel> {
	private int drawX;
	private int drawY;
	private boolean autoWidth;
	private boolean autoHeight;
	private boolean centerText;

	public SimpleLabel(int x, int y, int w, int h, Component pMessage) {
		super(x, y, w, h, pMessage);
		setAutoWidth(autoWidth);
		setAutoHeight(autoHeight);
		setCenterText(centerText);
		setHoverColor(false);
	}


	@Override
	public void setMessage(@NonNull Component message) {
		super.setMessage(message);
		setAutoWidth(isAutoWidth());
	}

	public boolean isAutoWidth() {
		return autoWidth;
	}

	public SimpleLabel setAutoWidth(boolean autoWidth) {
		this.autoWidth = autoWidth;
		if (this.autoWidth) {
			setWidth((int) (font.width(getMessage()) + borderRender.borderTotalWidth() * 2 + 10));
			setCenterText(centerText);
		}
		return this;
	}

	public boolean isAutoHeight() {
		return autoHeight;
	}

	public SimpleLabel setAutoHeight(boolean autoHeight) {
		this.autoHeight = autoHeight;
		if (this.autoHeight) {
			setHeight((int) (font.lineHeight + borderRender.borderTotalHeight() * 2 + 4));
			setCenterText(centerText);
		}
		return this;
	}

	public boolean isCenterText() {
		return centerText;
	}

	public SimpleLabel setCenterText(boolean centerText) {
		this.centerText = centerText;
		if (this.centerText) {

			setDrawX(getContentX() + _Math.half(getContentW()));
			setDrawY(getContentY() + _Math.half(getContentH()) - _Math.half(font.lineHeight));
		} else {
			setDrawX(getContentX());
			setDrawY(getContentY());
		}
		return this;
	}

	public SimpleLabel setDrawX(int drawX) {
		this.drawX = drawX;
		return this;
	}

	public int getDrawX() {
		return drawX;
	}

	public SimpleLabel setDrawY(int drawY) {
		this.drawY = drawY;
		return this;
	}

	public int getDrawY() {
		return drawY;
	}

	@Override
	protected void renderContent(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		//guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
		boolean mouseOver = hoverColor && isMouseOver(pMouseX, pMouseY);
		int tc = mouseOver ? getTextHoverColor() : getTextUsualColor();
		if (isCenterText()) {
			guiGraphics.text(font, getMessage(), getDrawX() - _Math.half(font.width(getMessage())), getDrawY(), tc, false);
			//guiGraphics.drawCenteredString(font,getMessage(),getDrawX(),getDrawY(), tc);
		} else {
			guiGraphics.text(font, getMessage(), getDrawX(), getDrawY(), tc, false);
		}
		if (mouseOver && getCustomTooltip() != null && !getCustomTooltip().isEmpty()) {
			GuiGraphicsHelper.renderTooltip(guiGraphics, getFont(), getCustomTooltip(), pMouseX, pMouseY);
		}

	}
}
