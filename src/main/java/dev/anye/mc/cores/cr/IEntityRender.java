package dev.anye.mc.cores.cr;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

public interface IEntityRender {
	void render(LivingEntity entity, LivingEntityRenderState renderState, LivingEntityRenderer<LivingEntity, ?, ?> renderer, SubmitNodeCollector submitNodeCollector, PoseStack poseStack);
}
