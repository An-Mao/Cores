package dev.anye.mc.cores.helper.server;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ServerSupports {
    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
    public static Level getLevel(ResourceKey<Level> pDimension) {
        if (getServer() == null) return Minecraft.getInstance().level;
        return getServer().getLevel(pDimension);
    }
    public static Level getOverworldLevel() {
        return getLevel(ServerLevel.OVERWORLD);
    }
    public static Level getNetherLevel() {
        return getLevel(ServerLevel.NETHER);
    }
    public static Level getEndLevel() {
        return getLevel(ServerLevel.END);
    }
}
