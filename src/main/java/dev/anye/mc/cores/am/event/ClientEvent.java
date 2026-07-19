package dev.anye.mc.cores.am.event;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.color.ColorConfig;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.color.ColorSchemes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Cores.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        String scheme = ColorConfig.instance.getData().getColorScheme();
        Identifier colorSchemeRes = Identifier.tryParse(scheme);
        _ColorScheme colorScheme = ColorSchemeRegister.REGISTRY.get().getValue(colorSchemeRes);
        ColorSchemes.setGlobal(colorScheme);
    }


    public static final ContextKey<Entity> RenderStateEntityKey = new ContextKey<>(Identifier.fromNamespaceAndPath(Cores.MOD_ID,"entity"));

}
