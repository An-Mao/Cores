package dev.anye.mc.cores;

import com.mojang.logging.LogUtils;
import dev.anye.core.system._File;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.register.DataRegister;
import dev.anye.mc.cores.js.Js;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Cores.MOD_ID)
public class Cores {
    public static final String MOD_ID = "cores";
    public static final String CONFIG_DIR = _File.getFileFullPathWithRun("config/"+Cores.MOD_ID+"/");
    private static final Logger LOGGER = LogUtils.getLogger();
    static {
        _File.checkAndCreateDir(CONFIG_DIR);
    }
    public Cores(IEventBus modEventBus, ModContainer modContainer) {
        //load();
        ColorSchemeRegister.register(modEventBus);
        DataRegister.register(modEventBus);

        if (Js.CanRun) LOGGER.info("GraalJS is loaded. js code can run");
        else LOGGER.info("GraalJS not loaded. js code can't run");
        /*
        _EasyJS easyJS = _EasyJS.NotSafe();
        System.out.println(easyJS.runCode("1+1"));

         */


    }
    private void load(){
        //_File.checkAndCreateDir(CONFIG_DIR);
    }
}
