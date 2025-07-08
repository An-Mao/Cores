package dev.anye.mc.cores.enchantment;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class EnchantmentHelper {
    public static Component getEnchantmentDescString(Enchantment enchantment){
        String key = getEnchantmentDescKey(enchantment);
        Component component = Component.translatable(key);
        if (component.getString().equals(key)){
            return Component.translatable("error_msg.cores.no_desc");
        }
        return component;
    }
    public static String getEnchantmentDescKey(Enchantment enchantment){
        return enchantment.getDescriptionId()+".desc";
    }
    public static Registry<Enchantment> getEnchantReg(){
        return BuiltInRegistries.ENCHANTMENT;
    }
    public static Optional<Enchantment> getEnchantRegWithOptional(ResourceLocation pRes){
        return getEnchantReg().getOptional(pRes);
    }
    public static Optional<Enchantment> getEnchantRegWithOptional(String id){
        return getEnchantRegWithOptional(ResourceLocation.tryParse(id));
    }
    public static Optional<Enchantment> getEnchantRegWithOptional(CompoundTag pTag){
        return getEnchantRegWithOptional(pTag,"id");
    }
    public static Optional<Enchantment> getEnchantRegWithOptional(CompoundTag pTag,String id){
        return getEnchantRegWithOptional(pTag.getString(id));
    }
    public static IForgeRegistry<Enchantment> getEnchantmentRegister(){
        return ForgeRegistries.ENCHANTMENTS;
    }
    public static Enchantment getEnchantment(String nameSpace,String id){
        return getEnchantment(new ResourceLocation(nameSpace,id));
    }
    public static Enchantment getEnchantment(ResourceLocation res){
        return getEnchantmentRegister().getValue(res);
    }
    public static @NotNull Set<ResourceLocation> getAllEnchantmentKeys(){
        return getEnchantmentRegister().getKeys();
    }
    public static @NotNull Collection<Enchantment> getAllEnchantments(){
        return getEnchantmentRegister().getValues();
    }
    public static @Nullable ResourceLocation getEnchantmentKey(Enchantment enchantment){
        return getEnchantmentRegister().getKey(enchantment);
    }
    public static ResourceLocation getRegEnchantId(Enchantment enchantment){
        return net.minecraft.world.item.enchantment.EnchantmentHelper.getEnchantmentId(enchantment);
    }
    public static String getEnchantId(Enchantment enchantment){
        return String.valueOf(getRegEnchantId(enchantment));
    }
    public static CompoundTag EnchantToCompoundTag(Enchantment enchantment,int lvl){
        CompoundTag tag = new CompoundTag();
        tag.putString("id", getEnchantId(enchantment));
        tag.putInt("lvl",lvl);
        return tag;
    }
    public static ListTag EnchantsToListTag(HashMap<Enchantment,Integer> edt){
        ListTag tag = new ListTag();
        edt.forEach((enchantment, integer) -> tag.add(EnchantToCompoundTag(enchantment,integer)));
        return tag;
    }
    public static CompoundTag EnchantsToCompoundTag(HashMap<Enchantment,Integer> edt){
        return EnchantsToCompoundTag(edt,"Enchantments");
    }
    public static CompoundTag EnchantsToCompoundTag(HashMap<Enchantment,Integer> edt,String saveKey){
        CompoundTag tag = new CompoundTag();
        tag.put(saveKey,EnchantsToListTag(edt));
        return tag;
    }
    public static HashMap<Enchantment,Integer> ListTagToEnchants(ListTag tag){
        HashMap<Enchantment,Integer> enchants = new HashMap<>();
        for (int i = 0; i < tag.size(); i++){
            CompoundTag compoundtag = tag.getCompound(i);
            enchants.put(CompoundTagToEnchant(compoundtag), getEnchantmentLevel(compoundtag));
        }
        return enchants;
    }
    public static HashMap<Enchantment,Integer> CompoundTagToEnchants(CompoundTag tag){
        return CompoundTagToEnchants(tag,"Enchantments");
    }
    public static HashMap<Enchantment,Integer> CompoundTagToEnchants(CompoundTag tag,String saveKey){
        return ListTagToEnchants(tag.getList(saveKey, Tag.TAG_COMPOUND));
    }
    public static Enchantment CompoundTagToEnchant(CompoundTag tag){
        return IdToEnchant(tag.getString("id"));
    }
    public static Enchantment IdToEnchant(String s){
        return getEnchantRegWithOptional(s).orElseThrow(() -> new RuntimeException("Enchantment not found for ID: " + s));
    }
    public static Enchantment VanillaCompoundTagToEnchant(CompoundTag pCompoundTag){
        AtomicReference<Enchantment> enchant = null;
        getEnchantRegWithOptional(pCompoundTag).ifPresent((enchantment) -> enchant.set(enchantment));
        return enchant.get();
    }
    public static int getEnchantmentLevel(CompoundTag pCompoundTag) {
        return Math.max(0,pCompoundTag.getInt("lvl"));
    }
    public static ListTag getEnchantBookEnchants(ItemStack pItem){
        CompoundTag lEnchantBook = pItem.getTag();
        if (lEnchantBook != null) {
            return lEnchantBook.getList("StoredEnchantments", Tag.TAG_COMPOUND);
        }
        return null;
    }
    public static boolean isEnchantItem(ItemStack pItem){
        if (pItem == ItemStack.EMPTY){
            return false;
        }
        if (pItem.getItem() == Items.ENCHANTED_BOOK){
            return true;
        }
        return !pItem.getEnchantmentTags().isEmpty();
    }
}
