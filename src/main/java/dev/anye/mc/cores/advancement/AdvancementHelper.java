package dev.anye.mc.cores.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;

public class AdvancementHelper {
    public static boolean isSpecifyAdvancement(Advancement advancement, String advancementId) {
        ResourceLocation vanillaAdvancementId = new ResourceLocation(advancementId);
        ResourceLocation advancementLocation = advancement.getId();
        return advancementLocation.equals(vanillaAdvancementId);
    }
}
