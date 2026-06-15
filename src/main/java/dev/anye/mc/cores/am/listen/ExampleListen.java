package dev.anye.mc.cores.am.listen;

import com.sun.net.httpserver.HttpExchange;
import dev.anye.core.exception._IOException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ExampleListen extends Listen{
	protected ExampleListen() {
		super("example");
	}

	@Override
	public void context(HttpExchange exchange) {
		try {
			LOGGER.debug("example listen");
			switch (exchange.getRequestMethod()){
				case POST,GET -> test(exchange);
				case null, default -> exchange.sendResponseHeaders(405, -1);
			}
		} catch (IOException e) {
			throw new _IOException(e);
		}
	}


	private void test(HttpExchange exchange){
		try {
			InputStream inputStream = exchange.getRequestBody();
			String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
			LOGGER.debug("json => {}", json);
			StringBuilder response = new StringBuilder();
			response.append("success");
			exchange.sendResponseHeaders(200, response.toString().getBytes().length);
			OutputStream os = exchange.getResponseBody();
			os.write(response.toString().getBytes());
			os.close();
		} catch (IOException e) {
			throw new _IOException(e);
		}
	}
}
