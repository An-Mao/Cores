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
	public static final HttpServer server = createServer();

	public static final Register<Listen> LISTEN_REGISTER = new Register<>(Cores.MOD_ID, "listen", builder -> builder.sync(false));

	public static final DeferredHolder<Listen,ExampleListen> EXAMPLE_LISTEN = LISTEN_REGISTER.register("example", ExampleListen::new);



	public static HttpServer createServer(){
		try {
			LOGGER.debug("listen port:{}",Configs.GENERAL.getDatas().listenPort());
			return HttpServer.create(new InetSocketAddress(Configs.GENERAL.getDatas().listenPort()), 0);
		} catch (IOException e) {
			throw new _IOException(e);
		}
	}

	public static void initServer(){
		LOGGER.debug("register listen start");
		LISTEN_REGISTER.getRegistry().forEach(listen -> server.createContext(listen.urlPath(), listen::handle));
		LOGGER.debug("register listen done");
	}

	public static boolean isActivity(Identifier key){
		if (key == null) return false;
		boolean[] stat = {false};
		LISTEN_REGISTER.getRegistry().get(key).ifPresent(listenReference -> stat[0] = listenReference.value().isActivity());
		return stat[0];
	}
	public static boolean activate(Identifier key){
		if (key == null) return false;
		boolean[] stat = {false};
		LISTEN_REGISTER.getRegistry().get(key).ifPresent(listenReference -> {
			stat[0] = true;
			listenReference.value().activate();
		});
		return stat[0];
	}
	public static boolean close(Identifier key){
		if (key == null) return false;
		boolean[] stat = {false};
		LISTEN_REGISTER.getRegistry().get(key).ifPresent(listenReference -> {
			stat[0] = true;
			listenReference.value().close();
		});
		return stat[0];
	}

	public static void startServer(){
		server.start();
	}
	public static void stopServer(){
		server.stop(0);
	}

	public static void register(IEventBus eventBus){
		LISTEN_REGISTER.register(eventBus);
	}
}
