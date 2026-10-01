package dev.anye.mc.cores.screen.widget;

import dev.anye.mc.cores.dt.IListBoxData;
import dev.anye.mc.cores.dt.IOnPress;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;

import java.util.List;

public record SimpleListBoxData(
		Component name,
		Object value,
		List<ClientTooltipComponent> tooltip,
		IOnPress onPress
) implements IListBoxData {

	public SimpleListBoxData(Component name, Object value) {
		this(name, value, ClientTooltipComponent.create(name.getVisualOrderText()), null);
	}

	public SimpleListBoxData(Component name, Object value, IOnPress onPress) {
		this(name, value, ClientTooltipComponent.create(name.getVisualOrderText()), onPress);
	}

	public SimpleListBoxData(Component name, Object value, ClientTooltipComponent tooltip, IOnPress onPress) {
		this(name, value, List.of(tooltip), onPress);
	}

	public SimpleListBoxData(Component name, Object value, List<ClientTooltipComponent> tooltip, IOnPress onPress) {
		this.name = name;
		this.value = value;
		this.tooltip = tooltip;
		this.onPress = onPress;
	}

	public Component name() {
		return name;
	}

	public Object value() {
		return value;
	}

	public List<ClientTooltipComponent> tooltip() {
		return tooltip;
	}


	public void onPress(Object value) {
		if (onPress != null) {
			onPress.onPress(value);
		}
	}
}
