package dev.anye.mc.cores.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.anye.mc.cores.Cores;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

import java.util.Optional;

@EventBusSubscriber(modid = Cores.MOD_ID, value = Dist.CLIENT)
public class CRenderPipelines {
	public static final RenderPipeline.Snippet SPHERE_SNIPPET = RenderPipeline.builder()
			.withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
			.withUniform("Projection", UniformType.UNIFORM_BUFFER)
			.withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES)
			.withCull(false)
			.withColorTargetState(new ColorTargetState(
					Optional.of(BlendFunction.TRANSLUCENT),
					ColorTargetState.WRITE_RED | ColorTargetState.WRITE_GREEN | ColorTargetState.WRITE_BLUE | ColorTargetState.WRITE_ALPHA))
			.withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false))//new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, 0f, 0f)
			.buildSnippet();
	public static final RenderPipeline SPHERE = RenderPipeline.builder(SPHERE_SNIPPET)
			.withLocation("pipeline/sphere")
			.withVertexShader("core/position_color")
			.withFragmentShader("core/position_color")
			.build();

	@SubscribeEvent
	public static void onPipelineRegister(RegisterRenderPipelinesEvent event) {
		event.registerPipeline(SPHERE);
	}
}
