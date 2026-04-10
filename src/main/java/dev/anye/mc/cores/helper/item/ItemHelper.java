package dev.anye.mc.cores.helper.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;
import java.util.Objects;

public class ItemHelper {
    public static Item getItem(String id){
        if (Objects.equals(id, "")){
            return null;
        }
            return BuiltInRegistries.ITEM.get(Identifier.tryParse(id)).get().value();
    }
    public static double getMainItemDamage(LivingEntity livingEntity){
        return getItemDamage(livingEntity.getMainHandItem());
    }
    public static double getItemDamage(String id){
        Item item = getItem(id);
        if (item == null){
            return 0;
        }
        return getItemDamage(new ItemStack(item));
    }
    public static double getItemDamage(ItemStack itemStack){
        Collection<AttributeModifier> atk = getAttributeModifiers(itemStack,EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
        return AttributeHelper.getAttributeModifierValue(atk);
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(ItemStack itemStack,EquipmentSlot pSlot) {
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
    public static boolean hasEnchant(ItemStack itemStack, Enchantment enchantment){
        return itemStack.getEnchantmentLevel(Holder.direct(enchantment)) > 0;
    }
}
