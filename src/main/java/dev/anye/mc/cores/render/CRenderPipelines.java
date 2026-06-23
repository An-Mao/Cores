package dev.anye.mc.cores.render;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.*;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.BindGroupLayouts;

public class CRenderPipelines {
	private CRenderPipelines() {}

	public static final RenderPipeline.Snippet SPHERE_SNIPPET = RenderPipeline.builder()
			.withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION)
			//.withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
			//.withUniform("Projection", UniformType.UNIFORM_BUFFER)
			.withVertexBinding(0,DefaultVertexFormat.POSITION_COLOR)
			//.withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES)
			.withCull(false)
			.withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
			.withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
			.withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false))//new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, 0f, 0f)
			.buildSnippet();
	public static final RenderPipeline SPHERE = RenderPipeline.builder(SPHERE_SNIPPET)
			.withLocation("pipeline/sphere")
			.withVertexShader("core/position_color")
			.withFragmentShader("core/position_color")
			.build();

}
