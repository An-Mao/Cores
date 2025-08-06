package dev.anye.mc.cores.screen;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.dt._BoundingBox;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.color.ColorConfig;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.color.ColorSchemes;
import dev.anye.mc.cores.screen.bs.BorderStyles;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import dev.anye.mc.cores.screen.widget.c.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SettingScreen extends Screen {
    CWidgetDropDownSelectBox colorSelectBox;
    CWidgetButton saveButton;
    public SettingScreen( ) {
        super(Component.translatable("screen."+ Cores.MOD_ID+".settings.title"));
    }

    @Override
    protected void init() {
        super.init();
        int x = this.width / 2, y = this.height / 2;

        saveButton = new CWidgetButton(new _BoundingBox(x - 32, this.height - 64, 64, 16),BorderStyles.DEFAULT, Component.translatable("screen."+ Cores.MOD_ID+".settings.button.save"),this::save);
        addRenderableWidget(saveButton);

        CWidgetLabel label = new CWidgetLabel(new _BoundingBox(16, 16,16,16),BorderStyles.DEFAULT, Component.translatable("screen."+ Cores.MOD_ID+".settings.label.select_color"));
        addRenderableWidget(label);

        colorSelectBox = new CWidgetDropDownSelectBox(new _BoundingBox(label.getX()+label.getWidth()+5,label.getY(), 80, 16), BorderStyles.DEFAULT, ColorSchemeRegister.getSchemeComponent(ColorSchemes.getGlobal()),7,true,getRegColor());
        addRenderableWidget(colorSelectBox);



        //addRenderableWidget(new CWidgetLabel(new _BoundingBox(100,16,128,20), BorderStyles.ROUNDED_NORMAL,Component.literal("Test")));

        /*
        addRenderableWidget(new CWidgetDropDownSelectBox(new _BoundingBox(100,40,128,20), BorderStyles.ROUNDED_NORMAL,Component.literal("Test"),7,true,new DT_ListBoxData(Component.literal("Test1"),"1"),new DT_ListBoxData(Component.literal("Test2"),"2"),new DT_ListBoxData(Component.literal("Test"),"3"),new DT_ListBoxData(Component.literal("Test4"),"4"),new DT_ListBoxData(Component.literal("Test"),"5"),new DT_ListBoxData(Component.literal("Test"),"6"),new DT_ListBoxData(Component.literal("Test1"),"1"),new DT_ListBoxData(Component.literal("Test2"),"2"),new DT_ListBoxData(Component.literal("Test"),"3"),new DT_ListBoxData(Component.literal("Test4"),"4"),new DT_ListBoxData(Component.literal("Test"),"5"),new DT_ListBoxData(Component.literal("Test"),"6"),new DT_ListBoxData(Component.literal("Test1"),"1"),new DT_ListBoxData(Component.literal("Test2"),"2"),new DT_ListBoxData(Component.literal("Test"),"3"),new DT_ListBoxData(Component.literal("Test4"),"4"),new DT_ListBoxData(Component.literal("Test"),"5"),new DT_ListBoxData(Component.literal("Test"),"6")));

         */
        /*
        addRenderableWidget(new CWidgetListBox(new _BoundingBox(100,40,400,200), BorderStyles.TEST,Component.literal("Test"),7,5,2,2,new DT_ListBoxData(Component.literal("Test1"),"1"),new DT_ListBoxData(Component.literal("Test2"),"2"),new DT_ListBoxData(Component.literal("Test"),"3"),new DT_ListBoxData(Component.literal("Test4"),"4"),new DT_ListBoxData(Component.literal("Test"),"5"),new DT_ListBoxData(Component.literal("Test"),"6"),new DT_ListBoxData(Component.literal("Test1"),"1"),new DT_ListBoxData(Component.literal("Test2"),"2"),new DT_ListBoxData(Component.literal("Test"),"3"),new DT_ListBoxData(Component.literal("Test4"),"4"),new DT_ListBoxData(Component.literal("Test"),"5"),new DT_ListBoxData(Component.literal("Test"),"6"),new DT_ListBoxData(Component.literal("Test1"),"1"),new DT_ListBoxData(Component.literal("Test2"),"2"),new DT_ListBoxData(Component.literal("Test"),"3"),new DT_ListBoxData(Component.literal("Test4"),"4"),new DT_ListBoxData(Component.literal("Test"),"5"),new DT_ListBoxData(Component.literal("Test"),"6")));

         */
        //addRenderableWidget(new CWidgetEditBox(new _BoundingBox(100,40,128,20), BorderStyles.ROUNDED_NORMAL,Component.literal("Test2")));




    }

    private void save() {
        DT_ListBoxData d = colorSelectBox.getSelectData();
        if (d != null) {
            if (d.getValue() instanceof _ColorScheme colorScheme){
                String key = ColorSchemeRegister.REGISTRY.getKey(colorScheme).toString();
                //System.out.println("key:"+key);
                ColorConfig.instance.getDatas().setColorScheme(key);
                ColorConfig.instance.save();
                ColorSchemes.setGlobal(colorScheme);
                if (this.minecraft != null) {
                    this.minecraft.setScreen(new SettingScreen());
                }
            }
        }
    }




    public List<DT_ListBoxData> getRegColor(){
        List<DT_ListBoxData> data = new ArrayList<>();
        ColorSchemeRegister.REGISTRY.forEach(colorScheme -> data.add(new DT_ListBoxData(ColorSchemeRegister.getSchemeComponent(colorScheme),colorScheme)));
        return data;
    }
}
