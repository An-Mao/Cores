package dev.anye.mc.cores.helper.component;

import dev.anye.core.math._Math;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import javax.annotation.Nullable;
import java.util.Random;

public class ComponentStyle extends ComponentStyleCDT{
    public static int SPEED = 6;//7
    public static Style getColorStyle(TextColor textColor){
        return Style.EMPTY.withColor(textColor);
    }
    public static Style getTextColorStyle(RainbowType rainbowType,int index){
        TextColor[] colors = getTextColor(rainbowType);
        if (index > colors.length - 1){
            Random random = new Random();
            return getColorStyle(colors[random.nextInt(colors.length)]);
        }
        return getColorStyle(colors[index]);
    }
    public static TextColor[] getTextColor(RainbowType rainbowType){
        return rainbowType.getColors();
    }
    public static MutableComponent Flash(String str){
        return Flash(str,SPEED);
    }
    public static MutableComponent Flash(String str,int speed){
        return Flash(str,RainbowType.DEFAULT,speed);
    }
    public static MutableComponent Flash(String str,RainbowType rainbowType) {
        return Flash(str,rainbowType, SPEED);
    }
    public static MutableComponent Flash(String str,RainbowType rainbowType,int speed){
        long time = System.currentTimeMillis() >> speed;
        int strlen = str.length();
        MutableComponent put = Component.literal("");
        int index = (int) (time % 21) / 3;
        if (strlen < 7 ){
            return put.append(Component.literal(str).withStyle(getTextColorStyle(rainbowType,index)));
        }
        for (int i = 0; i <strlen ; i++) {
            int ii = index - i % 7;
            if (ii < 0){
                ii = ii +7;
            }
            put.append(Component.literal(str.substring(i, i + 1)).withStyle(getTextColorStyle(rainbowType,ii)));
        }
        return put;
    }

    public static MutableComponent Fade(String str,RainbowType rainbowType){
        char[] chars = str.toCharArray();
        MutableComponent put = Component.literal("");
        TextColor[] colors = getTextColor(rainbowType);
        int i = -1;
        for (char c :chars){
            i = _Math.maxToZero(i,colors.length);
            put.append(Component.literal(String.valueOf(c)).withStyle(getColorStyle(colors[i])));
        }
        return put;
    }
    public enum RainbowType{
        BW(blackWithWhite),
        DEFAULT(rainbow),
        WarmColors(warmColors),
        CoolColors(coolColors),
        Grayscale(grayscale),
        PrimaryColors(primaryColors),
        SecondaryColors(secondaryColors),
        TrafficLight(trafficLight),
        SunsetGradient(sunsetGradient),
        OceanWave(oceanWave),
        VibrantColors(vibrantColors),
        PastelLike(pastelLike),
        FestiveColors(festiveColors),
        Alerts(alerts);
        private final TextColor[] colors;
        RainbowType(TextColor[] colors){
            this.colors = colors;
        }
        public TextColor[] getColors() {
            return colors;
        }


		@Nullable
		public static RainbowType GetByName(String name) {
			for(RainbowType ty : values()) {
				if (ty.name().equalsIgnoreCase(name)) {
					return ty;
				}
			}

			return null;
		}
    }
}
