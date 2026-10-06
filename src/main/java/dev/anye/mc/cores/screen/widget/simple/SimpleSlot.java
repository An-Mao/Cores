package dev.anye.mc.cores.screen.widget.simple;

import net.minecraft.network.chat.Component;

public abstract class SimpleSlot extends SimpleWidgetCore<SimpleSlot> {
	protected int slotWidth;
	protected int slotHeight;

	protected SimpleSlot(int x, int y, int w, int h, Component pMessage) {
		super(x, y, w, h, pMessage);
		setSlotHeight(16);
		setSlotWidth(16);
	}


	public void setSlotWidth(int slotWidth) {
		this.slotWidth = slotWidth;
	}

	public void setSlotHeight(int slotHeight) {
		this.slotHeight = slotHeight;
	}
}
