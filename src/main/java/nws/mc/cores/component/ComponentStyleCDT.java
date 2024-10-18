package nws.mc.cores.component;

import net.minecraft.network.chat.TextColor;
import nws.dev.core.color._ColorCDT;

public class ComponentStyleCDT extends _ColorCDT {
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
