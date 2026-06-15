package dev.anye.mc.cores.am.listen;

import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;

public abstract class Listen {
	public static final String POST = "POST";
	public static final String GET = "GET";
	protected final Logger LOGGER = LogUtils.getLogger();
	protected Gson gson = new Gson();
	protected final String urlPath;

	protected final boolean autoClose;
	protected final long closeTime;

	protected boolean closed = false;
	protected long lastActivityTime;

	protected Listen(String urlPath,boolean autoClose,long closeTime){
		this.urlPath = urlPath;
		this.autoClose = autoClose;
		this.closeTime = closeTime;
		activate();
	}
	protected Listen(String urlPath){
		this(urlPath,true,600);
	}

	public String urlPath(){
		if (urlPath.startsWith("/")) return urlPath;
		return "/"+urlPath;
	}
	public void close(){
		this.closed = true;
	}

	public void activate(){
		this.closed = false;
		this.lastActivityTime = getSystemTime();
	}

	public void handle(HttpExchange exchange){
		if (closed) return;
		if (autoClose) {
			if (isActivity()) {
				lastActivityTime = getSystemTime();
				context(exchange);
			}
		}else {
			context(exchange);
		}
	}

	public boolean isActivity(){
		return getSystemTime() - lastActivityTime < closeTime;
	}

	public long getSystemTime(){
		return System.currentTimeMillis() / 1000;
	}
	public abstract void context(HttpExchange exchange);
}
