package dev.anye.mc.cores.screen.widget.simple;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SimpleButton extends SimpleLabel {
    private final OnPress onPress;
    public SimpleButton(int x, int y, int w, int h, Component pMessage, OnPress onPress) {
        this(x, y, w, h, pMessage,true,false,true,onPress);
    }
    public SimpleButton(int x, int y, int w, int h, Component pMessage, boolean AutoWidth, boolean AutoHeight, boolean centerText, OnPress onPress) {
        super(x, y, w, h, pMessage,AutoWidth,AutoHeight,centerText);
        this.onPress = onPress;
    }
    public SimpleButton(int x, int y, int w, int h, Component pMessage, int borderColor, int fillColor, int textColor, boolean AutoWidth, boolean AutoHeight, boolean centerText, OnPress onPress) {
        super(x, y, w, h, pMessage,borderColor,fillColor,textColor,AutoWidth,AutoHeight,centerText);
        this.onPress = onPress;
    }

    @Override
    protected void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderContent(guiGraphics, mouseX, mouseY, partialTick);
    }
    @Override
    public void onClick(double pMouseX, double pMouseY) {
        this.onPress.onPress();
    }
}
