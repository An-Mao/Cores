package dev.anye.mc.cores;

import dev.anye.core.system._File;

public class CDT {
    public static final String ConfigDir = _File.getFileFullPathWithRun("config/cores/");
    public static final int MinecraftDayMinTick = 0;
    public static final int MinecraftDayMaxTick = 24000;
    static {
        _File.checkAndCreateDir(CDT.ConfigDir);
    }
}
