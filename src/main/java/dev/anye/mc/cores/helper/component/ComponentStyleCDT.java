package dev.anye.mc.cores.helper.component;

import dev.anye.mc.cores.constant.ColorIntCDT;
import net.minecraft.network.chat.TextColor;

public class ComponentStyleCDT extends ColorIntCDT {
	public static final TextColor blackTextColor = TextColor.fromRgb(black);
	public static final TextColor darkGrayTextColor = TextColor.fromRgb(darkGray);
	public static final TextColor grayTextColor = TextColor.fromRgb(gray);
	public static final TextColor lightGrayTextColor = TextColor.fromRgb(lightGray);
	public static final TextColor whiteTextColor = TextColor.fromRgb(white);
	public static final TextColor redTextColor = TextColor.fromRgb(red);
	public static final TextColor orangeTextColor = TextColor.fromRgb(orange);
	public static final TextColor yellowTextColor = TextColor.fromRgb(yellow);
	public static final TextColor greenTextColor = TextColor.fromRgb(green);
	public static final TextColor cyanTextColor = TextColor.fromRgb(cyan);
	public static final TextColor blueTextColor = TextColor.fromRgb(blue);
	public static final TextColor violetTextColor = TextColor.fromRgb(violet);
	public static final TextColor brownTextColor = TextColor.fromRgb(brown);

	/**
	 * rainbow（彩虹色）
	 */
	public static final int[] rainbow = {
			red,//0
			orange,//1
			yellow,//2
			green,//3
			cyan,//4
			blue,//5
			violet//6
	};
	/**
	 * 黑白色
	 */
	public static final int[] blackWithWhite = {
			black,//0
			white
	};


	/**
	 * warmColors（暖色系）
	 */
	public static final int[] warmColors = {
			red,
			orange,
			yellow
	};
	/**
	 * coolColors（冷色系）
	 */
	public static final int[] coolColors = {
			green,
			cyan,
			blue,
			violet
	};
	/**
	 * grayscale（灰度色）
	 */
	public static final int[] grayscale = {
			black,
			darkGray,
			gray,
			lightGray,
			white
	};
	/**
	 * primaryColors（三原色 - 红绿蓝）
	 */
	public static final int[] primaryColors = {
			red,
			green,
			blue
	};
	/**
	 * secondaryColors（三间色 - 橙绿紫）
	 */
	public static final int[] secondaryColors = {
			orange,
			green,
			violet
	};
	/**
	 * trafficLight（交通灯色）
	 */
	public static final int[] trafficLight = {
			red,
			yellow,
			green
	};
	/**
	 * sunsetGradient（日落渐变色）
	 */
	public static final int[] sunsetGradient = {
			red,
			orange,
			yellow,
			violet
	};
	/**
	 * oceanWave（海浪色）
	 */
	public static final int[] oceanWave = {
			blue,
			cyan,
			green
	};
	/**
	 * earthTones（大地色系）
	 */
	public static final int[] earthTones = {
			brown,
			green,
			orange
	};
	/**
	 * vibrantColors（鲜艳色系）
	 */
	public static final int[] vibrantColors = {
			red,
			yellow,
			blue,
			green
	};
	/**
	 * pastelLike（柔和色系 - 尝试用现有颜色模拟）
	 */
	public static final int[] pastelLike = {
			lightGray,
			cyan,
			violet,
			yellow
	};
	/**
	 * festiveColors（节日色 - 红绿搭配）
	 */
	public static final int[] festiveColors = {
			red,
			green
	};
	/**
	 * alerts（警告/提示色）
	 */
	public static final int[] alerts = {
			red,
			orange,
			yellow
	};
}
