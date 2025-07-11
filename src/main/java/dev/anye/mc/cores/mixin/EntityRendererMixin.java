package dev.anye.mc.cores.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.anye.mc.cores.cr.CoresRegs;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Unique
    private Entity cores$renderingEntity;
    @Inject(method = "extractRenderState" ,at = @At("HEAD"))
    public void cores$extractRenderState$read(Entity p_entity, EntityRenderState reusedState, float partialTick, CallbackInfo ci){
        cores$renderingEntity = p_entity;
    }
    @Inject(method = "render" ,at = @At("HEAD"))
    public void cores$render$health(EntityRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CallbackInfo ci){
        CoresRegs.ENTITY_RENDER_REG.forEach((s, aEntityRender) -> aEntityRender.render(cores$renderingEntity,renderState,poseStack,bufferSource,packedLight));
    }

}
