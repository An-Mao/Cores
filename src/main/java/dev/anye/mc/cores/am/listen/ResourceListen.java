package dev.anye.mc.cores.am.listen;

import com.sun.net.httpserver.HttpExchange;

public class ResourceListen extends Listen{
	protected ResourceListen(String urlPath, long closeTime) {
		super(urlPath, closeTime);
	}

	@Override
	public void context(HttpExchange exchange) {

	}
}
