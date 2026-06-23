package dev.anye.mc.cores.am.listen;

import com.mojang.logging.LogUtils;
import com.sun.net.httpserver.HttpServer;
import dev.anye.core.exception._IOException;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.config.Configs;
import dev.anye.mc.cores.register.Register;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ListenRegister {
	private ListenRegister() {}
	private static final Logger LOGGER = LogUtils.getLogger();

	public static final Register<Listen> LISTEN_REGISTER = new Register<>(Cores.MOD_ID, "listen", builder -> builder.sync(false));

	public static final DeferredHolder<Listen,ImageListen> IMAGE_LISTEN = LISTEN_REGISTER.register("image", ImageListen::new);
	//public static final DeferredHolder<Listen,ExampleListen> EXAMPLE_LISTEN = LISTEN_REGISTER.register("example", ExampleListen::new);


	public static void register(IEventBus eventBus){
		LISTEN_REGISTER.register(eventBus);
	}
}
