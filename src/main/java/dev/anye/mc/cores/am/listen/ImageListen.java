package dev.anye.mc.cores.am.listen;

import com.sun.net.httpserver.HttpExchange;
import dev.anye.core.exception._IOException;
import dev.anye.core.system._File;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class ImageListen extends Listen {

	protected ImageListen() {
		super("assets/cores/html/image/", "image", 300);
	}

	@Override
	public void get(HttpExchange exchange) {
		try {
			load(exchange);
		} catch (IOException e) {
			throw new _IOException(e);
		}
	}

	@Override
	public void other(HttpExchange exchange) {
		try {
			exchange.sendResponseHeaders(405, -1);
		} catch (IOException e) {
			throw new _IOException(e);
		}
	}

	public void load(HttpExchange exchange) throws IOException {
		Map<String, String> q = parseQuery(exchange.getRequestURI().getQuery());
		if (q.containsKey("type")) {
			String path = q.get("path");
			if (path == null || path.isBlank()) return;
			String t = path.substring(path.lastIndexOf(".")).toLowerCase(Locale.ROOT);
			if (!IMAGE_SUFFIX.contains(t)) {
				sendJson(exchange, 404, error("error path", "Unsupported image type"));
				return;
			}
			switch (q.get("type")) {
				case "assets": {
					String ns = "minecraft";
					if (path.contains(":")) {
						String[] ps = path.split(":");
						ns = ps[0];
						path = ps[1];
					}
					sendResource(exchange, "assets/" + ns + "/" + path, mime(path));
					break;
				}
				case "file":
					//Lock the file path to the current game directory.
					sendFile(exchange, _File.getFileFullPathWithRun(path), mime(path));
					break;
				case null, default:
					sendJson(exchange, 404, error("error type", "Unknow type"));
			}
		} else {
			sendJson(exchange, 404, error("error type", "Missing type"));
		}
	}

}
