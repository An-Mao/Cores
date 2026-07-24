package dev.anye.mc.cores.cores.event;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.cores.color.ColorSchemeRegister;
import dev.anye.mc.cores.cores.color.ColorSchemes;
import dev.anye.mc.cores.cores.config.color.ColorConfig;
import dev.anye.mc.cores.cores.util.KeyBinding;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Cores.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
		ColorConfig.INSTANCE.ifPresent(colorConfigData -> {
			String scheme = colorConfigData.getColorScheme();
			ResourceLocation colorSchemeRes = ResourceLocation.tryParse(scheme);
			_ColorScheme colorScheme = ColorSchemeRegister.REGISTRY.get().getValue(colorSchemeRes);
			ColorSchemes.setGlobal(colorScheme);
		});
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.OPEN_MENU);

    }
}
