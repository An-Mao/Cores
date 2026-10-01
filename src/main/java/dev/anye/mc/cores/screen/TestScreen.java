package dev.anye.mc.cores.screen;

import dev.anye.mc.cores.screen.widget.SimpleListBoxData;
import dev.anye.mc.cores.screen.widget.simple.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class TestScreen extends Screen {
	private static final List<SimpleListBoxData> testData = List.of(
			new SimpleListBoxData(Component.literal("0"), "0"),
			new SimpleListBoxData(Component.literal("1"), "1"),
			new SimpleListBoxData(Component.literal("2"), "2"),
			new SimpleListBoxData(Component.literal("3"), "3"),
			new SimpleListBoxData(Component.literal("4"), "4"),
			new SimpleListBoxData(Component.literal("5"), "5"),
			new SimpleListBoxData(Component.literal("6"), "6"),
			new SimpleListBoxData(Component.literal("7"), "7"),
			new SimpleListBoxData(Component.literal("8"), "8"),
			new SimpleListBoxData(Component.literal("9"), "9"),
			new SimpleListBoxData(Component.literal("10"), "10"),
			new SimpleListBoxData(Component.literal("11"), "11"),
			new SimpleListBoxData(Component.literal("12"), "12"),
			new SimpleListBoxData(Component.literal("13"), "13"),
			new SimpleListBoxData(Component.literal("14"), "14"),
			new SimpleListBoxData(Component.literal("15"), "15"),
			new SimpleListBoxData(Component.literal("16"), "16"),
			new SimpleListBoxData(Component.literal("17"), "17"),
			new SimpleListBoxData(Component.literal("18"), "18"),
			new SimpleListBoxData(Component.literal("19"), "19"));
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
		SimpleButton button = new SimpleButton(
				x, y,
				30, 20,
				Component.literal("home"),
				true, false, true,
				()-> this.minecraft.setScreenAndShow(new SettingScreen()));
		addRenderableWidget(button);
		x += button.getWidth();
		addRenderableWidget(new SimpleEditBox(x ,y,60,20,Component.empty()));
		y += 30;
		addRenderableWidget(new SimpleListBox(x ,y,300,70,40,20, testData));
		y += 80;
		SimpleInventory inventory = new SimpleInventory(x ,y,Component.empty());
		addRenderableWidget(inventory);
		y += inventory.getHeight();
		addRenderableWidget(new SimpleHotbar(x ,y,Component.empty()));


		/*addRenderableWidget(new SimpleEditBox(x,y+30,128,20,Component.empty()));
		addRenderableWidget(new SimpleEditBox(x,y+60,128,20,Component.empty()));
		addRenderableWidget(new SimpleEditBox(x,y+90,128,20,Component.empty()));
		addRenderableWidget(new SimpleEditBox(x,y+120,128,20,Component.empty()));*/

		/*addRenderableWidget(new CircularWidget(w / 2, height / 2, w, height, 5, 0, 80, Component.empty(),
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
