package dev.anye.mc.cores.screen;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.amlib.color.ColorSchemeRegister;
import dev.anye.mc.cores.amlib.color.ColorSchemes;
import dev.anye.mc.cores.amlib.config.color.ColorConfig;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import dev.anye.mc.cores.screen.widget.simple.SimpleButton;
import dev.anye.mc.cores.screen.widget.simple.SimpleDropDownSelectBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class SettingScreen extends Screen {
    SimpleDropDownSelectBox colorSelectBox;
    SimpleButton saveButton;
    public SettingScreen( ) {
        super(Component.translatable("screen.cores.settings.title"));
    }

    @Override
    protected void init() {
        super.init();
        int x = this.width / 2, y = this.height / 2;
        SimpleLabel label = new SimpleLabel(64, y - 8,16,16,Component.translatable("screen.cores.settings.label.select_color"),true,false,true);
        addRenderableWidget(label);
        colorSelectBox = new SimpleDropDownSelectBox( label.getX()+label.getWidth()+5,label.getY(), 80, 16, ColorSchemeRegister.getSchemeComponent(ColorSchemes.getGlobal()),getRegColor())
                .setRadius(2);
        addRenderableWidget(colorSelectBox);

        saveButton = new SimpleButton(x - 32, this.height - 64, 64, 16, Component.translatable("screen.cores.settings.button.save"),true,false,true,this::save);
        addRenderableWidget(saveButton);
    }

    private void save() {
        DT_ListBoxData d = colorSelectBox.getSelectData();
        if (d != null && d.getValue() instanceof _ColorScheme colorScheme) {
			String key = ColorSchemeRegister.REGISTRY.get().getKey(colorScheme).toString();
			//System.out.println("key:"+key);
			ColorConfig.INSTANCE.ifPresent(colorConfigData -> colorConfigData.setColorScheme(key));
			ColorConfig.INSTANCE.save();
			ColorSchemes.setGlobal(colorScheme);
			if (this.minecraft != null) {
				this.minecraft.setScreen(new SettingScreen());
			}
		}
    }




    public List<DT_ListBoxData> getRegColor(){
        List<DT_ListBoxData> data = new ArrayList<>();
        ColorSchemeRegister.REGISTRY.get().forEach(colorScheme -> data.add(new DT_ListBoxData(ColorSchemeRegister.getSchemeComponent(colorScheme),colorScheme)));
        return data;
    }
}
