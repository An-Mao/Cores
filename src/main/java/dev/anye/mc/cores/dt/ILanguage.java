package dev.anye.mc.cores.dt;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public interface ILanguage {
	Component lang(ServerPlayer serverPlayer, String key);
}
