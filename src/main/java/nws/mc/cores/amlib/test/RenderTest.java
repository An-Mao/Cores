package nws.mc.cores.amlib.test;

import net.minecraftforge.fml.common.Mod;
import nws.mc.cores.Cores;

@Mod.EventBusSubscriber(modid = Cores.MOD_ID)
public class RenderTest {
    /*
    public static void onHurt(LivingHurtEvent event){
        if (event.getEntity() instanceof ServerPlayer serverPlayer){
            List<String> list  = new ArrayList<>();
            serverPlayer.getMainHandItem().getEnchantments().keySet().forEach(enchantmentHolder -> list.add(enchantmentHolder.getRegisteredName()));
            Registry<Enchantment> reg = serverPlayer.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
            list.forEach(s -> {
                System.out.println(s);
                Optional<Holder.Reference<Enchantment>> enchantment = reg.getHolder(ResourceLocation.parse(s));
                enchantment.ifPresent(enchantmentReference -> System.out.println(enchantmentReference.get().getMaxLevel()));
            });
        }
    }

     */
}
