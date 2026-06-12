package dev.anye.mc.cores.screen.widget.square;

import dev.anye.mc.cores.render.GuiGraphicsX;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.List;

public class SquareButton extends SquareWidgetCore<SquareButton> {
	private final OnPress onPress;
	protected int textUsualColor, textSelectColor;
	private int dx = getX() + width / 2;
	private int dy = getY() + height / 2 - font.lineHeight / 2;

	public SquareButton(int x, int y, int w, int h, Component pMessage, OnPress onPress) {
		super(x, y, w, h, pMessage);
		this.onPress = onPress;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public void setTextSelectColor(int textSelectColor) {
		this.textSelectColor = textSelectColor;
	}

	public void setTextUsualColor(int textUsualColor) {
		this.textUsualColor = textUsualColor;
	}

	@Override
	public void onClick(MouseButtonEvent p_446284_, boolean p_434599_) {
		this.onPress.onPress();
	}

	/*
		@Override
		protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
			if (isMouseOver(pMouseX,pMouseY)){
				if (texture != null){
					DT_XYWHUV dt_xywhuv = bgSelect;
					if (dt_xywhuv != null) {
						drawImage(pGuiGraphics,dt_xywhuv);
					}
				}else {
					drawSquare(pGuiGraphics, backgroundHoverColor);
					pGuiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
					pGuiGraphics.drawCenteredString(font,getMessage(),dx,dy,textSelectColor);
					//DrawString(pGuiGraphics,getX() ,getY(),textSelectColor,getMessage());
				}
				pGuiGraphics.renderTooltip(font,getMessage(),pMouseX,pMouseY);
			}else {
				if (texture != null) {
					DT_XYWHUV dt_xywhuv = bgNormal;
					if (dt_xywhuv != null) {
						drawImage(pGuiGraphics,dt_xywhuv);
					}
				}else {
					drawSquare(pGuiGraphics, backgroundUsualColor);
					pGuiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
					pGuiGraphics.drawCenteredString(font,getMessage(),dx,dy,textUsualColor);
					//DrawString(pGuiGraphics,getX(),getY(),textUsualColor,getMessage());
				}
			}
		}


	 */
	@Override
	protected void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
		if (isMouseOver(mouseX, mouseY)) {
			drawSquare(guiGraphics, backgroundHoverColor);
			//RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
			guiGraphics.centeredText(font, getMessage(), dx, dy, textSelectColor);
			GuiGraphicsX.renderTooltip(guiGraphics, font, List.of(ClientTooltipComponent.create(getMessage().getVisualOrderText())), mouseX, mouseY);
		} else {
			drawSquare(guiGraphics, backgroundUsualColor);
			//RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
			//guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			guiGraphics.centeredText(font, getMessage(), dx, dy, textUsualColor);
		}
	}
}
