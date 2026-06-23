package dev.anye.mc.cores.render;

import dev.anye.core.color._ColorCDT;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.render.element.BorderRenderState;
import dev.anye.mc.cores.render.element.RectRenderState;
import dev.anye.mc.cores.render.element.SectorRenderState;
import dev.anye.mc.cores.render.element.SectorXRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.List;

public class GuiGraphicsX extends RenderCore {
	public static void Sector(GuiGraphicsExtractor guiGraphics, int x, int y, int outerRadius, double startArc, double endArc, int color) {
		guiGraphics.submitGuiElementRenderState(new SectorRenderState(
				guiGraphics.pose(),
				x, y,
				outerRadius,
				startArc,
				endArc,
				color,
				getBounds(x, y, outerRadius),
				getBounds(x, y, outerRadius)
		));
	}

	public static void SectorX(GuiGraphicsExtractor guiGraphics, int x, int y, int innerRadius, int outerRadius, double startArc, double endArc, int color) {
		if (innerRadius >= outerRadius) {
			if (innerRadius == 0) Sector(guiGraphics, x, y, outerRadius, startArc, endArc, color);
			return;
		}
		SectorX(guiGraphics, x, y, innerRadius, innerRadius, outerRadius, outerRadius, startArc, endArc, color);
	}

	public static void SectorX(GuiGraphicsExtractor guiGraphics, int x, int y, int innerRadiusX, int innerRadiusY, int outerRadiusX, int outerRadiusY, double startArc, double endArc, int color) {
		guiGraphics.submitGuiElementRenderState(new SectorXRenderState(
				guiGraphics.pose(),
				x, y,
				innerRadiusX,
				innerRadiusY,
				outerRadiusX,
				outerRadiusY,
				startArc,
				endArc,
				color,
				getBounds(x - outerRadiusX, y - outerRadiusY, outerRadiusX + outerRadiusX, outerRadiusY + outerRadiusY),
				getBounds(x - outerRadiusX, y - outerRadiusY, outerRadiusX + outerRadiusX, outerRadiusY + outerRadiusY)
		));
	}

	public static void RoundedRect(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
		Matrix3x2fStack poseStack = guiGraphics.pose();
		poseStack.pushMatrix();
		guiGraphics.submitGuiElementRenderState(new RectRenderState(
				RenderPipelines.GUI,
				TextureSetup.noTexture(),
				poseStack,
				x,
				y,
				width,
				height,
				radius,
				fillColor,
				getBounds(x, y, width, height),
				getBounds(x, y, width, height)
		));
		poseStack.popMatrix();

		poseStack.pushMatrix();
		guiGraphics.submitGuiElementRenderState(new BorderRenderState(
				RenderPipelines.GUI,
				TextureSetup.noTexture(),
				poseStack, x, y, width, height, radius, borderColor,
				getBounds(x, y, width, height),
				getBounds(x, y, width, height)));
		poseStack.popMatrix();


		Corners(guiGraphics, x, y, width, height, radius, borderColor);


	}

	public static void Corners(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int radius, int color) {

		int minX = x + radius, minY = y + radius, maxX = x + width - radius, maxY = y + height - radius;
		Matrix3x2fStack poseStack = guiGraphics.pose();
		poseStack.pushMatrix();
		GuiGraphicsX.SectorX(guiGraphics, minX, minY, 0, radius, _Math.ARC_180, _Math.ARC_270, color);
		GuiGraphicsX.SectorX(guiGraphics, maxX, minY, 0, radius, _Math.ARC_270, _Math.ARC_360, color);
		GuiGraphicsX.SectorX(guiGraphics, maxX, maxY, 0, radius, 0, _Math.ARC_90, color);
		GuiGraphicsX.SectorX(guiGraphics, minX, maxY, 0, radius, _Math.ARC_90, _Math.ARC_180, color);
		poseStack.popMatrix();
	}


	public static void DrawString(GuiGraphicsExtractor guiGraphics, String str, int x, int y) {
		DrawString(guiGraphics, Minecraft.getInstance().font, str, x, y, _ColorCDT.black);
	}

	public static void DrawString(GuiGraphicsExtractor guiGraphics, Font font, String str, int x, int y, int color) {
		guiGraphics.text(font, str, x, y, color);
	}

	public static void DrawString(GuiGraphicsExtractor guiGraphics, Font font, int x, int y, int color, boolean shadow, Component component) {
		guiGraphics.text(font, component, x, y, color, shadow);
	}

	public void DrawString(GuiGraphicsExtractor guiGraphics, int x, int y, int color, boolean shadow, Component component) {
		DrawString(guiGraphics, Minecraft.getInstance().font, x, y, color, shadow, component);
	}

	public void DrawString(GuiGraphicsExtractor guiGraphics, int x, int y, int color, Component component) {
		DrawString(guiGraphics, Minecraft.getInstance().font, x, y, color, false, component);
	}

	public void DrawString(GuiGraphicsExtractor guiGraphics, int x, int y, Component component) {
		DrawString(guiGraphics, Minecraft.getInstance().font, x, y, _ColorCDT.black, false, component);
	}

	public static Vector2ic positionTooltip(int screenWidth, int screenHeight, int mouseX, int mouseY, int tooltipWidth, int tooltipHeight) {
		return new Vector2i(mouseX + 16, mouseY);
	}

	public static void renderTooltip(GuiGraphicsExtractor guiGraphics, Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY) {
		guiGraphics.tooltip(font, components, mouseX, mouseY, GuiGraphicsX::positionTooltip, null);
	}

	public static ScreenRectangle getBounds(int x, int y, int w, int h) {
		return new ScreenRectangle(x, y, w, h);
	}

	public static ScreenRectangle getBounds(int x, int y, int r) {
		return new ScreenRectangle(x - r, y - r, r + r, r + r);
	}
}
