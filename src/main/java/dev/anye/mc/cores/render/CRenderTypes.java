package dev.anye.mc.cores.render;

import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;

public class CRenderTypes {
	public static final RenderType SPHERE = RenderType.create(
			"sphere_render_type", RenderSetup.builder(CRenderPipelines.SPHERE).createRenderSetup()
		);


}
