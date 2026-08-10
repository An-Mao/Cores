package dev.anye.mc.cores.am.listen;

import dev.anye.core.cdt._SuffixCDT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListenCDT {
	public static final List<String> IMAGE_SUFFIX = List.of(
			_SuffixCDT.PNG_SUFFIX, _SuffixCDT.JPG_SUFFIX, _SuffixCDT.JPEG_SUFFIX, _SuffixCDT.GIF_SUFFIX, _SuffixCDT.WEBP_SUFFIX
	);
	public static final List<String> WEB_SUFFIX = List.of(
			_SuffixCDT.HTML_SUFFIX, _SuffixCDT.CSS_SUFFIX, _SuffixCDT.JS_SUFFIX, _SuffixCDT.ICO_SUFFIX, _SuffixCDT.JSON_SUFFIX, _SuffixCDT.MD_SUFFIX, _SuffixCDT.XML_SUFFIX
	);
	public static final String DEFAULT_PAGE = "index.html";
	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String URL_SEPARATOR = "/";

	public static final Map<String, String> CONTENT_TYPE = new HashMap<>();

	static {
		CONTENT_TYPE.put(_SuffixCDT.HTML_SUFFIX, "text/html; charset=utf-8");
		CONTENT_TYPE.put(_SuffixCDT.JS_SUFFIX, "application/javascript; charset=utf-8");
		CONTENT_TYPE.put(_SuffixCDT.CSS_SUFFIX, "text/css; charset=utf-8");
		CONTENT_TYPE.put(_SuffixCDT.JSON_SUFFIX, "application/json; charset=utf-8");
		CONTENT_TYPE.put(_SuffixCDT.PNG_SUFFIX, "image/png");
		CONTENT_TYPE.put(_SuffixCDT.JPG_SUFFIX, "image/jpeg");
		CONTENT_TYPE.put(_SuffixCDT.JPEG_SUFFIX, "image/jpeg");
		CONTENT_TYPE.put(_SuffixCDT.GIF_SUFFIX, "image/gif");
	}


	public static String getContentType(String suffix) {
		return CONTENT_TYPE.getOrDefault(suffix, "application/octet-stream");
	}
}
