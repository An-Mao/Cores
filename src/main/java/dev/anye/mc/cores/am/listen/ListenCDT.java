package dev.anye.mc.cores.am.listen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.anye.core.cdt._SuffixCDT;

import java.util.List;

public class ListenCDT {
	public static final List<String> IMAGE_SUFFIX = List.of(
			_SuffixCDT.PNG_SUFFIX,_SuffixCDT.JPG_SUFFIX,_SuffixCDT.JPEG_SUFFIX,_SuffixCDT.GIF_SUFFIX,_SuffixCDT.WEBP_SUFFIX
	);
	public static final List<String> WEB_SUFFIX = List.of(
			_SuffixCDT.HTML_SUFFIX,_SuffixCDT.CSS_SUFFIX,_SuffixCDT.JS_SUFFIX,_SuffixCDT.ICO_SUFFIX,_SuffixCDT.JSON_SUFFIX,_SuffixCDT.MD_SUFFIX,_SuffixCDT.XML_SUFFIX
	);
	public static final String DEFAULT_PAGE = "index.html";
	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String URL_SEPARATOR = "/";
}
