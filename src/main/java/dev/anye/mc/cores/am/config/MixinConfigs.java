package dev.anye.mc.cores.am.config;

import dev.anye.mc.cores.am.config.mixin.MixinConfig;

public class MixinConfigs{
    public static final MixinConfig I = new MixinConfig();
    public static boolean EnableFixLevel;
    public static boolean EnableFixAttributes;
    static {
        init();
    }
    public static void init(){
        EnableFixLevel = I.isEnable("PlayerLevel");
        EnableFixAttributes = I.isEnable("Attributes");

    }
}
