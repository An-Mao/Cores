package dev.anye.mc.cores.screen.widget.c;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.anye.core.debug._DeBug;
import dev.anye.core.dt._BoundingBox;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.screen.bs.BorderStyle;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CWidgetDropDownSelectBox extends CWidgetBaseCore<CWidgetDropDownSelectBox>{
    private final List<DT_ListBoxData> dataList;
    private int nowSelectIndex = -1,lineHeight,
            nowPage;
    private boolean showList = false;
    private final int line,

            pages;
    private final boolean center;
    private int elementH;

    public CWidgetDropDownSelectBox(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage,int line,boolean center,DT_ListBoxData... data) {
        this(boundingBox, borderStyle, pMessage,line,center, Arrays.asList(data));
    }
    public CWidgetDropDownSelectBox(_BoundingBox boundingBox, BorderStyle borderStyle, Component pMessage,int line,boolean center,List<DT_ListBoxData> data) {
        super(boundingBox, borderStyle, pMessage);
        this.dataList = data;
        this.center = center;
        this.line = line;
        this.lineHeight = 0;
        this.pages = getPages(dataList.size(),line);
        this.nowPage = 1;
    }

    public int getPages(int number , int line){
        if (number == 0){
            return 1;
        }
        int n = number / line;
        if (number % line != 0){
            n ++;
        }
        return n;
    }
    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }
    public Component getSelectComponent(){
        DT_ListBoxData dropDownListBoxData = getSelectData();
        if (dropDownListBoxData != null){
            return dropDownListBoxData.getComponent();
        }
        return Component.literal("--");
    }
    public Component getComponent(int index){
        DT_ListBoxData dropDownListBoxData = getData(index);
        if (dropDownListBoxData != null){
            return dropDownListBoxData.getComponent();
        }
        return Component.literal("--");
    }
    public List<Component> getDataTooltip(int index){
        DT_ListBoxData dropDownListBoxData = getData(index);
        if (dropDownListBoxData != null){
            return dropDownListBoxData.getTooltip();
        }
        return List.of(Component.literal("--")) ;
    }
    public DT_ListBoxData getSelectData(){
        if (nowSelectIndex >= 0 && nowSelectIndex < dataList.size()){
            return dataList.get(nowSelectIndex);
        }
        return null;
    }
    public DT_ListBoxData getData(int index){
        int i = (getNowPage() - 1) * line + index;
        if (i >= 0 && i < dataList.size()){
            return dataList.get(i);
        }
        return null;
    }
    public Object getSelectValue(){
        if (nowSelectIndex > dataList.size() || nowSelectIndex < 0){
            _DeBug.ThrowError("Error Select");
            return null;
        }else {
            return dataList.get(nowSelectIndex).getValue();
        }
    }
    public boolean setSelect(int index){
        if (index > dataList.size() || index < 0){
            return false;
        }
        this.nowSelectIndex = index;
        return true;
    }
    public int getNowSelectIndex(){
        return nowSelectIndex;
    }
    public String FixStrWidth(String s){
        return font.plainSubstrByWidth(s,width);
    }
    public String FixStrWidth(Component s){
        return FixStrWidth(s.getString());
    }
    @Override
    public void onClick(double pMouseX, double pMouseY,int button) {
        showList = !showList;
        int np = getNowPage();
        if (showList){
            setHeight(boundingBox.getH() + lineHeight);
        }else {
            setHeight(boundingBox.getH());
        }
        updateIndex(pMouseY,np);
    }
    public void updateIndex(double mouseY,int  page){
        int i = (int) ((mouseY - contentBox.getY()) / elementH);
        if (i > 0){
            nowSelectIndex = i - 1;
            nowSelectIndex += (page- 1) * line;
            DT_ListBoxData dropDownListBoxData = getSelectData();
            if (dropDownListBoxData != null) {
                dropDownListBoxData.OnPress(getSelectValue());
            }
        }
    }
    public boolean isInWidget(double pMouseX,double pMouseY){
        return contentBox.isInBox((int) pMouseX, (int) pMouseY);
    }
    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double sx,double sy) {
        if (showList) {
            if (isInWidget(pMouseX,pMouseY)) {
                if (sy < 0 && getNowPage() < pages){
                    setNowPage(getNowPage() + 1);
                }else if (sy > 0 && getNowPage() > 1){
                    setNowPage(getNowPage() - 1);
                }
                return true;
            }
        }
        return super.mouseScrolled(pMouseX, pMouseY, sx,sy);

    }
    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (visible) {
            PoseStack poseStack = pGuiGraphics.pose();

            contentBox = showList ? new _BoundingBox(boundingBox.getX(),boundingBox.getY(),boundingBox.getW(),boundingBox.getH() + lineHeight):boundingBox.copy();
            poseStack.pushPose();
            poseStack.translate(0, 0, layerZ);
            borderStyle.render(pGuiGraphics, contentBox,this.colorScheme(), pMouseX, pMouseY);
            if (this.lineHeight == 0) this.lineHeight = line * contentBox.getH();
            renderContentX(pGuiGraphics, contentBox, pMouseX, pMouseY, pPartialTick);
            poseStack.popPose();
        }
    }

    protected void renderContentX(GuiGraphics guiGraphics,_BoundingBox boundingBox, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        Component c = getMessage();
        if (nowSelectIndex > -1 || c.equals(Component.empty())){
            c = getSelectComponent();
        }

        this.elementH = showList ? boundingBox.getH() / (line + 1) : boundingBox.getH();


        int sx = boundingBox.getX();
        int sy = center ?  CenterY(boundingBox.getY(),this.boundingBox.getH() - borderStyle.w()):boundingBox.getY();
        //int linePosY = center ? Math.max(_Math.half(elementH - font.lineHeight),0):0;

        guiGraphics.drawString(getFont(),Component.literal(FixStrWidth(c)),sx,sy,getTextHoverColor(),false);



        if (showList){
            for (int i = 0; i < line;i++){
                int lineH =  (i+1) * elementH;
                int lineY = boundingBox.getY() + lineH;
                Component select = getComponent(i);
                int tc = getTextUsualColor();
                if (mouseX > boundingBox.getX() && mouseX < boundingBox.getMaxX()
                        &&
                        mouseY > lineY && mouseY < lineY + elementH){
                    tc = textSelectColor;
                    guiGraphics.renderTooltip(font,getDataTooltip(i), Optional.empty(),mouseX,mouseY);
                    guiGraphics.fill(boundingBox.getX(),lineY,boundingBox.getMaxX(), lineY+ elementH, backgroundSelectColor);
                }
                guiGraphics.drawString(getFont(),Component.literal(FixStrWidth(select)),sx,sy+lineH,tc,false);
            }
        }
        poseStack.popPose();
    }
    @Override
    protected void renderContent(GuiGraphics guiGraphics, _BoundingBox boundingBox, int mouseX, int mouseY, float partialTick) {
    }
}
