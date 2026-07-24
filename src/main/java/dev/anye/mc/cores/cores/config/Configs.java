package dev.anye.mc.cores.cores.config;

import dev.anye.mc.cores.cores.config.attribute.AttributeConfig;
import dev.anye.mc.cores.cores.config.general.GeneralConfig;
import dev.anye.mc.cores.cores.config.the_world.TheWorldConfig;

public class Configs {
    public static final GeneralConfig GENERAL;
    public static final TheWorldConfig THE_WORLD;
    public static final AttributeConfig ATTRIBUTE;
    static {
        GENERAL = new GeneralConfig();
        THE_WORLD = new TheWorldConfig();
        ATTRIBUTE = new AttributeConfig();
    }
	private Configs(){}
}
