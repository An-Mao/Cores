package dev.anye.mc.cores;

import dev.anye.mc.cores.amlib.color.ColorSchemeRegister;
import dev.anye.mc.cores.amlib.network.easy_net.EasyNetRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Cores.MOD_ID)
public class Cores {
    public static final String MOD_ID = "cores";
    public Cores() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EasyNetRegister.register(modEventBus);
        ColorSchemeRegister.register(modEventBus);
        test();
    }
    private void test(){
    }
}
