package dev.anye.mc.cores.render;

import com.mojang.blaze3d.buffers.BufferType;
import com.mojang.blaze3d.buffers.BufferUsage;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import org.joml.Matrix4fStack;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Consumer;

public class GuiGraphicsX {
    public static final int POSITION_COLOR_BYTE_SIZE = 10 * DefaultVertexFormat.POSITION_COLOR.getVertexSize();
    public static void render(Consumer<BufferBuilder> builderConsumer){
        try(ByteBufferBuilder byteBufferBuilder = new ByteBufferBuilder(POSITION_COLOR_BYTE_SIZE)) {
            BufferBuilder builder = new BufferBuilder(byteBufferBuilder, VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            builderConsumer.accept(builder);
            render(builder);
        }
    }
    public static void render(BufferBuilder builder){
        try(MeshData meshData = builder.buildOrThrow()) {
            GpuBuffer gpuBuffer = RenderSystem.getDevice().createBuffer(null, BufferType.VERTICES, BufferUsage.STATIC_WRITE,meshData.vertexBuffer());
            Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
            matrix4fStack.pushMatrix();
            RenderSystem.setShaderColor(1,1,1,1);
            try (RenderPass renderPass = RenderSystem.getDevice()
                    .createCommandEncoder()
                    .createRenderPass(Minecraft.getInstance().getMainRenderTarget().getColorTexture(), OptionalInt.empty(),Minecraft.getInstance().getMainRenderTarget().getDepthTexture(), OptionalDouble.empty())){
                renderPass.setPipeline(RenderPipelines.GUI);
                renderPass.setVertexBuffer(0,gpuBuffer);
                renderPass.draw(0,gpuBuffer.size);
            }
            matrix4fStack.popMatrix();
            RenderSystem.setShaderColor(1,1,1,1);
            gpuBuffer.close();
        }
    }
}
