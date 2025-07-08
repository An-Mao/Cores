package dev.anye.mc.cores.item;

import dev.anye.mc.cores.attribute.AttributeHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.Objects;

public class ItemHelper {
    public static Item getItem(String id){
        if (Objects.equals(id, "")){
            return null;
        }
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
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
        Collection<AttributeModifier> atk = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
        return AttributeHelper.getAttributeModifierValue(atk);
    }

    public static boolean hasEnchant(ItemStack itemStack, Enchantment enchantment){
        return itemStack.getEnchantmentLevel(enchantment) > 0;
    }
}
