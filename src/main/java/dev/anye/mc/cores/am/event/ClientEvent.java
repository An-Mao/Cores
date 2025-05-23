package dev.anye.mc.cores.am.event;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.color.ColorConfig;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.color.ColorSchemes;
import dev.anye.mc.cores.am.util.KeyBinding;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = Cores.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        String scheme = ColorConfig.instance.getDatas().getColorScheme();
        ResourceLocation colorSchemeRes = ResourceLocation.tryParse(scheme);
        if (colorSchemeRes != null && ColorSchemeRegister.REGISTRY.get(colorSchemeRes).isPresent()) {
            _ColorScheme colorScheme = ColorSchemeRegister.REGISTRY.get(colorSchemeRes).get().value();
            ColorSchemes.setGlobal(colorScheme);
        }
    }
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event){
        event.register(KeyBinding.OPEN_MENU);
    }
}
