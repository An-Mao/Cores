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
    public static final TextColor[] rainbow = {
            redTextColor,//0
            orangeTextColor,//1
            yellowTextColor,//2
            greenTextColor,//3
            cyanTextColor,//4
            blueTextColor,//5
            violetTextColor//6
    };
    /**
     * 黑白色
     */
    public static final TextColor[] blackWithWhite = {
            blackTextColor,//0
            whiteTextColor
    };



    /**
     * warmColors（暖色系）
     */
    public static final TextColor[] warmColors = {
            redTextColor,
            orangeTextColor,
            yellowTextColor
    };
    /**
     * coolColors（冷色系）
     */
    public static final TextColor[] coolColors = {
            greenTextColor,
            cyanTextColor,
            blueTextColor,
            violetTextColor
    };
    /**
     * grayscale（灰度色）
     */
    public static final TextColor[] grayscale = {
            blackTextColor,
            darkGrayTextColor,
            grayTextColor,
            lightGrayTextColor,
            whiteTextColor
    };
    /**
     * primaryColors（三原色 - 红绿蓝）
     */
    public static final TextColor[] primaryColors = {
            redTextColor,
            greenTextColor,
            blueTextColor
    };
    /**
     * secondaryColors（三间色 - 橙绿紫）
     */
    public static final TextColor[] secondaryColors = {
            orangeTextColor,
            greenTextColor,
            violetTextColor
    };
    /**
     * trafficLight（交通灯色）
     */
    public static final TextColor[] trafficLight = {
            redTextColor,
            yellowTextColor,
            greenTextColor
    };
    /**
     * sunsetGradient（日落渐变色）
     */
    public static final TextColor[] sunsetGradient = {
            redTextColor,
            orangeTextColor,
            yellowTextColor,
            violetTextColor
    };
    /**
     * oceanWave（海浪色）
     */
    public static final TextColor[] oceanWave = {
            blueTextColor,
            cyanTextColor,
            greenTextColor
    };
    /**
     * earthTones（大地色系）
     */
    public static final TextColor[] earthTones = {
            brownTextColor,
            greenTextColor,
            orangeTextColor
    };
    /**
     * vibrantColors（鲜艳色系）
     */
    public static final TextColor[] vibrantColors = {
            redTextColor,
            yellowTextColor,
            blueTextColor,
            greenTextColor
    };
    /**
     * pastelLike（柔和色系 - 尝试用现有颜色模拟）
     */
    public static final TextColor[] pastelLike = {
            lightGrayTextColor,
            cyanTextColor,
            violetTextColor,
            yellowTextColor
    };
    /**
     * festiveColors（节日色 - 红绿搭配）
     */
    public static final TextColor[] festiveColors = {
            redTextColor,
            greenTextColor
    };
    /**
     * alerts（警告/提示色）
     */
    public static final TextColor[] alerts = {
            redTextColor,
            orangeTextColor,
            yellowTextColor
    };
}
