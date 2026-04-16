package dev.anye.mc.cores.helper.component;

import dev.anye.core.math._Math;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;
import java.util.Random;

public class ComponentStyle extends ComponentStyleCDT{
    public static int SPEED = 6;//7
    public static int getTextColorStyle(RainbowType rainbowType,int index){
        if (index > rainbowType.colors.length - 1){
            Random random = new Random();
            return rainbowType.colors[random.nextInt(rainbowType.colors.length)];
        }
        return rainbowType.colors[index];
    }
    public static int[] getTextColor(RainbowType rainbowType){
        return rainbowType.getColors();
    }
    public static MutableComponent Flash(String str){
        return Flash(str,SPEED);
    }
    public static MutableComponent Flash(String str,int speed){
        return Flash(str,RainbowType.RAINBOW,speed);
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
            return put.append(Component.literal(str).withColor(getTextColorStyle(rainbowType,index)));
        }
        for (int i = 0; i <strlen ; i++) {
            int ii = index - i % 7;
            if (ii < 0){
                ii = ii +7;
            }
            put.append(Component.literal(str.substring(i, i + 1)).withColor(getTextColorStyle(rainbowType,ii)));
        }
        return put;
    }

    public static MutableComponent Fade(String str,RainbowType rainbowType){
        char[] chars = str.toCharArray();
        MutableComponent put = Component.literal("");
        int i = -1;
        for (char c :chars){
            i = _Math.maxToZero(i,rainbowType.colors.length);
            put.append(Component.literal(String.valueOf(c)).withColor(rainbowType.colors[i]));
        }
        return put;
    }
    public enum RainbowType{
        BW(blackWithWhite),
        RAINBOW(rainbow),
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
        private final int[] colors;
        RainbowType(int[] colors){
            this.colors = colors;
        }
        public int[] getColors() {
            return colors;
        }
        public static @Nullable RainbowType GetByName(String name){
            for (RainbowType ty : RainbowType.values()){
                if (ty.name().equalsIgnoreCase(name)) return ty;
            }
            return null;
        }
    }
}
