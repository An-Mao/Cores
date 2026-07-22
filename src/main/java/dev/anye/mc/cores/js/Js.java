package dev.anye.mc.cores.js;

import net.neoforged.fml.ModList;
import dev.anye.core.javascript._GraalJS;
import dev.anye.core.javascript._JavaScript;
import dev.anye.core.javascript._NashornJS;

public class Js {
	public static final boolean GraalJs = GraalJsIsInstall();

	public static boolean GraalJsIsInstall() {
		return ModList.get().isLoaded("graaljs");
	}

	public static _JavaScript<?,?> getJsEngine(boolean cache) {
		return GraalJs ? new _GraalJS(cache) : new _NashornJS(cache);
	}

}
