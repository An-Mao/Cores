package dev.anye.mc.cores;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.am.color.ColorConfig;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.color.ColorSchemes;
import dev.anye.mc.cores.am.util.KeyBinding;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;


@Mod(value = Cores.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Cores.MOD_ID, value = Dist.CLIENT)
public class CoresClient {
    public CoresClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        //ontainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
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
