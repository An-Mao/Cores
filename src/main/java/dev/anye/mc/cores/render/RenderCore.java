package dev.anye.mc.cores.render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;

public abstract class RenderCore {
	public static void addVertex(BufferBuilder buffer, int x, int y, int color) {
		buffer.addVertex(x, y, 0).setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);
	}

	public static void addVertex(BufferBuilder buffer, Matrix4f matrix, int x, int y, int color) {
		buffer.addVertex(matrix, x, y, 0).setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);
	}

	public static void addVertex(VertexConsumer buffer, Matrix4f matrix, int x, int y, int color) {
		buffer.addVertex(matrix, x, y, 0).setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);
	}

	public static void addVertex(VertexConsumer buffer, Matrix4f matrix, float x, float y, int color) {
		buffer.addVertex(matrix, x, y, 0).setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);
	}

	public static void addVertex(VertexConsumer buffer, Matrix4f matrix, double x, double y, int color) {
		addVertex(buffer, matrix, (float) x, (float) y, color);
	}

}
