package dev.anye.mc.cores.helper.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.Identifier;

public class AdvancementHelper {
	public static boolean isSpecifyAdvancement(Advancement advancement, String advancementId) {
		Identifier vanillaAdvancementId = Identifier.tryParse(advancementId);
		Identifier advancementLocation = advancement.parent().get();
		return advancementLocation.equals(vanillaAdvancementId);
	}
}
