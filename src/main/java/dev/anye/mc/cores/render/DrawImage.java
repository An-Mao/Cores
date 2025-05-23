package dev.anye.mc.cores.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class DrawImage {
    public static void test(){}
    public static void blit(PoseStack poseStack, ResourceLocation pAtlasLocation, int pX, int pY, int pUOffset, int pVOffset, int pUWidth, int pVHeight) {
        blit(poseStack, pAtlasLocation, pX, pY, 0, (float)pUOffset, (float)pVOffset, pUWidth, pVHeight, 256, 256);
    }
    public static void blit(PoseStack poseStack,ResourceLocation pAtlasLocation, int pX, int pY, int pBlitOffset, float pUOffset, float pVOffset, int pUWidth, int pVHeight, int pTextureWidth, int pTextureHeight) {
        blit(poseStack, pAtlasLocation, pX, pX + pUWidth, pY, pY + pVHeight, pBlitOffset, pUWidth, pVHeight, pUOffset, pVOffset, pTextureWidth, pTextureHeight);
    }
    public static void blit(PoseStack poseStack,ResourceLocation pAtlasLocation, int pX1, int pX2, int pY1, int pY2, int pBlitOffset, int pUWidth, int pVHeight, float pUOffset, float pVOffset, int pTextureWidth, int pTextureHeight) {
        innerBlit(poseStack, pAtlasLocation, pX1, pX2, pY1, pY2, pBlitOffset, (pUOffset + 0.0F) / (float)pTextureWidth, (pUOffset + (float)pUWidth) / (float)pTextureWidth, (pVOffset + 0.0F) / (float)pTextureHeight, (pVOffset + (float)pVHeight) / (float)pTextureHeight);
    }
    public static void innerBlit(PoseStack poseStack, ResourceLocation pAtlasLocation, int pX1, int pX2, int pY1, int pY2, int pBlitOffset, float pMinU, float pMaxU, float pMinV, float pMaxV) {

        System.out.println("wait render");
        /*
        RenderSystem.setShaderTexture(0, pAtlasLocation);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, (float)pX1, (float)pY1, (float)pBlitOffset).setUv(pMinU, pMinV);//.endVertex();
        bufferbuilder.addVertex(matrix4f, (float)pX1, (float)pY2, (float)pBlitOffset).setUv(pMinU, pMaxV);//.endVertex();
        bufferbuilder.addVertex(matrix4f, (float)pX2, (float)pY2, (float)pBlitOffset).setUv(pMaxU, pMaxV);//.endVertex();
        bufferbuilder.addVertex(matrix4f, (float)pX2, (float)pY1, (float)pBlitOffset).setUv(pMaxU, pMinV);//.endVertex();
        BufferUploader.drawWithShader(bufferbuilder.build());

         */
    }
}
