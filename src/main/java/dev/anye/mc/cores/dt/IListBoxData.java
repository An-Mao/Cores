package dev.anye.mc.cores.dt;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface IListBoxData {
	Component name();
	Object value();
	List<ClientTooltipComponent> tooltip();
	void onPress(Object value);
}
