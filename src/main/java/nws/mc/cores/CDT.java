package nws.mc.cores;

import nws.dev.core.system._File;

public class CDT {
    public static final String ConfigDir = _File.getFileFullPathWithRun("config/"+Cores.MOD_ID+"/");
    public static final int MinecraftDayMinTick = 0;
    public static final int MinecraftDayMaxTick = 24000;









    static {
        _File.checkAndCreateDir(CDT.ConfigDir);
    }
}