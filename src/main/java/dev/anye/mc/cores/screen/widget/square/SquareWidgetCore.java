package dev.anye.mc.cores.screen.widget.square;

import dev.anye.mc.cores.screen.widget.DT_XYWH;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public abstract class SquareWidgetCore<T extends SquareWidgetCore<T>> extends RenderWidgetCore<T> {
	public SquareWidgetCore(int x, int y, int w, int h, Component pMessage) {
		super(x, y, w, h, pMessage);
	}

	protected void drawSquare(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int color) {
		guiGraphics.fill(x, y, x + width, y + height, color);
	}

	protected void drawSquare(GuiGraphicsExtractor guiGraphics, DT_XYWH dt_xywh, int color) {
		drawSquare(guiGraphics, dt_xywh.x(), dt_xywh.y(), dt_xywh.width(), dt_xywh.height(), color);
	}

	protected void drawSquare(GuiGraphicsExtractor guiGraphics, int color) {
		drawSquare(guiGraphics, getX(), getY(), getWidth(), getHeight(), color);
	}

	@Override
	protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		if (this.visible) {
			renderContent(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
		}
	}

	protected abstract void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick);

}
