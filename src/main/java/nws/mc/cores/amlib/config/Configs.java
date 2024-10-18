package nws.mc.cores.amlib.config;

import nws.mc.cores.amlib.config.attribute.AttributeConfig;
import nws.mc.cores.amlib.config.general.GeneralConfig;
import nws.mc.cores.amlib.config.the_world.TheWorldConfig;

public class Configs {
    public static final GeneralConfig general;
    public static final TheWorldConfig theWorld;
    public static final AttributeConfig attribute;
    static {
        general = new GeneralConfig();
        theWorld = new TheWorldConfig();
        attribute = new AttributeConfig();
    }
}
