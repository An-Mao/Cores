package nws.mc.cores;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import nws.mc.cores.amlib.color.ColorSchemeRegister;
import nws.mc.cores.amlib.color.ColorSchemes;

@Mod(Cores.MOD_ID)
public class Cores {
    public static final String MOD_ID = "cores";
    public Cores(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ColorSchemeRegister.register(modEventBus);
        test();
    }
    private void test( ) {
        //System.out.println("colorScheme:"+ ColorSchemes.getGlobal());
    }

}
