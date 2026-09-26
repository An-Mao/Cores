package dev.anye.mc.cores.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.anye.core.math._Arc;
import dev.anye.core.math._MathCDT;
import dev.anye.mc.cores.dt.FadeColorData;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;

public final class Render2DHelper {
	private Render2DHelper(){}

	/**
	 * 依靠w，h绘制四个角落
	 *
	 * <pre>
	 *     绘制顺序
	 *     1 - 4
	 *     |   |
	 *     2 - 3
	 *
	 * - -w- -
	 * |
	 * h
	 * |
	 * - -w- -
	 * </pre>
	 * @param vertexConsumer 顶点缓存
	 * @param pose 变换矩阵
	 * @param radius 半径
	 * @param innerRadius 内半径
	 * @param w 横向两个角的间距
	 * @param h 纵向两个角的间距
	 * @param colorData 颜色数据
	 */
	public static void fan4(VertexConsumer vertexConsumer, Matrix3x2fStack pose,
							float radius, float innerRadius, float w,float h,
							FadeColorData colorData){
		// a(r,r)
		// b(r,r + h)
		// c(r + w,r + h)
		// d(r + w,r)
		pose.pushMatrix();
		// a(r,r)
		pose.translate(radius,radius);
		Render2DHelper.fan(vertexConsumer,pose, _Arc.c(180), _Arc.c(90),radius,innerRadius,colorData);
		//b(0,h)
		pose.translate(0,h);
		Render2DHelper.fan(vertexConsumer,pose,_Arc.c(270), _Arc.c(180),radius,innerRadius,colorData);
		//c(w,0)
		pose.translate(w,0);
		Render2DHelper.fan(vertexConsumer,pose,_Arc.c(360), _Arc.c(270),radius,innerRadius,colorData);
		//d(0,-h)
		pose.translate(0,-h);
		Render2DHelper.fan(vertexConsumer,pose,_Arc.c(90), _Arc.c(0),radius,innerRadius,colorData);
		pose.popMatrix();
	}

	/**
	 * 依靠w，h绘制四个角落角度为反向
	 *
	 * <pre>
	 *     绘制顺序
	 *     1 - 4
	 *     |   |
	 *     2 - 3
	 *
	 * - -w- -
	 * |
	 * h
	 * |
	 * - -w- -
	 * </pre>
	 * @param vertexConsumer 顶点缓存
	 * @param pose 变换矩阵
	 * @param radius 半径
	 * @param innerRadius 内半径
	 * @param w 横向两个角的间距
	 * @param h 纵向两个角的间距
	 * @param colorData 颜色数据
	 */
	public static void fan4N(VertexConsumer vertexConsumer, Matrix3x2fStack pose,
							float radius, float innerRadius, float w,float h,
							FadeColorData colorData){
		// a(r,r)
		// b(r,r + h)
		// c(r + w,r + h)
		// d(r + w,r)
		pose.pushMatrix();
		// a(r,r)
		pose.translate(radius,radius);
		Render2DHelper.fan(vertexConsumer,pose,_Arc.c(360), _Arc.c(270),radius,innerRadius,colorData);
		//b(0,h)
		pose.translate(0,h);
		Render2DHelper.fan(vertexConsumer,pose,_Arc.c(90), _Arc.c(0),radius,innerRadius,colorData);
		//c(w,0)
		pose.translate(w,0);
		Render2DHelper.fan(vertexConsumer,pose, _Arc.c(180), _Arc.c(90),radius,innerRadius,colorData);
		//d(0,-h)
		pose.translate(0,-h);
		Render2DHelper.fan(vertexConsumer,pose,_Arc.c(270), _Arc.c(180),radius,innerRadius,colorData);
		pose.popMatrix();
	}

	/**
	 * 绘制彩色扇形，可以设置內弧，每次使用4个顶点，需要QUADS管线
	 * @param vertexConsumer 顶点缓存
	 * @param pose 矩阵
	 * @param startArc 开始角度
	 * @param endArc 结束角度
	 * @param radius 半径
	 * @param innerRadius 内圈半径
	 * @param colorData 颜色数据
	 */
	public static void fan(VertexConsumer vertexConsumer, Matrix3x2fStack pose,
						   double startArc, double endArc, float radius, float innerRadius,
						   FadeColorData colorData){
		if (radius <= 0 ) return;
		pose.pushMatrix();
		double arc = endArc - startArc;
		int segments = (int) Math.ceil(Math.abs(arc) / _Arc.ARC);
		if (segments < 1) segments = 1;
		double s = 1F / segments;

		for (int i = 0; i < segments; i++) {
			double a = i * s;
			float currentArc = (float) (startArc + arc * a);
			float nextArc = (float) (startArc + arc * (a + s));

			float cosCurrentArc = Mth.cos(currentArc);
			float sinCurrentArc = Mth.sin(currentArc);
			float cosNextArc = Mth.cos(nextArc);
			float sinNextArc = Mth.sin(nextArc);

			float x1 = cosCurrentArc * radius;
			float y1 = sinCurrentArc * radius;

			float innerX1 = innerRadius == 0 ? 0 : cosCurrentArc * innerRadius;
			float innerY1 = innerRadius == 0 ? 0 : sinCurrentArc * innerRadius;

			float x2 = cosNextArc * radius;
			float y2 = sinNextArc * radius;

			float innerX2 = innerRadius == 0 ? 0 : cosNextArc * innerRadius;
			float innerY2 = innerRadius == 0 ? 0 : sinNextArc * innerRadius;

			vertexConsumer.addVertexWith2DPose(pose, x1, y1).setColor(colorData.leftTopColor());
			vertexConsumer.addVertexWith2DPose(pose, innerX1, innerY1).setColor(colorData.leftBottomColor());
			vertexConsumer.addVertexWith2DPose(pose, innerX2, innerY2).setColor(colorData.rightBottomColor());
			vertexConsumer.addVertexWith2DPose(pose, x2, y2).setColor(colorData.rightTopColor());
		}
		pose.popMatrix();
	}

	/**
	 * 依靠w，h绘制四个角落
	 *
	 * <pre>
	 *     绘制顺序
	 *     1 - 4
	 *     |   |
	 *     2 - 3
	 *
	 * - -w- -
	 * |
	 * h
	 * |
	 * - -w- -
	 * </pre>
	 * @param vertexConsumer 顶点缓存
	 * @param pose 变换矩阵
	 * @param radius 半径
	 * @param innerRadius 内半径
	 * @param w 横向两个角的间距
	 * @param h 纵向两个角的间距
	 * @param colorData 颜色数据
	 */
	public static void fan4(VertexConsumer vertexConsumer, Matrix3x2fStack pose,
							float radius, float innerRadius,
							float w,float h,
							int colorData){
		// a(r,r)
		// b(r,r + h)
		// c(r + w,r + h)
		// d(r + w,r)
		pose.pushMatrix();
		// a(r,r)
		pose.translate(radius,radius);
		Render2DHelper.fan(vertexConsumer,pose,_Arc.c(180), _Arc.c(90),radius,innerRadius,colorData);
		//b(0,h)
		pose.translate(0,h);
		Render2DHelper.fan(vertexConsumer,pose,_Arc.c(270), _Arc.c(180),radius,innerRadius,colorData);
		//c(w,0)
		pose.translate(w,0);
		Render2DHelper.fan(vertexConsumer,pose,_Arc.c(360), _Arc.c(270),radius,innerRadius,colorData);
		//d(0,-h)
		pose.translate(0,-h);
		Render2DHelper.fan(vertexConsumer,pose,_Arc.c(90), _Arc.c(0),radius,innerRadius,colorData);
		pose.popMatrix();
	}

	/**
	 * 绘制单色扇形，可以设置內弧，每次使用4个顶点，需要QUADS管线
	 * @param vertexConsumer 顶点缓存
	 * @param pose 矩阵
	 * @param startArc 开始角度
	 * @param endArc 结束角度
	 * @param radius 半径
	 * @param innerRadius 内圈半径
	 * @param color 填充色
	 */
	public static void fan(VertexConsumer vertexConsumer, Matrix3x2fStack pose, double startArc, double endArc, float radius, float innerRadius, int color){
		if (radius <= 0 ) return;
		pose.pushMatrix();
		double arc = endArc - startArc;
		int segments = (int) Math.ceil(Math.abs(arc) / _Arc.ARC);
		if (segments < 1) segments = 1;
		double s = 1F / segments;


		for (int i = 0; i < segments; i++) {
			double a = i * s;
			float currentArc = (float) (startArc + arc * a);
			float nextArc = (float) (startArc + arc * (a + s));
			/*
			float currentAngle = currentArc - Mth.HALF_PI;
			float nextAngle = nextArc - Mth.HALF_PI;

			float cosCurrentArc = Mth.cos(currentAngle);
			float sinCurrentArc = Mth.sin(currentAngle);

			float cosNextArc = Mth.cos(nextAngle);
			float sinNextArc = Mth.sin(nextAngle);
			*/

			float cosCurrentArc = Mth.cos(currentArc);
			float sinCurrentArc = Mth.sin(currentArc);

			float cosNextArc = Mth.cos(nextArc);
			float sinNextArc = Mth.sin(nextArc);

			float x1 = cosCurrentArc * radius;
			float y1 = sinCurrentArc * radius;

			float innerX1 = innerRadius == 0 ? 0 : cosCurrentArc * innerRadius;
			float innerY1 = innerRadius == 0 ? 0 : sinCurrentArc * innerRadius;

			float x2 = cosNextArc * radius;
			float y2 = sinNextArc * radius;

			float innerX2 = innerRadius == 0 ? 0 : cosNextArc * innerRadius;
			float innerY2 = innerRadius == 0 ? 0 : sinNextArc * innerRadius;

			vertexConsumer.addVertexWith2DPose(pose, x1, y1).setColor(color);
			vertexConsumer.addVertexWith2DPose(pose, innerX1, innerY1).setColor(color);
			vertexConsumer.addVertexWith2DPose(pose, innerX2, innerY2).setColor(color);
			vertexConsumer.addVertexWith2DPose(pose, x2, y2).setColor(color);
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
	public static void fan(VertexConsumer vertexConsumer, Matrix3x2fStack pose, double startArc, double endArc, float radius, int color){
		pose.pushMatrix();
		double arc = endArc - startArc;
		vertexConsumer.addVertexWith2DPose(pose, 0, 0).setColor(color);
		int segments = (int) Math.ceil(Math.abs(arc) / _Arc.ARC);
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
	 * (逆时针)绘制矩形
	 * @param vertexConsumer 顶点缓存
	 * @param pose 变换矩阵
	 * @param width 宽度
	 * @param height 高度
	 * @param color 颜色
	 */
	public static void rect(VertexConsumer vertexConsumer, Matrix3x2fStack pose,float width,float height,int color){
		rect(vertexConsumer,pose,width,height,color,color,color,color);
		/*
		pose.pushMatrix();
		vertexConsumer.addVertexWith2DPose(pose, 0, 0).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, 0, height).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width, height).setColor(color);
		vertexConsumer.addVertexWith2DPose(pose, width, 0).setColor(color);
		pose.popMatrix();
		*/
	}
	/**
	 * (逆时针)绘制渐变矩形
	 * <pre>
	 * A - D
	 * |   |
	 * B - C
	 * </pre>
	 * @param vertexConsumer 顶点缓存
	 * @param pose 变换矩阵
	 * @param width 宽度
	 * @param height 高度
	 * @param leftTopColor 左上角颜色
	 * @param leftBottomColor 左下角颜色
	 * @param rightBottomColor 右下角颜色
	 * @param rightTopColor 右上角颜色
	 */
	public static void rect(VertexConsumer vertexConsumer, Matrix3x2fStack pose,
							float width,float height,
							int leftTopColor,int leftBottomColor,int rightBottomColor,int rightTopColor){
		pose.pushMatrix();
		vertexConsumer.addVertexWith2DPose(pose, 0, 0).setColor(leftTopColor);
		vertexConsumer.addVertexWith2DPose(pose, 0, height).setColor(leftBottomColor);
		vertexConsumer.addVertexWith2DPose(pose, width, height).setColor(rightBottomColor);
		vertexConsumer.addVertexWith2DPose(pose, width, 0).setColor(rightTopColor);
		pose.popMatrix();
	}
	/**
	 * (逆时针)绘制渐变矩形
	 * <pre>
	 * A - D
	 * |   |
	 * B - C
	 * </pre>
	 * @param vertexConsumer 顶点缓存
	 * @param pose 变换矩阵
	 * @param width 宽度
	 * @param height 高度
	 * @param color 颜色数据
	 */
	public static void rect(VertexConsumer vertexConsumer, Matrix3x2fStack pose,
							float width,float height,
							FadeColorData color){
		pose.pushMatrix();
		vertexConsumer.addVertexWith2DPose(pose, 0, 0).setColor(color.leftTopColor());
		vertexConsumer.addVertexWith2DPose(pose, 0, height).setColor(color.leftBottomColor());
		vertexConsumer.addVertexWith2DPose(pose, width, height).setColor(color.rightBottomColor());
		vertexConsumer.addVertexWith2DPose(pose, width, 0).setColor(color.rightTopColor());
		pose.popMatrix();
	}
}
