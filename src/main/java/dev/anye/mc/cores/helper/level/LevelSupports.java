package dev.anye.mc.cores.helper.level;

import dev.anye.mc.cores.helper.component.ComponentHelp;
import dev.anye.mc.cores.helper.server.ServerSupports;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LevelSupports {
    @Nullable
    public static Level getLevel(ResourceKey<Level> pDimension) {
        return ServerSupports.getLevel(ServerLevel.OVERWORLD);
    }

    public static void sendMsg(Level level,String key,Object... data){
        if (level.getServer() != null) {
            List<ServerPlayer> players = level.getServer().getPlayerList().getPlayers();
            for (ServerPlayer player:players) {
                String s = Component.translatable(key).getString();
                ComponentHelp.sendFormatMsg(player,s,"/n",data);
            }
        }
    }

}
