package dev.anye.mc.cores.screen.widget;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class DT_ListBoxData {
    private final Component component;
    private final Object value;
    private final List<ClientTooltipComponent> tooltip;
    private final OnPress onPress;
    public DT_ListBoxData(Component component, Object value){
        this(component,value,ClientTooltipComponent.create(component.getVisualOrderText()),null);
    }
    public DT_ListBoxData(Component component, Object value, OnPress onPress){
        this(component,value,ClientTooltipComponent.create(component.getVisualOrderText()),onPress);
    }
    public DT_ListBoxData(Component component, Object value, ClientTooltipComponent tooltip, OnPress onPress){
        this(component,value,List.of(tooltip),onPress);
    }
    public DT_ListBoxData(Component component, Object value, List<ClientTooltipComponent> tooltip, OnPress onPress){
        this.component = component;
        this.value = value;
        this.tooltip = tooltip;
        this.onPress = onPress;
    }

    public Component getComponent() {
        return component;
    }

    public Object getValue() {
        return value;
    }

    public List<ClientTooltipComponent> getTooltip() {
        return tooltip;
    }

    public void OnPress(Object value) {
        if (onPress != null) {
            onPress.onPress(value);
        }
    }

    public interface OnPress {
        void onPress(Object value);
    }
}
