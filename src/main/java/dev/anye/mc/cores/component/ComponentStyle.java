package dev.anye.mc.cores.component;

import dev.anye.core.math._Math;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.util.Random;

public class ComponentStyle extends ComponentStyleCDT{
    public static Style getColorStyle(TextColor textColor){
        return Style.EMPTY.withColor(textColor);
    }
    public static Style getTextColorStyle(int colorsIndex,int index){
        TextColor[] colors = getTextColor(colorsIndex);
        if (index > colors.length - 1){
            Random random = new Random();
            return getColorStyle(colors[random.nextInt(colors.length)]);
        }
        return getColorStyle(colors[index]);
    }
    public static TextColor[] getTextColor(int colorsIndex){
        if (colorsIndex == 0){
            return blackWithWhite;
        } else if (colorsIndex == 1) {
            return rainbow;
        }
        return new TextColor[white];
    }
    public static MutableComponent Flash(String str, long time){
        return Flash(str,1,time);
    }
    public static MutableComponent Flash(String str,int colorIndex,long time){
        int strlen = str.length();
        MutableComponent put = Component.literal("");
        int index = (int) (time % 21) / 3;
        if (strlen < 7 ){
            return put.append(Component.literal(str).withStyle(getTextColorStyle(colorIndex,index)));
        }
        for (int i = 0; i <strlen ; i++) {
            int ii = index - i % 7;
            if (ii < 0){
                ii = ii +7;
            }
            put.append(Component.literal(str.substring(i, i + 1)).withStyle(getTextColorStyle(colorIndex,ii)));
        }
        return put;
    }

    public static MutableComponent Fade(String str,int colorIndex){
        char[] chars = str.toCharArray();
        MutableComponent put = Component.literal("");
        TextColor[] colors = getTextColor(colorIndex);
        int i = -1;
        for (char c :chars){
            i = _Math.maxToZero(i,colors.length);
            put.append(Component.literal(String.valueOf(c)).withStyle(getColorStyle(colors[i])));
        }
        return put;
    }
}
