package dev.anye.mc.cores;

import dev.anye.core.system._File;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.register.DataReg;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Cores.MOD_ID)
public class Cores {
    public static final String MOD_ID = "cores";
    public static final String CONFIG_DIR = _File.getFileFullPathWithRun("","config",Cores.MOD_ID);
    static {
        load();
    }
    public Cores(FMLJavaModLoadingContext context) {
        BusGroup modEventBus = context.getModBusGroup();
        ColorSchemeRegister.register(modEventBus);
        DataReg.reg(modEventBus);
        /*
        _EasyJS easyJS = new _EasyJS();
        System.out.println("Cores init");
        System.out.println(easyJS.runCode("1+1"));

         */
    }
    private static void load(){
        _File.checkAndCreateDir(CONFIG_DIR);
    }
}
