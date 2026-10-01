package dev.anye.mc.cores.screen;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.am.color.ColorConfig;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.color.ColorSchemes;
import dev.anye.mc.cores.screen.widget.SimpleListBoxData;
import dev.anye.mc.cores.screen.widget.simple.SimpleButton;
import dev.anye.mc.cores.screen.widget.simple.SimpleDropDownSelectBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleLabel;
import dev.anye.mc.cores.screen.widget.simple.SimpleWidgetCore;
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
		super(Component.translatable("screen.cores.settings.title"));
	}

	@Override
	protected void init() {
		super.init();
		int centerX = this.width / 2;
		int centerY = this.height / 2;

		int ix = 10;
		int iy = 10;
		SimpleButton button = new SimpleButton(
				ix, iy,
				30, 20,
				Component.translatable("screen.cores.settings.button.chang_background"),
				true, false, true,
				()->{
					SimpleWidgetCore.setBackground();
					this.minecraft.setScreenAndShow(new SettingScreen());
				});
		addRenderableWidget(button);
		ix += button.getWidth() + 10;

		button = new SimpleButton(
				ix, iy,
				30, 20,
				Component.translatable("screen.cores.settings.button.chang_rounded"),
				true, false, true,
				()->{
					SimpleWidgetCore.setRounded();
					this.minecraft.setScreenAndShow(new SettingScreen());
				});
		addRenderableWidget(button);

		ix += button.getWidth() + 10;
		button = new SimpleButton(
				ix, iy,
				30, 20,
				Component.translatable("screen.cores.settings.button.chang_glow"),
				true, false, true,
				()->{
					SimpleWidgetCore.setGlow();
					this.minecraft.setScreenAndShow(new SettingScreen());
				});
		addRenderableWidget(button);

		ix += button.getWidth() + 10;
		button = new SimpleButton(
				ix, iy,
				30, 20,
				Component.translatable("screen.cores.settings.button.inner_glow"),
				true, false, true,
				()->{
					SimpleWidgetCore.setInnerGlow();
					this.minecraft.setScreenAndShow(new SettingScreen());
				});
		addRenderableWidget(button);

		ix += button.getWidth() + 10;
		button = new SimpleButton(
				ix, iy,
				30, 20,
				Component.translatable("screen.cores.settings.button.outer_glow"),
				true, false, true,
				()->{
					SimpleWidgetCore.setOuterGlow();
					this.minecraft.setScreenAndShow(new SettingScreen());
				});
		addRenderableWidget(button);

		ix = 10;
		iy += 50;
		SimpleLabel label = new SimpleLabel(ix, iy, 20, 20, Component.translatable("screen.cores.settings.label.select_color"))
				.setAutoWidth(true).setCenterText(true);
		addRenderableWidget(label);
		ix += label.getWidth() + 10;
		colorSelectBox = new SimpleDropDownSelectBox(ix, iy, 80, 20, ColorSchemeRegister.getSchemeComponent(ColorSchemes.getGlobal()), getRegColor())
				.setRadius(2);
		addRenderableWidget(colorSelectBox);


		saveButton = new SimpleButton(centerX, this.height - 35, 64, 20, Component.translatable("screen.cores.settings.button.save"), true, false, true, this::save);
		addRenderableWidget(saveButton);

		ix = 10;
		iy += 50;
		SimpleButton test = new SimpleButton(ix,iy,30,20,Component.literal("Test"),SettingScreen::openTest);
		addRenderableWidget(test);
	}

	public static void openTest() {
		Minecraft.getInstance().setScreenAndShow(new TestScreen());
	}

	private void save() {
		SimpleListBoxData d = colorSelectBox.getSelectData();
		if (d != null && d.value() instanceof _ColorScheme colorScheme) {
			Identifier identifier = ColorSchemeRegister.COLOR_SCHEME_REGISTER.getRegistry().getKey(colorScheme);
			if (identifier == null) return;
			String key = identifier.toString();
			ColorConfig.instance.update(colorConfigData -> colorConfigData.setColorScheme(key));
			ColorConfig.instance.save();
			ColorSchemes.setGlobal(colorScheme);
			this.minecraft.setScreenAndShow(new SettingScreen());

		}
	}


	public List<SimpleListBoxData> getRegColor() {
		List<SimpleListBoxData> data = new ArrayList<>();
		ColorSchemeRegister.COLOR_SCHEME_REGISTER.getRegistry().forEach(colorScheme -> data.add(new SimpleListBoxData(ColorSchemeRegister.getSchemeComponent(colorScheme), colorScheme)));
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
