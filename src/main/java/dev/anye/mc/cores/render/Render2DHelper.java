package dev.anye.mc.cores.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.anye.core.math._MathCDT;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;

public final class Render2DHelper {
	private Render2DHelper(){}

	/**
	 * 绘制扇形，使用4个顶点，可以设置內弧，需要QUADS管线
	 * @param vertexConsumer 顶点缓存
	 * @param pose 矩阵
	 * @param startArc 开始角度
	 * @param endArc 结束角度
	 * @param innerRadiusX 外圈半径 X
	 * @param innerRadiusY 外圈半径 Y
	 * @param outerRadiusX 内圈半径 X
	 * @param outerRadiusY 内圈半径 Y
	 * @param color 填充色
	 */
	public static void fan(VertexConsumer vertexConsumer, Matrix3x2fStack pose, double startArc, double endArc, float innerRadiusX, float innerRadiusY, float outerRadiusX, float outerRadiusY, int color){
		pose.pushMatrix();
		double arc = endArc - startArc;
		int segments = (int) Math.ceil(Math.abs(arc) / _MathCDT.ARC);
		if (segments < 1) segments = 1;
		double s = 1F / segments;
		for (int i = 0; i < segments; i++) {
			double a = i * s;
			float currentArc = (float) (startArc + arc * a);
			float nextArc = (float) (startArc + arc * (a + s));
			float x1Inner = Mth.cos(currentArc) * innerRadiusX;
			float y1Inner = Mth.sin(currentArc) * innerRadiusY;
			float x1Outer = Mth.cos(currentArc) * outerRadiusX;
			float y1Outer = Mth.sin(currentArc) * outerRadiusY;
			float x2Inner = Mth.cos(nextArc) * innerRadiusX;
			float y2Inner = Mth.sin(nextArc) * innerRadiusY;
			float x2Outer = Mth.cos(nextArc) * outerRadiusX;
			float y2Outer = Mth.sin(nextArc) * outerRadiusY;
			vertexConsumer.addVertexWith2DPose(pose, x1Inner, y1Inner).setColor(color);
			vertexConsumer.addVertexWith2DPose(pose, x1Outer, y1Outer).setColor(color);
			vertexConsumer.addVertexWith2DPose(pose, x2Outer, y2Outer).setColor(color);
			vertexConsumer.addVertexWith2DPose(pose, x2Inner, y2Inner).setColor(color);
		}
		pose.popMatrix();
	}
	/**
	 * 绘制扇形，需要FAN管线
	 * @param vertexConsumer ve
	 * @param pose pose
	 * @param startArc 开始角度
	 * @param endArc 结束角度
	 * @param color 颜色
	 * @param radius 半径
	 */
	public static void fan(VertexConsumer vertexConsumer, Matrix3x2fStack pose, double startArc, double endArc, int color, float radius){
		pose.pushMatrix();
		double arc = endArc - startArc;
		vertexConsumer.addVertexWith2DPose(pose, 0, 0).setColor(color);
		int segments = (int) Math.ceil(Math.abs(arc) / _MathCDT.ARC);
		if (segments < 1) segments = 1;
		for (int i = 0; i <= segments; i++) {
			float currentArc = (float) (startArc + arc * i / segments);
			float x = Mth.cos(currentArc) * radius;
			float y = Mth.sin(currentArc) * radius;
			vertexConsumer.addVertexWith2DPose(pose, x, y).setColor(color);
		}
		pose.popMatrix();
	}

	/**
	 * 1 —— 4
	 * |    |
	 * 2 —— 3
	 * 绘制无四角边框
	 * @param vertexConsumer 顶点缓存
	 * @param pose 矩阵
	 * @param width 宽度
	 * @param height 高度
	 * @param size 边框大小
	 * @param color 颜色
	 */
	public static void border(VertexConsumer vertexConsumer, Matrix3x2fStack pose,float width,float height,float size,int color){
		pose.pushMatrix();
		// Top border
		vertexConsumer.addVertexWith2DPose(pose, size, 0).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, size, size).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width - size, size).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width - size, 0).setColor(color);
		// Bottom border
		vertexConsumer.addVertexWith2DPose(pose, size, height - size).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, size, height).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width - size, height).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width - size, height - size).setColor(color);
		// Left border
		vertexConsumer.addVertexWith2DPose(pose, 0, size).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, 0, height - size).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, size, height - size).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, size, size).setColor(color);
		// Right border
		vertexConsumer.addVertexWith2DPose(pose, width - size, size).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width - size, height - size).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width, height - size).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width, size).setColor(color);
		pose.popMatrix();
	}

	/**
	 * 绘制矩形
	 * @param vertexConsumer 顶点缓存
	 * @param pose 变换矩阵
	 * @param width 宽度
	 * @param height 高度
	 * @param color 颜色
	 */
	public static void rect(VertexConsumer vertexConsumer, Matrix3x2fStack pose,float width,float height,int color){
		pose.pushMatrix();
		vertexConsumer.addVertexWith2DPose(pose, 0, 0).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, 0, height).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width, height).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width, 0).setColor(color);
		pose.popMatrix();
	}
}
