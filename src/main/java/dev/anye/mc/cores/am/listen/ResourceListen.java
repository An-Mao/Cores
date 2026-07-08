package dev.anye.mc.cores.am.listen;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import dev.anye.core.exception._IOException;

public class ResourceListen extends Listen{
	protected ResourceListen(String urlPath, long closeTime) {
		super(urlPath, closeTime);
	}

	@Override
	public void context(HttpExchange exchange) {
		try {
			switch (exchange.getRequestMethod()){
				case GET -> loadBaseFile(exchange);
				case null, default -> exchange.sendResponseHeaders(405, -1);
			}
		} catch (IOException e) {
			throw new _IOException(e);
		}
	}
}
