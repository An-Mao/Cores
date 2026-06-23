package dev.anye.mc.cores.am.listen;

import com.mojang.logging.LogUtils;
import com.sun.net.httpserver.HttpServer;
import dev.anye.core.exception._IOException;
import dev.anye.mc.cores.am.config.Configs;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ListenCore{
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final HttpServer server = createServer();

	private ListenCore(){}
	public static HttpServer createServer(){
		try {
			LOGGER.debug("listen port:{}", Configs.GENERAL.getDatas().listenPort());
			return HttpServer.create(new InetSocketAddress(Configs.GENERAL.getDatas().listenPort()), 0);
		} catch (IOException e) {
			throw new _IOException(e);
		}
	}

	public static void initServer(){
		LOGGER.debug("register listen start");
		ListenRegister.LISTEN_REGISTER.getRegistry().forEach(listen -> server.createContext(listen.urlPath(), listen::handle));
		LOGGER.debug("register listen done");
		server.setExecutor(null);
	}

	public static boolean isActivity(Identifier key){
		if (key == null) return false;
		boolean[] stat = {false};
		ListenRegister.LISTEN_REGISTER.getRegistry().get(key).ifPresent(listenReference -> stat[0] = listenReference.value().isActivity());
		return stat[0];
	}
	public static boolean activate(Identifier key){
		if (key == null) return false;
		boolean[] stat = {false};
		ListenRegister.LISTEN_REGISTER.getRegistry().get(key).ifPresent(listenReference -> {
			stat[0] = true;
			listenReference.value().activate();
		});
		return stat[0];
	}
	public static boolean close(Identifier key){
		if (key == null) return false;
		boolean[] stat = {false};
		ListenRegister.LISTEN_REGISTER.getRegistry().get(key).ifPresent(listenReference -> {
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
}
