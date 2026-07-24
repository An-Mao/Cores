package dev.anye.mc.cores;

import com.mojang.logging.LogUtils;
import dev.anye.core.system._File;
import dev.anye.mc.cores.cores.color.ColorSchemeRegister;
import dev.anye.mc.cores.cores.network.easy_net.EasyNetRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Cores.MOD_ID)
public class Cores {
    public static final String MOD_ID = "cores";
	public static final String CONFIG_DIR = _File.getFileFullPathWithRun("config", Cores.MOD_ID);
	private static final Logger LOGGER = LogUtils.getLogger();

	static {
		_File.checkAndCreateDir(CONFIG_DIR);
	}

    public Cores() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EasyNetRegister.register(modEventBus);
        ColorSchemeRegister.register(modEventBus);
    }
}
