package dev.anye.mc.cores.am.listen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.sun.net.httpserver.HttpExchange;
import dev.anye.core.cdt._SuffixCDT;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class Listen {
	public static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final List<String> IMAGE_SUFFIX = List.of(
			_SuffixCDT.PNG_SUFFIX,_SuffixCDT.JPG_SUFFIX,_SuffixCDT.JPEG_SUFFIX,_SuffixCDT.GIF_SUFFIX
	);
	public static final String POST = "POST";
	public static final String GET = "GET";
	protected final Logger logger = LogUtils.getLogger();
	protected Gson gson = new Gson();
	protected final String urlPath;

	protected final boolean autoClose;
	protected final long closeTime;

	protected boolean closed = false;
	protected long lastActivityTime;

	protected Listen(String urlPath,long closeTime){
		this.urlPath = urlPath;
		this.autoClose = closeTime > 0;
		this.closeTime = closeTime;
		activate();
	}
	protected Listen(String urlPath){
		this(urlPath,600);
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



	public Map<String, String> parseQuery(String query) {
		if (query == null || query.trim().isEmpty()) return Map.of();
		Map<String, String> result = new HashMap<>();
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			try {
				String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
				String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
				result.put(key, value);
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
		return result;
	}


	public void sendJson(HttpExchange exchange, int status, JsonElement json) throws IOException {
		send(exchange, status, PRETTY_GSON.toJson(json), "application/json; charset=utf-8");
	}
	public void send(HttpExchange exchange, int status, String content, String contentType) throws IOException {
		send(exchange, status, content.getBytes(StandardCharsets.UTF_8), contentType);
	}

	public void send(HttpExchange exchange, int status, byte[] content, String contentType) throws IOException {
		exchange.getResponseHeaders().set("Content-Type", contentType);
		exchange.sendResponseHeaders(status, content.length);
		try (OutputStream stream = exchange.getResponseBody()) {
			stream.write(content);//stream.close();
		}

	}


	public String mime(String path) {
		String lower = path.toLowerCase(Locale.ROOT);
		if (lower.endsWith(_SuffixCDT.HTML_SUFFIX)) return "text/html; charset=utf-8";
		if (lower.endsWith(_SuffixCDT.JS_SUFFIX)) return "application/javascript; charset=utf-8";
		if (lower.endsWith(_SuffixCDT.CSS_SUFFIX)) return "text/css; charset=utf-8";
		if (lower.endsWith(_SuffixCDT.JSON_SUFFIX)) return "application/json; charset=utf-8";
		if (lower.endsWith(_SuffixCDT.PNG_SUFFIX)) return "image/png";
		if (lower.endsWith(_SuffixCDT.JPG_SUFFIX) || lower.endsWith(_SuffixCDT.JPEG_SUFFIX)) return "image/jpeg";
		if (lower.endsWith(_SuffixCDT.GIF_SUFFIX)) return "image/gif";
		return "application/octet-stream";
	}
	public void sendFile(HttpExchange exchange, String filePath, String contentType) throws IOException {
		byte[] bytes = readFile(filePath);
		if (bytes == null) {
			sendJson(exchange, 404, error("not_found", "Missing resource: " + filePath));
			return;
		}
		send(exchange, 200, bytes, contentType);
	}

	public void sendResource(HttpExchange exchange, String resourcePath, String contentType) throws IOException {
		byte[] bytes = readAssetsResource(resourcePath);
		if (bytes == null) {
			sendJson(exchange, 404, error("not_found", "Missing resource: " + resourcePath));
			return;
		}
		send(exchange, 200, bytes, contentType);
	}

	public JsonObject ok(String message) {
		JsonObject json = new JsonObject();
		json.addProperty("ok", true);
		json.addProperty("message", message);
		return json;
	}

	public JsonObject error(String code, String message) {
		JsonObject json = new JsonObject();
		json.addProperty("ok", false);
		json.addProperty("code", code);
		json.addProperty("message", message == null ? "" : message);
		return json;
	}
	public @Nullable byte[] readFile(String file) throws IOException {
		return readFile(Path.of(file));
	}

	public @Nullable byte[] readFile(Path file) throws IOException {
		if (!Files.exists(file)) return null;
		return Files.readAllBytes(file);
	}

	public @Nullable byte[] readAssetsResource(String resourcePath) throws IOException {
		try (InputStream stream = Listen.class.getClassLoader().getResourceAsStream(resourcePath)) {
			if (stream != null) return stream.readAllBytes();
		}
		try {
			Path devPath = Path.of("src", "main", "resources").resolve(resourcePath).normalize();
			if (Files.exists(devPath)) return Files.readAllBytes(devPath);
		} catch (InvalidPathException e) {
			logger.warn(e.getMessage());
		}
		return null;
	}














	public abstract void context(HttpExchange exchange);
}
