package dev.anye.mc.cores.screen;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.color.ColorConfig;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.color.ColorSchemes;
import dev.anye.mc.cores.am.gui.TestScreen;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import dev.anye.mc.cores.screen.widget.simple.SimpleButton;
import dev.anye.mc.cores.screen.widget.simple.SimpleDropDownSelectBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SettingScreen extends Screen {
	SimpleDropDownSelectBox colorSelectBox;
	SimpleButton saveButton;

	public SettingScreen() {
		super(Component.translatable("screen." + Cores.MOD_ID + ".settings.title"));
	}

	@Override
	protected void init() {
		super.init();
		int x = this.width / 2,
				y = this.height / 2;
		SimpleLabel label = new SimpleLabel(64, y - 8, 16, 16, Component.translatable("screen." + Cores.MOD_ID + ".settings.label.select_color"), true, false, true);
		addRenderableWidget(label);
		saveButton = new SimpleButton(x - 32, this.height - 64, 64, 16, Component.translatable("screen." + Cores.MOD_ID + ".settings.button.save"), true, false, true, this::save);
		addRenderableWidget(saveButton);
		colorSelectBox = new SimpleDropDownSelectBox(label.getX() + label.getWidth() + 5, label.getY(), 80, 16, ColorSchemeRegister.getSchemeComponent(ColorSchemes.getGlobal()), getRegColor())
				.setRadius(2);
		addRenderableWidget(colorSelectBox);
		//addRenderableWidget(new SimpleEditBox(100,16,128,20,Component.empty()));
		//SimpleButton test = new SimpleButton(10,10,50,24,Component.literal("Test"),SettingScreen::openTest);
		//addRenderableWidget(test);
	}

	public static void openTest() {
		Minecraft.getInstance().setScreenAndShow(new TestScreen());
	}

	private void save() {
		DT_ListBoxData d = colorSelectBox.getSelectData();
		if (d != null && d.getValue() instanceof _ColorScheme colorScheme) {
			Identifier identifier = ColorSchemeRegister.COLOR_SCHEME_REGISTER.getRegistry().getKey(colorScheme);
			if (identifier == null) return;
			String key = identifier.toString();
			ColorConfig.instance.ifPresent(colorConfigData -> colorConfigData.setColorScheme(key));
			ColorConfig.instance.save();
			ColorSchemes.setGlobal(colorScheme);
			this.minecraft.setScreenAndShow(new SettingScreen());

		}
	}


	public List<DT_ListBoxData> getRegColor() {
		List<DT_ListBoxData> data = new ArrayList<>();
		ColorSchemeRegister.COLOR_SCHEME_REGISTER.getRegistry().forEach(colorScheme -> data.add(new DT_ListBoxData(ColorSchemeRegister.getSchemeComponent(colorScheme), colorScheme)));
		return data;
	}

    /*
    @Override
    public void render(GuiGraphics p_281549_, int p_281550_, int p_282878_, float p_282465_) {
        super.render(p_281549_, p_281550_, p_282878_, p_282465_);
        Draw.render(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, RenderPipelines.GUI,builder -> {
            builder.addVertex(p_281549_.pose().last().pose(),10,10,0).setColor(0xffff0000);
            builder.addVertex(p_281549_.pose().last().pose(),10,100,0).setColor(0xffff0000);
            builder.addVertex(p_281549_.pose().last().pose(),100,100,0).setColor(0xffff0000);
            builder.addVertex(p_281549_.pose().last().pose(),100,100,0).setColor(0xffff0000);
            builder.addVertex(p_281549_.pose().last().pose(),100,10,0).setColor(0xffff0000);
            builder.addVertex(p_281549_.pose().last().pose(),10,10,0).setColor(0xffff0000);
        });
    }

     */
}
