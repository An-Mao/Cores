package dev.anye.mc.cores.server;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ServerSupports {
    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
    public static ServerLevel getLevel(ResourceKey<Level> pDimension) {
        return getServer().getLevel(pDimension);
    }
    public static ServerLevel getOverworldLevel() {
        return getLevel(ServerLevel.OVERWORLD);
    }
    public static ServerLevel getNetherLevel() {
        return getLevel(ServerLevel.NETHER);
    }
    public static ServerLevel getEndLevel() {
        return getLevel(ServerLevel.END);
    }
}
