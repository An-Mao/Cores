package dev.anye.mc.cores.screen.widget.c;

import dev.anye.core.dt._BoundingBox;
import dev.anye.mc.cores.screen.bs.BorderStyle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CWidgetButton extends CWidgetLabel{
    private final OnPress onPress;

    public CWidgetButton(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage,OnPress onPress) {
        this(boundingBox, borderStyle, pMessage,true,true,onPress);
    }

    public CWidgetButton(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage, boolean centerText,boolean autoW,OnPress onPress) {
        super(boundingBox, borderStyle, pMessage, centerText,autoW);
        this.onPress = onPress;
    }
    @Override
    public void onClick(double pMouseX, double pMouseY,int button) {
        this.onPress.onPress();
    }
}
