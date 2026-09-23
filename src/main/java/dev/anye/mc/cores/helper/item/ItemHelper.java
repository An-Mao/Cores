package dev.anye.mc.cores.helper.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.Collection;
import java.util.function.Consumer;

public final class ItemHelper {
	private ItemHelper(){}

	public static double getMainItemDamage(LivingEntity livingEntity) {
		return getItemDamage(livingEntity.getMainHandItem());
	}

	public static double getItemDamage(String id) {
		Item item = getItem(id);
		if (item == null) {
			return 0;
		}
		return getItemDamage(new ItemStack(item));
	}

	public static double getItemDamage(ItemStack itemStack) {
		Collection<AttributeModifier> atk = getAttributeModifiers(itemStack, EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
		return AttributeHelper.getAttributeModifierValue(atk);
	}

	public static Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(ItemStack itemStack, EquipmentSlot pSlot) {
		Multimap<Holder<Attribute>, AttributeModifier> multimap;
		multimap = HashMultimap.create();
		if (itemStack.has(DataComponents.ATTRIBUTE_MODIFIERS)) {
			ItemAttributeModifiers data = itemStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
			if (data != null) {
				data.modifiers().forEach(entry -> {
					if (entry.slot().test(pSlot)) {
						multimap.put(entry.attribute(), entry.modifier());
					}
				});
			}
		}
		return multimap;
	}

	public static boolean hasEnchant(ItemStack itemStack, Enchantment enchantment) {
		return itemStack.getEnchantmentLevel(Holder.direct(enchantment)) > 0;
	}


	public static Item getItem(String name) {
		if (name == null || name.isBlank()) return Items.AIR;
		return BuiltInRegistries.ITEM.get(Identifier.parse(name)).map(Holder.Reference::value).orElse(Items.AIR);
	}

	public static Identifier getKey(ItemStack item){
		return getKey(item.getItem());
	}

	public static Identifier getKey(Item item){
		return BuiltInRegistries.ITEM.getKey(item);
	}
	public static String getStringKey(ItemStack item){
		return getStringKey(item.getItem());
	}

	public static String getStringKey(Item item){
		return getKey(item).toString();
	}


	public static JsonElement itemToJson(ItemStack item, HolderLookup.Provider lookupProvider){
		return ItemStack.CODEC.encodeStart(RegistryOps.create(JsonOps.INSTANCE, lookupProvider), item).getOrThrow();
	}

	public static ItemStack jsonToItem(JsonElement json, HolderLookup.Provider lookupProvider){
		return ItemStack.CODEC.parse(RegistryOps.create(JsonOps.INSTANCE, lookupProvider),json).getOrThrow();
	}

	public static ItemResource resource(Item item, Consumer<ItemStack> consumer){
		ItemStack stack = new ItemStack(item);
		consumer.accept(stack);
		return ItemResource.of(stack);
	}
}
