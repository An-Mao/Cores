package dev.anye.mc.cores.am.config;

import dev.anye.mc.cores.am.config.attribute.AttributeConfig;
import dev.anye.mc.cores.am.config.general.GeneralConfig;

public class Configs {
	public static final GeneralConfig general;
	public static final AttributeConfig attribute;

	static {
		general = new GeneralConfig();
		attribute = new AttributeConfig();
	}

	private Configs() {
	}
}
