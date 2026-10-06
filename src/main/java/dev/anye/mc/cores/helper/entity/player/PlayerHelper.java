package dev.anye.mc.cores.helper.entity.player;

import dev.anye.mc.cores.helper.entity.EntityHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PlayerHelper {
    public static final int SLOT_CHEST = 102;


    public static int getExperienceForLevel(int targetLevel) {
        int totalExperience = 0;
        for (int level = 0; level < targetLevel; level++) {
            if (level >= 30) {
                totalExperience += 112 + (level - 30) * 9;
            } else if (level >= 15) {
                totalExperience += 37 + (level - 15) * 5;
            } else {
                totalExperience += 7 + level * 2;
            }
        }
        return totalExperience;
    }
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
