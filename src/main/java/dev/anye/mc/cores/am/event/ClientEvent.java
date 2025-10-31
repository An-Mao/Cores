package dev.anye.mc.cores.am.event;

import com.google.common.reflect.TypeToken;
import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.color.ColorConfig;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.color.ColorSchemes;
import dev.anye.mc.cores.am.util.KeyBinding;
import dev.anye.mc.cores.cr.CoresRegs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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


    public static final ContextKey<Entity> RenderStateEntityKey = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(Cores.MOD_ID,"entity"));

}
