package dev.anye.mc.cores.screen.widget.square;

import dev.anye.core.color._ColorCDT;
import dev.anye.core.debug._DeBug;
import dev.anye.mc.cores.render.GuiGraphicsX;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import dev.anye.mc.cores.screen.widget.DT_XYWHUV;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.joml.Matrix3x2fStack;

import java.util.List;

public class SquareListBox extends SquareWidgetCore<SquareListBox> {
    private List<DT_ListBoxData> data;
    private int dataSize;
    private int line ,row,index,startIndex;
    private int elementalWidth, elementalHeight;
    private int hSpace, vSpace;
    private DT_XYWHUV bg;
    private int bgColor,textSelectColor,textColor;
    private int left = 0,top = 0,strX,strY;
    public SquareListBox(int x, int y, int w, int h, int elementalWidth, int elementalHeight, Component pMessage, List<DT_ListBoxData> data) {
        super(x,y,w,h,pMessage);
        this.data = data;
        this.dataSize = this.data.size();
        this.elementalWidth = elementalWidth;
        this.elementalHeight = elementalHeight;
        this.index = -1;
        this.startIndex = 0;
        this.bgColor = _ColorCDT.black;
        this.textColor = _ColorCDT.white;
        this.textSelectColor = _ColorCDT.yellow;
        resetAutoSpace();
        setStrY();
    }
    public void setData(List<DT_ListBoxData> data) {
        this.data = data;
        this.dataSize = this.data.size();
        this.index = -1;
        this.startIndex = 0;
    }

    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public SquareListBox setFont(Font font) {
        super.setFont(font);
        setStrY();
        return self();
    }

    public void setLeft(int left) {
        this.left = left;
        resetAutoSpace();
    }
    public void resetAutoSpace(){
        double s = elementalWidth % (width - left);
        s /=  (double) (width - left) / elementalWidth;
        s /= 2;
        setVSpace((int) s);
        setHSpace((int) s);
    }
    public void setBg(DT_XYWHUV bg) {
        this.bg = bg;
    }
    public void setStrY() {
        this.strY = (elementalWidth/font.lineHeight) / 2;
    }

    public void setElementalHeight(int elementalHeight) {
        this.elementalHeight = elementalHeight;
    }

    public void setElementalWidth(int elementalWidth) {
        this.elementalWidth = elementalWidth;
    }
    public void setVSpace(int vSpace) {
        this.vSpace = vSpace;
        this.line = getHeight() / (elementalHeight + vSpace);
    }

    public void setHSpace(int hSpace) {
        this.hSpace = hSpace;
        this.row = getWidth() / (elementalWidth + hSpace);
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSelectColor(int textSelectColor) {
        this.textSelectColor = textSelectColor;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setLine(int line) {
        this.line = line;
    }
    /*
    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        PoseStack poseStack = pGuiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(0, 0, layerZ);
        int idex = -1;
        if (texture != null){
            drawImage(pGuiGraphics,bg);
            for (int i = 0;i < line;i++){
                int dy = left + getY() + i * (elementalHeight + vSpace);
                for (int r = 0; r < row; r++){
                    if (startIndex < dataSize){
                        int ni =startIndex + i * row + r;
                        if (ni < dataSize) {
                            int dx = top + getX() + r * (elementalWidth + hSpace);
                            DT_XYWHUV bg = new DT_XYWHUV(dx,dy,elementalWidth,elementalHeight,bgNormal.getUOffset(),bgNormal.getVOffset());
                            int tc = textColor;
                            if (pMouseX > dx
                                    && pMouseX < dx + elementalWidth
                                    && pMouseY > dy
                                    && pMouseY < dy+ elementalHeight) {
                                bg = new DT_XYWHUV(dx,dy,elementalWidth,elementalHeight,bgSelect.getUOffset(),bgSelect.getVOffset());
                                tc = textSelectColor;
                                idex = ni;
                                pGuiGraphics.renderTooltip(font,getData(ni).getTooltip(), Optional.empty(),pMouseX,pMouseY);
                            }
                            drawImage(pGuiGraphics,bg);
                            DrawString(pGuiGraphics,dx,dy + strY,tc,FixStrWidth(getDataComponent(ni)));
                        }
                    }else {
                        break;
                    }
                }
            }
        }else {
            drawSquare(pGuiGraphics,bgColor);
            for (int i = 0;i < line;i++){
                int dy = left + getY() + i * (elementalHeight + vSpace);
                for (int r = 0; r < row; r++){
                    if (startIndex < dataSize){
                        int ni =startIndex + i * row + r;
                        if (ni < dataSize) {
                            int dx = top + getX() + r * (elementalWidth + hSpace);
                            int bgc = backgroundUsualColor;
                            int tc = textColor;
                            if (pMouseX > dx
                                    && pMouseX < dx + elementalWidth
                                    && pMouseY > dy
                                    && pMouseY < dy+ elementalHeight) {
                                bgc = backgroundHoverColor;
                                tc = textSelectColor;
                                idex = ni;
                                pGuiGraphics.renderTooltip(font,getData(ni).getTooltip(), Optional.empty(),pMouseX,pMouseY);
                            }
                            drawSquare(pGuiGraphics, dx, dy, elementalWidth, elementalHeight, bgc);
                            DrawString(pGuiGraphics, dx, dy + strY, tc, FixStrWidth(getDataComponent(ni)));
                        }
                    }else {
                        break;
                    }
                }
            }
        }
        index = idex;
        poseStack.popPose();
    }

     */
    @Override
    protected void renderContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        Matrix3x2fStack poseStack = guiGraphics.pose();
        poseStack.pushMatrix();
        poseStack.translate(0, 0);
        int idex = -1;
        drawSquare(guiGraphics, bgColor);
        for (int i = 0; i < line; i++) {
            int dy = left + getY() + i * (elementalHeight + vSpace);
            for (int r = 0; r < row; r++) {
                if (startIndex < dataSize) {
                    int ni = startIndex + i * row + r;
                    if (ni < dataSize) {
                        int dx = top + getX() + r * (elementalWidth + hSpace);
                        int bgc = backgroundUsualColor;
                        int tc = textColor;
                        if (mouseX > dx
                                && mouseX < dx + elementalWidth
                                && mouseY > dy
                                && mouseY < dy + elementalHeight) {
                            bgc = backgroundHoverColor;
                            tc = textSelectColor;
                            idex = ni;
                            GuiGraphicsX.renderTooltip(guiGraphics,font, getData(ni).getTooltip(), mouseX, mouseY);
                        }
                        drawSquare(guiGraphics, dx, dy, elementalWidth, elementalHeight, bgc);
                        drawString(guiGraphics, dx, dy + strY, tc, FixStrWidth(getDataComponent(ni)));
                    }
                } else {
                    break;
                }
            }
        }
        index = idex;
        poseStack.popMatrix();
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
    public boolean isSelectElemental(int pMouseX, int pMouseY){
        if (index < 0){
            return false;
        }
        return pMouseX > getX() + (index - 1) * (elementalWidth + hSpace)
                && pMouseX < getX() + (elementalWidth + hSpace) * index
                && pMouseY > getY() + (elementalHeight + vSpace) * (index - 1)
                && pMouseY < getY() + (elementalHeight + vSpace) * index;
    }
    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double sx,double sy) {
        if (sy < 0 && startIndex < dataSize - row){
            startIndex = startIndex + row;
        }else if (startIndex >= row){
            startIndex = startIndex - row;
        }
        index = -1;
        return super.mouseScrolled(pMouseX, pMouseY, sx,sy);
    }

    @Override
    public void onClick(MouseButtonEvent pEvent, boolean pIsDoubleClick) {
        onClick(pEvent.x(),pEvent.y());
    }

    public void onClick(double pMouseX, double pMouseY) {
        if (isMouseOver(pMouseX,pMouseY) && index >= 0){
            DT_ListBoxData d = getData(index);
            if (d != null){
                d.OnPress(d.getValue());
            }
        }
    }

    public String FixStrWidth(String s){
        return font.plainSubstrByWidth(s,elementalWidth);
    }
    public String FixStrWidth(Component s){
        return FixStrWidth(s.getString());
    }
}
