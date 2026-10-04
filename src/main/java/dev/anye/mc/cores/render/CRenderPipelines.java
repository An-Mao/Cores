package dev.anye.mc.cores.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.pipeline.*;
import net.minecraft.client.renderer.BindGroupLayouts;

import java.util.Optional;

public class CRenderPipelines {
	private CRenderPipelines() {
	}

	public static final BindGroupLayout SPHERE_GL = BindGroupLayout.builder()
			.withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
			.withUniform("Projection", UniformType.UNIFORM_BUFFER)
			.build();

	public static final RenderPipeline.Snippet SPHERE_SNIPPET = RenderPipeline.builder()
			.withBindGroupLayout(SPHERE_GL)
			//.withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
			//.withBindGroupLayout(BindGroupLayouts.PROJECTION)
			//.withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
			//.withUniform("Projection", UniformType.UNIFORM_BUFFER)
			.withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
			//.withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES)
			.withCull(false)
			.withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
			.withColorTargetState(new ColorTargetState(Optional.of(BlendFunction.TRANSLUCENT), GpuFormat.RGBA8_UNORM,ColorTargetState.WRITE_RED | ColorTargetState.WRITE_GREEN | ColorTargetState.WRITE_BLUE | ColorTargetState.WRITE_ALPHA))
			.withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false))//new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, 0f, 0f)
			.buildSnippet();
	public static final RenderPipeline SPHERE = RenderPipeline.builder(SPHERE_SNIPPET)
			.withLocation("pipeline/sphere")
			.withVertexShader("core/position_color")
			.withFragmentShader("core/position_color")
			.build();

}
