package nws.mc.cores.amlib.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import nws.dev.core.color.scheme._ColorScheme;
import nws.mc.cores.Cores;
import nws.mc.cores.amlib.color.ColorSchemeRegister;
import nws.mc.cores.amlib.color.ColorSchemes;
import nws.mc.cores.amlib.config.color.ColorConfig;
import nws.mc.cores.amlib.util.KeyBinding;

@Mod.EventBusSubscriber(modid = Cores.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        String scheme = ColorConfig.instance.getDatas().getColorScheme();
        ResourceLocation colorSchemeRes = ResourceLocation.tryParse(scheme);
        _ColorScheme colorScheme = ColorSchemeRegister.REGISTRY.get().getValue(colorSchemeRes);
        ColorSchemes.setGlobal(colorScheme);
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.OPEN_MENU);

    }
}
