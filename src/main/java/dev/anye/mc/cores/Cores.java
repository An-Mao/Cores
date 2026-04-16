package dev.anye.mc.cores;

import com.mojang.logging.LogUtils;
import dev.anye.core.system._File;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.config.MixinConfigs;
import dev.anye.mc.cores.am.register.DataRegister;
import dev.anye.mc.cores.js.Js;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

@Mod(Cores.MOD_ID)
public class Cores {
    public static final String MOD_ID = "cores";
    public static final String CONFIG_DIR = _File.getFileFullPathWithRun("","config",Cores.MOD_ID);
    private static final Logger LOGGER = LogUtils.getLogger();
    static {
        _File.checkAndCreateDir(CONFIG_DIR);
    }
    public Cores(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerRegistries);
        ColorSchemeRegister.register(modEventBus);
        DataRegister.register(modEventBus);

        modEventBus.addListener(this::commonSetup);


        if (Js.GraalJs) LOGGER.info("GraalJS is loaded. GraalJS Mode");
        else LOGGER.info("GraalJS not loaded. Nashorn Mode");

    }

    private void commonSetup(FMLCommonSetupEvent event) {
        /*
        _EasyJS easyJS = _EasyJS.NotSafe();
        System.out.println(easyJS.runCode("1+1"));

         */
    }

    private void registerRegistries(NewRegistryEvent event) {
        event.register(ColorSchemeRegister.REGISTRY);
    }


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
