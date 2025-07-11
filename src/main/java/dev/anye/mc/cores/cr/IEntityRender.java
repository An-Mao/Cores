package dev.anye.mc.cores.cr;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;

public interface IEntityRender {
    void render(Entity entity, EntityRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight);
}
