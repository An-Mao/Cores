package dev.anye.mc.cores.time;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class TimeHelper {
	public static final int MinecraftDayMinTick = 0;
	public static final int MinecraftDayMaxTick = 24000;
    public static int TickToDay(int gameTick){
        return gameTick / MinecraftDayMaxTick;
    }

    public static int GetDayTime(int time){
        int currentTimeOfDay = time % 24000;
        currentTimeOfDay = (currentTimeOfDay + 24000) % 24000;
        return currentTimeOfDay;
    }
    public static String FormatDate(int tick){
        int h = tick / 1000;
        int m = (tick - (h * 1000)) % 60;
        h += 6;
        if (h >= 24){
            h -= 24;
        }
        return String.format("%02d:%02d", h, m);
    }
    public static String tickToTime(int tick){
        return String.valueOf(tick / 1200);
    }

    public static long getOverWorldTime(MinecraftServer server){
        return server.overworld().getGameTime();
    }
    public static long getOverWorldTime(ServerPlayer serverPlayer){
        return getOverWorldTime(serverPlayer.server);
    }
    public static long getTimeIntervals(long time1 ,long time2){
        return time1 - time2;
    }
}
