package dev.anye.mc.cores;

import com.google.common.reflect.TypeToken;
import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.am.color.ColorConfig;
import dev.anye.mc.cores.am.color.ColorSchemeRegister;
import dev.anye.mc.cores.am.color.ColorSchemes;
import dev.anye.mc.cores.am.util.KeyBinding;
import dev.anye.mc.cores.cr.CoresRegs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;


@Mod(value = Cores.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Cores.MOD_ID, value = Dist.CLIENT)
public class CoresClient {
	public CoresClient(ModContainer container) {
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		String scheme = ColorConfig.instance.getDatas().getColorScheme();
		Identifier colorSchemeRes = Identifier.tryParse(scheme);
		if (colorSchemeRes != null) {
			ColorSchemeRegister.COLOR_SCHEME_REGISTER.getRegistry().get(colorSchemeRes).ifPresent(cs ->{
				ColorSchemes.setGlobal(cs.value());
			});
		}
	}

	@SubscribeEvent
	public static void onKeyRegister(RegisterKeyMappingsEvent event) {
		event.register(KeyBinding.OPEN_MENU);
	}

	public static final ContextKey<Entity> RenderStateEntityKey = new ContextKey<>(Identifier.fromNamespaceAndPath(Cores.MOD_ID, "entity"));

	@SubscribeEvent
	public static void onRegRenderState(RegisterRenderStateModifiersEvent event) {
		event.registerEntityModifier(new TypeToken<>() {
		}, (entity, renderState) -> renderState.setRenderData(RenderStateEntityKey, entity));
	}

	@SubscribeEvent
	public static void onRender(RenderLivingEvent.Post<LivingEntity, ?, ?> event) {
		if (event.getRenderState().getRenderData(RenderStateEntityKey) instanceof LivingEntity livingEntity) {
			CoresRegs.ENTITY_RENDER_REG.forEach((s, iEntityRender) -> iEntityRender.render(livingEntity,
					event.getRenderState(), event.getRenderer(), event.getSubmitNodeCollector(), event.getPoseStack()));
            /*
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            poseStack.translate(0, livingEntity.getBbHeight() / 2.0F, 0);
            float radius = Math.max(livingEntity.getBbWidth(), livingEntity.getBbHeight())  * 0.7F;
            int   color  = 0x5500FFFF;
            event.getSubmitNodeCollector().submitCustomGeometry(poseStack, CRenderTypes.SPHERE, (pose, v) -> {
                new SphereRenderState(pose,  radius, 32, 16, color).render(v);
            });

            poseStack.popPose();

             */
		}
	}


}
