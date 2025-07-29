package dev.anye.mc.cores.screen.widget.c;

import dev.anye.core.debug._DeBug;
import dev.anye.core.dt._BoundingBox;
import dev.anye.mc.cores.screen.bs.BorderStyle;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Optional;

public class CWidgetListBox extends CWidgetBaseCore<CWidgetListBox>{
    protected final List<DT_ListBoxData> data;
    protected final int rowSpace,lineSpace,tRowSpace,tLineSpace;
    protected int
            line ,
            row,
            index,
            startIndex,
            elementalWidth,
            elementalHeight,
            strX = 1,
            strY = 1;
    public CWidgetListBox(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage,int row,int line,int rowSpace,int lineSpace,DT_ListBoxData... data) {
        this(boundingBox,borderStyle,pMessage,row,line,rowSpace,lineSpace,List.of(data));
    }
    public CWidgetListBox(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage,int row,int line,int rowSpace,int lineSpace,List<DT_ListBoxData> data) {
        super(boundingBox, borderStyle, pMessage);
        this.rowSpace = rowSpace;
        this.tRowSpace = rowSpace << 1;
        this.lineSpace = lineSpace;
        this.tLineSpace = lineSpace << 1;
        this.data = data;
        this.row = row;
        this.line = line;
        this.elementalWidth = 0;
        this.elementalHeight = 0;
        this.index = -1;
        this.startIndex = 0;
    }
    @Override
    protected void renderContent(GuiGraphics guiGraphics, _BoundingBox boundingBox, int mouseX, int mouseY, float partialTick) {
        if (this.elementalWidth == 0) this.elementalWidth = boundingBox.getW() / row - tRowSpace;
        if (this.elementalHeight == 0) this.elementalHeight = boundingBox.getH() / line - tLineSpace;

        int idex = -1;
        for (int i = 0; i < line; i++) {
            int elemY = boundingBox.getY() + tLineSpace + i * ( elementalHeight + tLineSpace) + this.strY;
            for (int r = 0; r < row; r++) {
                if (startIndex < data.size()) {
                    int elemIndex = startIndex + i * row + r;
                    if (elemIndex < data.size()) {
                        int elemX = boundingBox.getX() + tRowSpace + r * (elementalWidth + tRowSpace);
                        _BoundingBox eb = new _BoundingBox(elemX,elemY,elementalWidth,elementalHeight);
                        this.borderStyle.renderElement(guiGraphics,eb,colorScheme(),mouseX,mouseY);
                        int txtColor = eTextColor.UsualColor();
                        if (
                                mouseX > elemX
                                        && mouseX < elemX + elementalWidth
                                        && mouseY > elemY
                                        && mouseY < elemY + elementalHeight
                        ) {
                            txtColor = eTextColor.HoverColor();
                            idex = elemIndex;
                            guiGraphics.renderTooltip(font, getData(elemIndex).getTooltip(), Optional.empty(), mouseX, mouseY);
                        }
                        drawString(guiGraphics, eb.getX()+ strX, eb.getY(), txtColor, FixStrWidth(getDataComponent(elemIndex),elementalWidth));
                    }
                } else {
                    break;
                }
            }
        }
        index = idex;
    }
    public DT_ListBoxData getData(int index){
        if (index < this.data.size()){
            return this.data.get(index);
        }
        _DeBug.ThrowError("error index");
        return null;
    }
    public Component getDataComponent(int index){
        DT_ListBoxData d = getData(index);
        if (d != null){
            return d.getComponent();
        }
        _DeBug.ThrowError("error data");
        return Component.literal("Error :: Null");
    }
    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double sx,double sy) {
        if (sy < 0 && startIndex < data.size() - row){
            startIndex = startIndex + row;
        }else if (startIndex >= row){
            startIndex = startIndex - row;
        }
        index = -1;
        return super.mouseScrolled(pMouseX, pMouseY, sx,sy);
    }
    @Override
    public void onClick(double pMouseX, double pMouseY,int button) {
        if (isMouseOver(pMouseX,pMouseY) && index >= 0){
            DT_ListBoxData d = getData(index);
            if (d != null){
                d.OnPress(d.getValue());
            }
        }
    }

    public String FixStrWidth(String s,int w){
        return font.plainSubstrByWidth(s,w);
    }
    public String FixStrWidth(Component s,int w){
        return FixStrWidth(s.getString(),w);
    }
}
