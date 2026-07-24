package dev.anye.mc.cores.cores.test;

import dev.anye.mc.cores.Cores;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cores.MOD_ID)
public class RenderTest {
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event){
        //System.out.println(event.getEntity().getName()+"::"+event.getEntity().getHealth()+"::"+event.getEntity().getMaxHealth());
    }
}
