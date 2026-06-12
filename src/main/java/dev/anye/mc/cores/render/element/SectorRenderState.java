package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import dev.anye.core.math._Math;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public record SectorRenderState(
		RenderPipeline pipeline,
		TextureSetup textureSetup,
		Matrix3x2fStack pose, int x, int y, int outerRadius, double startArc, double endArc, int color,
		@Nullable ScreenRectangle scissorArea,
		@Nullable ScreenRectangle bounds) implements GuiElementRenderState {
	public static final double ANGLE_RESOLUTION = _Math.ARC;
	private static final Logger LOGGER = LogUtils.getLogger();

	public SectorRenderState(Matrix3x2fStack pose, int x, int y, int outerRadius, double startArc, double endArc, int color, @Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) {
		this(RenderPipelines.DEBUG_TRIANGLE_FAN, TextureSetup.noTexture(), pose, x, y, outerRadius, startArc, endArc, color, scissorArea, bounds);
	}

	@Override
	public void buildVertices(VertexConsumer vertexConsumer) {
		pose.pushMatrix();
		pose.translate(x(), y());
		double arc = endArc - startArc;
		vertexConsumer.addVertexWith2DPose(pose, 0, 0).setColor(color);
		int segments = (int) Math.ceil(Math.abs(arc) / ANGLE_RESOLUTION);
		if (segments < 1) segments = 1;
		for (int i = 0; i <= segments; i++) {
			float currentArc = (float) (startArc + arc * (double) i / segments);
			float x = Mth.cos(currentArc) * outerRadius;
			float y = Mth.sin(currentArc) * outerRadius;
			vertexConsumer.addVertexWith2DPose(pose, x, y).setColor(color);
		}
		pose.popMatrix();
	}
}
