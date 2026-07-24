package dev.anye.mc.cores.amlib.flash;

import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.entity.EntityHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cores.MOD_ID)
public class FlashEvent {
    public static void LivingTick(LivingEvent.LivingTickEvent event){
        if (!EntityHelper.isPlayerOrNpcOrAnimal(event.getEntity())){
            LivingEntity livingEntity = event.getEntity();
            String name = livingEntity.getPose().name();
            if (livingEntity.level().getGameTime() - livingEntity.getPersistentData().getInt("flash.time") > 200) {
                //System.out.println("name:"+name);
                if (!name.equals(livingEntity.getPersistentData().getString("flash.pose"))) {
                    livingEntity.getPersistentData().putString("flash.pose", name);
                    livingEntity.getPersistentData().putInt("flash.tick", 1);
                } else {
                    int tick = livingEntity.getPersistentData().getInt("flash.tick");
                    livingEntity.getPersistentData().putInt("flash.tick", tick + 1);
                }
            }
        }
    }
    public static void onAttack(LivingAttackEvent event){
        if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer){
            if (!EntityHelper.isPlayerOrNpcOrAnimal(event.getEntity())){
                LivingEntity livingEntity = event.getEntity();
                if (livingEntity.level().getGameTime() - livingEntity.getPersistentData().getInt("flash.time") > 200) {
                    int tick = livingEntity.getPersistentData().getInt("flash.tick");
                    if (tick > 0 && tick < 6) {
                        livingEntity.hurt(serverPlayer.damageSources().genericKill(),livingEntity.getMaxHealth());
                    }
                }
                livingEntity.getPersistentData().putLong("flash.time",livingEntity.level().getGameTime());
                livingEntity.getPersistentData().putInt("flash.tick", 0);
            }
        }
    }
}
