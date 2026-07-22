package dev.anye.mc.cores.js;

import java.io.Reader;

@Deprecated(since = "2.0.5")
public interface _IJS {
	Object runCode(String code);

	Object runFile(String file);

	Object runFile(Reader file);
}
