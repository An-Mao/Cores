package dev.anye.mc.cores.entity.player;

import dev.anye.mc.cores.entity.EntityHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PlayerHelper {
    public static int getLevelFromExperience(int experience) {
        int level = 0;
        int exp = 7;

        while (experience >= exp) {
            level++;
            experience -= exp;
            exp += 2 + level * 3;

            if (level >= 30) {
                exp += 2 + level * 7;
            }
        }

        return level;
    }
    public static Player getNearPlayer(Entity entity, double radius){
        return EntityHelper.getLevel(entity).getNearestPlayer(entity,radius);
    }
}
