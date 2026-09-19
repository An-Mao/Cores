package dev.anye.mc.cores.helper.entity;

import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class EntityHelper {
	private static final Logger LOGGER = LogUtils.getLogger();
	private EntityHelper(){}

	public static Level getLevelCanNull(Entity entity) {
		return entity.level();
	}

	public static @NotNull Level getLevel(Entity entity) {
		return entity.level();
	}

	public static long getLevelTime(Entity entity) {
		return getLevel(entity).getGameTime();
	}

	public static ServerLevel getServerLevel(LivingEntity pLivingEntity) {
		return (ServerLevel) pLivingEntity.level();
	}

	public static BlockEntity getBlockEntity(Level pLevel, BlockPos pPos) {
		return pLevel.getBlockEntity(pPos);
	}

	public static BlockEntity getBlockEntity(LivingEntity pLivingEntity, BlockPos pPos) {
		return getLevel(pLivingEntity).getBlockEntity(pPos);
	}

	public static boolean isPlayer(LivingEntity livingEntity) {
		return livingEntity instanceof Player;
	}

	public static boolean isNpc(LivingEntity livingEntity) {
		return livingEntity instanceof Npc;
	}

	public static boolean isAnimal(LivingEntity livingEntity) {
		return livingEntity instanceof Animal;
	}

	public static boolean isPlayerOrNpcOrAnimal(LivingEntity livingEntity) {
		return isPlayer(livingEntity) || isNpc(livingEntity) || isAnimal(livingEntity);
	}

	public static List<Entity> getRadiusEntities(Entity entity, double radius) {
		return getLevel(entity).getEntities(null, entity.getBoundingBox().inflate(radius));
	}

	public static List<Entity> getRadiusEntities(Level level, AABB aabb, double radius) {
		return level.getEntities(null, aabb.inflate(radius));
	}


	public static boolean isServerLevel(Entity entity) {
		return !getLevel(entity).isClientSide();
	}

	public static Set<String> getLivingEntityTag(LivingEntity entity) {
		return entity.entityTags();
	}

	public static @Nullable EntityType<?> getEntityType(String id) {
		return getEntityType(Identifier.parse(id));
	}
	public static @Nullable EntityType<?> getEntityType(Identifier id) {
		return BuiltInRegistries.ENTITY_TYPE.get(id).map(Holder.Reference::value).orElse(null);
	}

	public static List<? extends LivingEntity> getLivingEntities(LivingEntity livingEntity) {
		return getLivingEntities(livingEntity, 10);
	}

	public static List<? extends LivingEntity> getLivingEntities(LivingEntity livingEntity, int radius) {
		return getLevel(livingEntity).getEntities(EntityTypeTest.forClass(livingEntity.getClass()), livingEntity.getBoundingBox().inflate(radius), Entity::isAlive);
	}

	@Nullable
	public static Entity getViewEntity(Player player, double maxDistance) {
		HitResult result = ProjectileUtil.getHitResultOnViewVector(player, _ -> true, maxDistance);
		if (result instanceof EntityHitResult entityHitResult) {
			return entityHitResult.getEntity();
		}
		return null;
	}

	@Nullable
	public static LivingEntity getViewLivingEntity(Player player, double maxDistance) {
		HitResult result = ProjectileUtil.getHitResultOnViewVector(player, LivingEntity.class::isInstance, maxDistance);
		if (result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
			return livingEntity;
		}
		return null;
	}

	public static List<Player> getPlayers(Level level, Entity entity, double distance) {
		List<Player> players = new ArrayList<>();
		level.players().forEach(player -> {
			if (player.distanceTo(entity) <= distance) players.add(player);
		});
		return players;
	}

	public static String getEntityRegStringID(Entity entity) {
		return getEntityRegID(entity).toString();
	}
	public static Identifier getEntityRegID(Entity entity) {
		return BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
	}
	public static String getEntityRegStringIDWith(Entity entity,String c) {
		Identifier identifier = getEntityRegID(entity);
		return identifier.getNamespace() + c + identifier.getPath();
	}
	public static String getEntityRegStringIDWithX(Entity entity,String c) {
		Identifier identifier = getEntityRegID(entity);
		return identifier.getPath() + c + identifier.getNamespace();
	}


	/**
	 * 将实体序列化为JSON
	 * @param entity 要进行序列化的实体
	 * @return JSON
	 */
	public static JsonElement toJson(Entity entity) {
		try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(entity.problemPath(), LOGGER)) {
			TagValueOutput entityData = TagValueOutput.createWithContext(reporter, entity.registryAccess());
			entity.saveWithoutId(entityData);
			return CompoundTag.CODEC.encodeStart(JsonOps.INSTANCE, entityData.buildResult()).getOrThrow();
		}
	}

	/**
	 * 将JSON反序列化为实体实例
	 * @param serverLevel level
	 * @param eid 实体注册ID
	 * @param json 实体数据
	 * @return 成功返回实体实例，失败返回null
	 */
	public static Entity jsonToEntity(ServerLevel serverLevel, String eid, JsonElement json){
		return jsonToEntity(serverLevel,Identifier.parse(eid),json);
	}

	/**
	 * 将JSON反序列化为实体实例
	 * @param serverLevel level
	 * @param eid 实体注册ID
	 * @param json 实体数据
	 * @return 成功返回实体实例，失败返回null
	 */
	public static Entity jsonToEntity(ServerLevel serverLevel, Identifier eid,JsonElement json){
		Entity entity = BuiltInRegistries.ENTITY_TYPE.getValue(eid).create(serverLevel, EntitySpawnReason.COMMAND);
		if (entity != null) {
			try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(entity.problemPath(), LOGGER)) {
				CompoundTag tag = CompoundTag.CODEC.parse(JsonOps.INSTANCE,json).getOrThrow();
				entity.load(TagValueInput.create(reporter, serverLevel.registryAccess(), tag));
				return entity;
			}
		}
		return null;
	}

	public static ItemStack getSpawnEgg(EntityType<?> entityType, ItemStack or){
		return SpawnEggItem.byId(entityType).map(ItemStack::new).orElse(or);
	}

	public static List<Entity> getPlayerLeash(ServerPlayer serverPlayer){
		return getPlayerLeash(serverPlayer,32);
	}
	public static List<Entity> getPlayerLeash(ServerPlayer serverPlayer,int size){
		return serverPlayer.level().getEntitiesOfClass(Entity.class, AABB.ofSize(serverPlayer.getBoundingBox().getCenter(), size, size, size), entity -> entity instanceof Leashable leashable && leashable.getLeashHolder() == serverPlayer);
	}
}
