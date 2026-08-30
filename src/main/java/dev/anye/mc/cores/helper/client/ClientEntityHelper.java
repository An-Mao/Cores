package dev.anye.mc.cores.helper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ClientEntityHelper {
	public static boolean checkView(Minecraft minecraft, LivingEntity entity) {
		if (minecraft.player != null) {
			return isNameplateInRenderDistance(entity, minecraft.getEntityRenderDispatcher().distanceToSqr(entity)) && minecraft.player.hasLineOfSight(entity) && entity == minecraft.getEntityRenderDispatcher().crosshairPickEntity;
		}
		return false;
	}

	public static boolean isNameplateInRenderDistance(LivingEntity entity, double squareDistance) {
		double value = entity.getAttributeValue(Attributes.NAME_TAG_DISTANCE);
		return squareDistance <= value * value;
	}
}
