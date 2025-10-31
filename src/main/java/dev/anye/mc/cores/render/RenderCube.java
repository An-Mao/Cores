package dev.anye.mc.cores.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Matrix4f;
@ApiStatus.Internal
public class RenderCube {
    public static void renderEndPortalCube(Matrix4f pose, MultiBufferSource buffer,float size){
        renderEndCube(pose, buffer.getBuffer(RenderType.endPortal()),size);
    }
    public static void renderEndGatewayCube(Matrix4f pose, MultiBufferSource buffer,float size){
        renderEndCube(pose, buffer.getBuffer(RenderType.endGateway()),size);
    }
    public static void renderEndCube( Matrix4f pPose, VertexConsumer pConsumer, float size) {
        renderFace( pPose, pConsumer, 0.0F, size, 0.0F, size, size, size, size, size, Direction.SOUTH);
        renderFace( pPose, pConsumer, 0.0F, size, size, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
        renderFace( pPose, pConsumer, size, size, size, 0.0F, 0.0F, size, size, 0.0F, Direction.EAST);
        renderFace( pPose, pConsumer, 0.0F, 0.0F, 0.0F, size, 0.0F, size, size, 0.0F, Direction.WEST);
        renderFace( pPose, pConsumer, 0.0F, size, 0.0f, 0.0f, 0.0F, 0.0F, size, size, Direction.DOWN);
        renderFace( pPose, pConsumer, 0.0F, size, size, size, size, size, 0.0F, 0.0F, Direction.UP);
    }

    private static void renderFace(Matrix4f pPose, VertexConsumer pConsumer, float pX0, float pX1, float pY0, float pY1, float pZ0, float pZ1, float pZ2, float pZ3, Direction pDirection) {
        pConsumer.addVertex(pPose, pX0, pY0, pZ0);//.endVertex();
        pConsumer.addVertex(pPose, pX1, pY0, pZ1);//.endVertex();
        pConsumer.addVertex(pPose, pX1, pY1, pZ2);//.endVertex();
        pConsumer.addVertex(pPose, pX0, pY1, pZ3);//.endVertex();
    }
}
