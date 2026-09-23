package dev.anye.mc.cores.am.event;

import com.mojang.logging.LogUtils;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.command.CommandList;
import dev.anye.mc.cores.am.listen.ListenCore;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;

@EventBusSubscriber(modid = Cores.MOD_ID)
public class Events {
	private static final Logger LOGGER = LogUtils.getLogger();

	private Events() {
	}

	@SubscribeEvent
	public static void regCommand(RegisterCommandsEvent event) {
		LOGGER.debug("register commands");
		CommandList.register(event.getDispatcher());
	}

	@SubscribeEvent
	public static void onServerStopping(ServerStoppingEvent event) {
		//?
		ListenCore.stopServer();
	}

	@SubscribeEvent
	public static void onServerTick(ServerTickEvent.Post event) {
		ListenCore.timeout();
	}
}
