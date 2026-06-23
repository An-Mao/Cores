package dev.anye.mc.cores.helper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.ClientHooks;

public class ClientEntityHelper {
	public static boolean checkView(Minecraft minecraft, LivingEntity entity) {
		if (minecraft.player != null) {
			return ClientHooks.isNameplateInRenderDistance(entity, minecraft.getEntityRenderDispatcher().distanceToSqr(entity)) && minecraft.player.hasLineOfSight(entity) && entity == minecraft.getEntityRenderDispatcher().crosshairPickEntity;
		}
		return false;
	}
}
