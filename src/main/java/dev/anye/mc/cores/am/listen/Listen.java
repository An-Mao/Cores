package dev.anye.mc.cores.am.listen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.sun.net.httpserver.HttpExchange;

import dev.anye.mc.cores.am.config.ListenConfig;

import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class Listen extends ListenCDT{
	public static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

	protected final Logger logger = LogUtils.getLogger();
	protected final Gson gson = new Gson();

	protected final String localPath;
	protected final String rawUrlPath;
	protected final String urlPath;
	protected final boolean autoClose;
	protected final long closeTime;

	protected boolean closed = false;
	protected long lastActivityTime;

	protected Listen(String localPath, String rawUrlPath, long closeTime){
		this.localPath = localPath;
		this.rawUrlPath = rawUrlPath;
		this.urlPath = optimizePath(rawUrlPath);
		this.autoClose = closeTime > 0;
		this.closeTime = closeTime;
		activate();
	}
	protected Listen(String localPath,String rawUrlPath){
		this(localPath, rawUrlPath,600);
	}
	protected Listen(String rawUrlPath, long closeTime){
		this(rawUrlPath, rawUrlPath,closeTime);
	}
	protected Listen(String rawUrlPath){
		this(rawUrlPath, rawUrlPath);
	}

	public String optimizePath(String rawUrlPath){
		if (rawUrlPath.startsWith(URL_SEPARATOR)){
			if (rawUrlPath.endsWith(URL_SEPARATOR)) return rawUrlPath;
			else return rawUrlPath + URL_SEPARATOR;
		}else if (rawUrlPath.endsWith(URL_SEPARATOR)) return URL_SEPARATOR + rawUrlPath;
		else return URL_SEPARATOR + rawUrlPath + URL_SEPARATOR;
	}

	public String urlPath(){
		return urlPath;
	}

	public void close(){
		this.closed = true;
	}

	public void activate(){
		this.closed = false;
		this.lastActivityTime = getSystemTime();
	}

	public String getClientIp(HttpExchange exchange) {
		InetSocketAddress remoteAddress = exchange.getRemoteAddress();
		return remoteAddress.getAddress().getHostAddress();
	}


	public boolean checkIp(HttpExchange exchange){
		String ip = getClientIp(exchange);
		//logger.debug("user ip => {}",ip);
		return ListenConfig.LISTEN_CONFIG.checkIp(ip);
	}


	public void handle(HttpExchange exchange){
		if (closed) return;
		if (!checkIp(exchange)) return;
		if (autoClose) {
			if (!isActivity()) return;
			lastActivityTime = getSystemTime();
			ListenCore.setTimeout(lastActivityTime);
		}
		switch (exchange.getRequestMethod().toUpperCase()) {
			case GET:
				get(exchange);
				break;
			case POST:
				post(exchange);
				break;
			default: other(exchange);
				break;
		}
		context(exchange);
		//if (!loadBaseFile(exchange)) context(exchange);
	}


	
	public void context(HttpExchange exchange){}

	public void get(HttpExchange exchange){
		if (normalAccess(exchange)) loadBaseFile(exchange);
	}
	public void post(HttpExchange exchange){}
	public void other(HttpExchange exchange){}







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

	public void send(HttpExchange exchange, int status, String content, String contentType) throws IOException {
		send(exchange, status, content.getBytes(StandardCharsets.UTF_8), contentType);
	}
	public void send(HttpExchange exchange, int status, byte[] content, String contentType) throws IOException {
		exchange.getResponseHeaders().set("Content-Type", contentType);
		exchange.sendResponseHeaders(status, content.length);
		try (OutputStream stream = exchange.getResponseBody()) {
			stream.write(content);
			//stream.close();
		}

	}
	public void sendJson(HttpExchange exchange, int status, JsonElement json) throws IOException {
		send(exchange, status, PRETTY_GSON.toJson(json), "application/json; charset=utf-8");
	}
	public void sendFile(HttpExchange exchange, String filePath, String contentType) throws IOException {
		if (ListenConfig.LISTEN_CONFIG.checkPath(filePath)) {
			byte[] bytes = readFile(filePath);
			if (bytes == null) {
				sendJson(exchange, 404, error("not_found", "Missing resource: " + filePath));
				return;
			}
			send(exchange, 200, bytes, contentType);
		}else sendJson(exchange,401, error("Access not allowed","Path disabled"));
	}
	public void sendResource(HttpExchange exchange, String resourcePath, String contentType) throws IOException {
		if (ListenConfig.LISTEN_CONFIG.checkPath(resourcePath)){
			byte[] bytes = readAssetsResource(resourcePath);
			if (bytes == null) {
				sendJson(exchange, 404, error("not_found", "Missing resource: " + resourcePath));
				return;
			}
			send(exchange, 200, bytes, contentType);
		}else sendJson(exchange,401, error("Access not allowed","Path disabled"));
	}
	public boolean sendDefaultResource(HttpExchange exchange,String path){
		try {
			sendResource(exchange, localPath + path, mime(path));
			return true;
		} catch (IOException e) {
			this.logger.warn("Assets error => {}",e.getMessage());
		}
		return false;
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



	public boolean loadBaseFile(HttpExchange exchange) {
		String path = exchange.getRequestURI().getPath();
		if (path.startsWith(urlPath)) {
			path = path.substring(urlPath.length());
			if (path.isEmpty() || !checkSuffix(path , WEB_SUFFIX)) return false;
			return sendDefaultResource(exchange,path);
		}
		return false;
	}

	public boolean normalAccess(HttpExchange exchange){
		if (exchange.getRequestMethod().equalsIgnoreCase(GET)) {
			String q = exchange.getRequestURI().getQuery();
			return q == null || q.isEmpty();
		}
		return false;
	}

	public String getFileSuffix(String name){
		return name.substring(name.lastIndexOf(".")).toLowerCase(Locale.ROOT);
	}

	public String mime(String path) {
		return getContentType(getFileSuffix(path.toLowerCase(Locale.ROOT)));
	}
	public boolean checkSuffix(String name,List<String> suffixes){
		return suffixes.contains(getFileSuffix(name));
	}

}
