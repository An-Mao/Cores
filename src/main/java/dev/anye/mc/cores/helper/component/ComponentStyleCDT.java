package dev.anye.mc.cores.helper.component;

import net.minecraft.network.chat.TextColor;

public class ComponentStyleCDT {
    public static final int black = 0xFF000000;
    public static final int white = 0xFFFFFFFF;
    public static final int red = 0xFFFF0000;
    public static final int orange = 0xFFFFA500;
    public static final int yellow = 0xFFFFFF00;
    public static final int green = 0xFF00FF00;
    public static final int cyan = 0xFF00FFFF;
    public static final int blue = 0xFF0000FF;
    public static final int violet = 0xFFEE82EE;

    public static final TextColor blackTextColor = TextColor.fromRgb(black);
    public static final TextColor whiteTextColor = TextColor.fromRgb(white);
    public static final TextColor redTextColor = TextColor.fromRgb(red);
    public static final TextColor orangeTextColor = TextColor.fromRgb(orange);
    public static final TextColor yellowTextColor = TextColor.fromRgb(yellow);
    public static final TextColor greenTextColor = TextColor.fromRgb(green);
    public static final TextColor cyanTextColor = TextColor.fromRgb(cyan);
    public static final TextColor blueTextColor = TextColor.fromRgb(blue);
    public static final TextColor violetTextColor = TextColor.fromRgb(violet);
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
}
