package dev.anye.mc.cores.am.event;

import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.config.MixinConfigs;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class Events {
    @Mod.EventBusSubscriber(modid = Cores.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void commonSetup(final FMLCommonSetupEvent event)
        {
        }
    }

    @Mod.EventBusSubscriber(modid = Cores.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class GameEvents {
        /*
        @SubscribeEvent
        public static void onLevelChange(PlayerXpEvent.LevelChange levelChange) {
            if (levelChange.getEntity() instanceof ServerPlayer serverPlayer) {
                int points;
                if (levelChange.getLevels() < 0) points = PlayerHelper.getExperienceForLevel(-levelChange.getLevels());
                else points = PlayerHelper.getExperienceForLevel(levelChange.getLevels());
                serverPlayer.giveExperiencePoints(points);
                levelChange.setCanceled(true);
            }
        }

         */

        @SubscribeEvent
        public static void regCommand(RegisterCommandsEvent event) {
            //CommandList commandList = new CommandList(event.getDispatcher());
            //commandList.register();
            event.getDispatcher()
                    .register(Commands.literal(Cores.MOD_ID)
                            .then(Commands.literal("mixin")
                                    .then(Commands.literal("playerLevel")
                                            .executes(
                                            context -> {
                                                if (context != null){
                                                    MixinConfigs.EnableFixLevel = !MixinConfigs.EnableFixLevel;
                                                    context.getSource().sendSuccess(() -> Component.translatable("command.cores.mixin.player_level").append(Component.translatable(MixinConfigs.EnableFixLevel?"command.cores.mixin.player_level.enable":"command.cores.mixin.player_level.disable")), false);
                                                    return 1;
                                                }
                                                return 0;
                                            })
                                    )
                            )
                    );
        }
    }

}
