package dev.anye.mc.cores.render;

import dev.anye.core.color._ColorCDT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class DrawString {
    public static void drawString(GuiGraphics guiGraphics,String str,int x,int y){
        drawString(guiGraphics,Minecraft.getInstance().font, str,x,y, _ColorCDT.black);
    }
    public static void drawString(GuiGraphics guiGraphics, Font font, String str, int x, int y, int color){
        guiGraphics.drawString(font,str,x,y,color);
    }
    public void drawString(GuiGraphics guiGraphics,Font font,int x,int y,int color,boolean shadow, Component component){
        guiGraphics.drawString(font,component,x,y,color,shadow);
    }
    public void drawString(GuiGraphics guiGraphics,int x,int y,int color,boolean shadow, Component component){
        drawString(guiGraphics,Minecraft.getInstance().font,x,y,color,shadow,component);
    }
    public void drawString(GuiGraphics guiGraphics,int x,int y,int color,Component component){
        drawString(guiGraphics,Minecraft.getInstance().font,x,y,color,false,component);
    }
    public void drawString(GuiGraphics guiGraphics,int x,int y,Component component){
        drawString(guiGraphics,Minecraft.getInstance().font,x,y,_ColorCDT.black,false,component);
    }
}
