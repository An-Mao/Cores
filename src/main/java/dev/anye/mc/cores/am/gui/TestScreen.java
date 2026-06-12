package dev.anye.mc.cores.am.gui;

import dev.anye.mc.cores.screen.widget.CircularWidget;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TestScreen extends Screen {
	public TestScreen() {
		super(Component.empty());
	}

	@Override
	protected void init() {
		super.init();
		addRenderableWidget(new CircularWidget(width / 2, height / 2, width, height, 5, 0, 80, Component.empty(),
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
		));
	}
}
