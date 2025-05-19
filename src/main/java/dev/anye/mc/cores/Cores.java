package dev.anye.mc.cores;

import dev.anye.core.javascript._EasyJS;
import dev.anye.core.system._File;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.register.DataRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Cores.MOD_ID)
public class Cores {
    public static final String MOD_ID = "cores";
    public static final String CONFIG_DIR = _File.getFileFullPathWithRun("config/"+Cores.MOD_ID+"/");
    static {
        _File.checkAndCreateDir(CONFIG_DIR);
    }
    public Cores(IEventBus modEventBus, ModContainer modContainer) {
        //load();
        ColorSchemeRegister.register(modEventBus);
        DataRegister.register(modEventBus);
        /*
        _EasyJS easyJS = _EasyJS.NotSafe();
        System.out.println(easyJS.runCode("1+1"));

         */
    }
    private void load(){
        //_File.checkAndCreateDir(CONFIG_DIR);
    }
}
