package dev.anye.mc.cores.cores.gui;

import dev.anye.core.color._ColorCDT;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.cores.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;

import java.util.HashMap;

public class TipGui {
    public static final String id = "tip";
    private static final ResourceLocation texture = new ResourceLocation(Cores.MOD_ID,"textures/gui/tip");
    private static final HashMap<String,Long> msg = new HashMap<>();
    private static boolean show = Configs.GENERAL.getData().isShowTipGui();
    private static final int imageH = 24;
    private static int x;
    private static int y;
    public static void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (show){
            x = screenWidth / 2;
            y = 20;
            HashMap<String,Long> copyMsg = new HashMap<>(msg);
            copyMsg.forEach((s, aLong) -> {
                if (!s.isEmpty()){
                    if (aLong > 0){
                        long newLong = aLong - 1;
                        if (newLong < 1){
                            msg.remove(s);
                        }else {
                            msg.put(s,newLong);
                        }
                        Font font = Minecraft.getInstance().font;
                        int strWidth = font.width(s);
                        int drawX = x - strWidth / 2;
                        guiGraphics.blit(texture,drawX,y,0,0,strWidth,imageH);
                        guiGraphics.drawString(font,s,drawX,y, _ColorCDT.black);
                        y += imageH + 5;
                    }else {
                        msg.remove(s);
                    }
                }
            });
        }
    }
    public static void addMsg(String m, long time){
        msg.put(m , time);
    }
    public static void setShow(boolean s){
        show = s;
    }
}
