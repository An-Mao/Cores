package nws.mc.cores.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;

public class AdvancementHelper {
    public static boolean isSpecifyAdvancement(Advancement advancement, String advancementId) {
        ResourceLocation vanillaAdvancementId = ResourceLocation.tryParse(advancementId);
        ResourceLocation advancementLocation = advancement.parent().get();
        return advancementLocation.equals(vanillaAdvancementId);
    }
}
