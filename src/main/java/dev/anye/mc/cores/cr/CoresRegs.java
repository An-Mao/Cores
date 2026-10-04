package dev.anye.mc.cores.cr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import dev.anye.mc.cores.render.CRenderTypes;
import dev.anye.mc.cores.render.element.SphereRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public final class CoresRegs {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final String Pack = "dev.anye.mc.register";
	public static final Map<String, IEntityRender> ENTITY_RENDER_REG = new HashMap<>();
	private CoresRegs(){}

	public static void render(LivingEntity livingEntity, LivingEntityRenderState renderState, LivingEntityRenderer<LivingEntity,?,?> renderer, SubmitNodeCollector submitNodeCollector, PoseStack poseStack){
			poseStack.pushPose();
			poseStack.translate(0, livingEntity.getBbHeight() / 2.0F, 0);
			float radius = Math.max(livingEntity.getBbWidth(), livingEntity.getBbHeight()) / 2.0F * 1.2F;
			int   color  = 0x66006699;
			submitNodeCollector.submitCustomGeometry(poseStack, CRenderTypes.SPHERE, (pose, v) -> {
				new SphereRenderState(pose,  radius, 32, 16, color).render(v);
			});

			poseStack.popPose();


	}
}
