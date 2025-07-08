package dev.anye.mc.cores.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EntityHelper {
    public static Level getLevelCanNull(Entity entity){
        return entity.level();
    }
    public static @NotNull Level getLevel(Entity entity){
        return entity.level();
    }
    public static long getLevelTime(Entity entity){
        return getLevel(entity).getGameTime();
    }
    public static ServerLevel getServerLevel(LivingEntity pLivingEntity){
        return (ServerLevel) pLivingEntity.level();
    }
    public static BlockEntity getBlockEntity(Level pLevel, BlockPos pPos){
        return pLevel.getBlockEntity(pPos);
    }
    public static BlockEntity getBlockEntity(LivingEntity pLivingEntity, BlockPos pPos){
        return getLevel(pLivingEntity).getBlockEntity(pPos);
    }
    public static boolean isPlayer(LivingEntity livingEntity){
        return livingEntity instanceof Player;
    }
    public static boolean isNpc(LivingEntity livingEntity){
        return livingEntity instanceof Npc;
    }
    public static boolean isAnimal(LivingEntity livingEntity){
        return livingEntity instanceof Animal;
    }
    public static boolean isPlayerOrNpcOrAnimal(LivingEntity livingEntity){
        return isPlayer(livingEntity) || isNpc(livingEntity) || isAnimal(livingEntity);
    }
    public static List<Entity> getRadiusEntities(Entity entity, double radius){
        return getLevel(entity).getEntities(null,entity.getBoundingBox().inflate(radius));
    }
    public static List<Entity> getRadiusEntities(Level level, AABB aabb, double radius){
        return level.getEntities(null,aabb.inflate(radius));
    }
}
