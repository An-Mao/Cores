package dev.anye.mc.cores.am.listen;

import com.mojang.logging.LogUtils;
import com.sun.net.httpserver.HttpServer;
import dev.anye.core.exception._IOException;
import dev.anye.core.system._Time;
import dev.anye.mc.cores.am.config.ListenConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ListenCore{
	private static final Logger LOGGER = LogUtils.getLogger();
	public static HttpServer server = createServer();
	private static long timeout = 0;

	private ListenCore(){}

	public static HttpServer createServer(ServerPlayer serverPlayer){
		if (serverPlayer != null){
			LOGGER.warn("The server was started by player {}",serverPlayer.getName());
			return cs();
		}
		return null;
	}

	private static HttpServer cs(){
		try {
			int port = ListenConfig.LISTEN_CONFIG.map(ListenConfig.Data::listenPort).orElse(0);
			if (port == 0) return null;
			LOGGER.debug("listen port:{}", port);
			timeout = System.currentTimeMillis() / 1000L;
			return HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			throw new _IOException(e);
		}
	}

	public static void timeout(){
		int t = ListenConfig.LISTEN_CONFIG.map(ListenConfig.Data::timeout).orElse(0);
		if ( t == -1) return;
		if ((System.currentTimeMillis() / 1000L) - timeout >= t){
			stopServer();
			server = null;
		}
	}


	public static HttpServer createServer(){
		if (!ListenConfig.LISTEN_CONFIG.map(ListenConfig.Data::enable).orElse(false)) return null;
		return cs();
	}

	public static void initServer(){
		if (server == null) return;
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
		if (server == null) return;
		server.start();
	}
	public static void stopServer(){
		if (server == null) return;
		server.stop(0);
	}

	public static void setTimeout(long lastActivityTime) {
		timeout = lastActivityTime;
	}
}
