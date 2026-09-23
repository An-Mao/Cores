package dev.anye.mc.cores.helper.time;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.timeline.Timelines;

public class TimeHelper {
	public static final long MinecraftDayMinTick = 0;
	public static final long MinecraftDayMaxTick = 24000;

	public static long tickToDay(long gameTick) {
		return gameTick / MinecraftDayMaxTick;
	}

	public static long getDayTime(long time) {
		long currentTimeOfDay = time % MinecraftDayMaxTick;
		currentTimeOfDay = (currentTimeOfDay + MinecraftDayMaxTick) % MinecraftDayMaxTick;
		return currentTimeOfDay;
	}

	public static String formatDate(long tick) {
		long h = tick / 1000;
		long m = (tick - (h * 1000)) % 60;
		h += 6;
		if (h >= 24) {
			h -= 24;
		}
		return String.format("%02d:%02d", h, m);
	}

	public static String tickToTime(long tick) {
		return String.valueOf(tick / 1200);
	}

	public static long getOverWorldTime(MinecraftServer server) {
		return server.overworld().getGameTime();
	}

	public static long getOverWorldTime(ServerPlayer serverPlayer) {
		return getOverWorldTime(serverPlayer.level().getServer());
	}

	public static long getTimeIntervals(long time1, long time2) {
		return time1 - time2;
	}

	public static long getDayTime(Entity entity) {
		return getDayTime(entity.level());
	}

	public static long getDayTime(Level level) {
		return level.registryAccess().getOrThrow(Timelines.OVERWORLD_DAY).value().getCurrentTicks(level.clockManager());
	}


	public static boolean isDay(long time) {
		return time >= 7200 && time < 22800;
	}

	public static boolean isNight(long time) {
		return time < 7200 || time >= 22800;
	}
}
