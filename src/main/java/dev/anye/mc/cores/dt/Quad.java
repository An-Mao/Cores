package dev.anye.mc.cores.dt;

import org.jspecify.annotations.NonNull;

public record Quad(float leftTopX, float leftTopY,
				   float leftBottomX, float leftBottomY,
				   float rightBottomX, float rightBottomY,
				   float rightTopX, float rightTopY
) {

	@Override
	public @NonNull String toString() {
		return "Quad{" +
				"leftTopX=" + leftTopX +
				", leftTopY=" + leftTopY +
				", leftBottomX=" + leftBottomX +
				", leftBottomY=" + leftBottomY +
				", rightBottomX=" + rightBottomX +
				", rightBottomY=" + rightBottomY +
				", rightTopX=" + rightTopX +
				", rightTopY=" + rightTopY +
				'}';
	}

	public static Quad create(
			float leftBottomX,float leftBottomY,
			float rightBottomX,float rightBottomY,
			float rightTopX,float rightTopY
	){
		return new Quad(
				0,0,
				leftBottomX,leftBottomY,
				rightBottomX,rightBottomY,
				rightTopX,rightTopY
				);
	}

	/**
	 * 朝下普通梯形
	 * <pre>
	 *     ---
	 *     \ /
	 *      -
	 * </pre>
	 * @param w 上边
	 * @param h 高
	 * @param w2 下边
	 * @param xOffset 偏移量
	 * @return 四边形数据
	 */
	public static Quad normalDownTrapezoid(float w, float h, float w2, float xOffset){
		return new Quad(
				0,0,
				xOffset,h,
				xOffset + w2,h,
				w,0
				);
	}
	/**
	 * 朝上普通梯形
	 * <pre>
	 *       -
	 *      / \
	 *      ---
	 * </pre>
	 * @param w 上边
	 * @param h 高
	 * @param w2 下边
	 * @param xOffset 偏移量
	 * @return 四边形数据
	 */
	public static Quad normalUpTrapezoid(float w, float h, float w2, float xOffset){
		return new Quad(
				xOffset,0,
				0,h,
				w2,h,
				xOffset + w,0
		);
	}
	/**
	 * 朝右普通梯形
	 * <pre>
	 * |
	 * |  |
	 * |
	 * </pre>
	 * @param h1 左边
	 * @param w 宽
	 * @param h2 右边
	 * @param yOffset 偏移量
	 * @return 四边形数据
	 */
	public static Quad normalRightTrapezoid(float h1, float w, float h2, float yOffset){
		return new Quad(
				0,0,
				0,h1,
				w,yOffset + h2,
				w,yOffset
		);
	}
	/**
	 * 朝左普通梯形
	 * <pre>
	 *    |
	 * |  |
	 *    |
	 * </pre>
	 * @param h1 左边
	 * @param w 宽
	 * @param h2 右边
	 * @param yOffset 偏移量
	 * @return 四边形数据
	 */
	public static Quad normalLeftTrapezoid(float h1, float w, float h2, float yOffset){
		return new Quad(
				0,yOffset,
				0,yOffset + h1,
				w,h2,
				w,0
		);
	}
}
