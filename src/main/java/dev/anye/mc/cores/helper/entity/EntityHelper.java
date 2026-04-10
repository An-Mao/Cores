package dev.anye.mc.cores.helper.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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


    public static boolean isServerLevel(Entity entity){
        return !getLevel(entity).isClientSide();
    }
    public static Set<String> getLivingEntityTag(LivingEntity entity){
        return entity.entityTags();
    }

    public static EntityType<?> getEntityType(String id){
        //return ForgeRegistries.ENTITY_TYPES.getValue(new net.minecraft.resources.ResourceLocation(registryId));
        return  BuiltInRegistries.ENTITY_TYPE.get(Identifier.parse(id)).get().value();
    }
    public static List<? extends LivingEntity> getLivingEntities(LivingEntity livingEntity){
        return getLivingEntities(livingEntity,10);
    }
    public static List<? extends LivingEntity> getLivingEntities(LivingEntity livingEntity, int radius){
        return getLevel(livingEntity).getEntities(EntityTypeTest.forClass(livingEntity.getClass()), livingEntity.getBoundingBox().inflate(radius), Entity::isAlive);
    }

    @Nullable
    public static Entity getViewEntity(Player player,double maxDistance){
        HitResult result = ProjectileUtil.getHitResultOnViewVector(player, entity -> true ,maxDistance);
        if (result instanceof EntityHitResult entityHitResult) {
            return entityHitResult.getEntity();
        }
        return null;
    }
    @Nullable
    public static LivingEntity getViewLivingEntity(Player player,double maxDistance){
        HitResult result = ProjectileUtil.getHitResultOnViewVector(player, entity -> entity instanceof LivingEntity ,maxDistance);
        if (result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
            return livingEntity;
        }
        return null;
    }
    public static List<Player> getPlayers(Level level, Entity entity,double distance){
        List<Player> players = new ArrayList<>();
        level.players().forEach(player -> {
            if (player.distanceTo(entity) <= distance) players.add(player);
        });
        return players;
    }
}
