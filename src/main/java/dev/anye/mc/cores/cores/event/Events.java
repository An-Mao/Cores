package dev.anye.mc.cores.amlib.event;

import dev.anye.mc.cores.Cores;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Cores.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Events {
    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event)
    {
        //Net.register();
    }

}
