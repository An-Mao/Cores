package dev.anye.mc.cores.am.gui;

import dev.anye.mc.cores.screen.widget.simple.SimpleEditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TestScreen extends Screen {
	public TestScreen() {
		super(Component.empty());
	}

	@Override
	protected void init() {
		super.init();
		int x = 10;
		int y = 10;
		/*addRenderableWidget(new SimpleLabel(x,y,128,120,Component.literal("Test"),0xFFFFFFFF,0x00000000,0xffffffff,false,false,true).setPreferredGlowColor(0xFFFF00FF));

		addRenderableWidget(new SimpleEditBox(x + 140 ,y,128,120,Component.literal("Test1")).setBorderUsualColor(0xFFFFFFFF).setTextUsualColor(0xffffffff).setGlowConfig(
				SimpleWidgetCoreNX.GlowConfig.defaultConfig()
						.setEnabled(true)
						.setIntensity(0.9f)
						.setRange(8)
						.setInnerGlow(true)
						.setOuterGlow(true)
						.setPreferredColor(0xFFFF00FF)
		));*/

		addRenderableWidget(new SimpleEditBox(x + 140 ,y,128,120,Component.literal("Test1")));

		/*addRenderableWidget(new SimpleEditBox(x,y+30,128,20,Component.empty()));
		addRenderableWidget(new SimpleEditBox(x,y+60,128,20,Component.empty()));
		addRenderableWidget(new SimpleEditBox(x,y+90,128,20,Component.empty()));
		addRenderableWidget(new SimpleEditBox(x,y+120,128,20,Component.empty()));*/

		/*addRenderableWidget(new CircularWidget(width / 2, height / 2, width, height, 5, 0, 80, Component.empty(),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "123"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
				new DT_ListBoxData(Component.literal("HHHHHHHHHHHHHHHHHHHHHHHHHHHH"), "HHHHHHHHHHHHHHHHHHHHHHHHHHHH")
		));*/
	}
}
