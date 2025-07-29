package dev.anye.mc.cores.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class DrawSphere {
    // 定义一个自定义的 RenderType
    // 这个 RenderType 将配置 OpenGL 状态以实现半透明效果
    public static final RenderType SPHERE_RENDER_TYPE = RenderType.create(
            "sphere_render_type", // 唯一标识符
            DefaultVertexFormat.POSITION_COLOR, // 顶点格式：位置和颜色
            VertexFormat.Mode.TRIANGLES, // 绘制模式：三角形
            256, // 预分配的缓冲区大小
            false, // useDelegate - 是否使用委托渲染器 (通常是 false)
            false, // noSorting - 是否禁用透明排序 (对于简单形状通常禁用)
            RenderType.CompositeState.builder() // 复合状态构建器
                    .setShaderState(RenderStateShard.POSITION_COLOR_SHADER) // 使用位置-颜色着色器
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY) // 设置为半透明混合模式
                    .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST) // 深度测试：小于等于通过
                    .setCullState(RenderStateShard.NO_CULL) // 不剔除背面 (这样球体内部也能看到)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE) // 写入颜色缓冲区
                    .createCompositeState(false) // 创建复合状态
    );
    /**
     * 绘制一个半透明的球体。
     *
     * @param poseStack 姿态栈，用于变换矩阵。
     * @param bufferSource 多缓冲区源，用于获取顶点消费者。
     * @param radius 球体的半径。
     * @param slices 经度切片数 (垂直切片)。
     * @param stacks 纬度切片数 (水平切片)。
     * @param color ARGB 格式的颜色值 (例如：0x800000FF 表示半透明蓝色)。
     */
    public static void draw(PoseStack poseStack, MultiBufferSource bufferSource, float radius, int slices, int stacks, int color) {
        // 从缓冲区源获取顶点消费者，使用我们自定义的 RenderType
        VertexConsumer consumer = bufferSource.getBuffer(SPHERE_RENDER_TYPE);
        // 获取当前模型视图矩阵和法线矩阵
        Matrix4f modelViewMatrix = poseStack.last().pose();
        Matrix3f normalMatrix = poseStack.last().normal();
        // 提取颜色分量
        float a = ((color >> 24) & 0xFF) / 255.0F;
        float r = ((color >> 16) & 0xFF) / 255.0F;
        float g = ((color >> 8) & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        // 生成球体顶点数据 (UV Sphere)
        for (int i = 0; i < stacks; i++) {
            float phi = (float) Math.PI * i / stacks; // 纬度角 (0 到 PI)
            float sinPhi = (float) Math.sin(phi);
            float cosPhi = (float) Math.cos(phi);
            for (int j = 0; j < slices; j++) {
                float theta = (float) (2.0 * Math.PI * j / slices); // 经度角 (0 到 2*PI)
                float sinTheta = (float) Math.sin(theta);
                float cosTheta = (float) Math.cos(theta);
                // 当前点 (x, y, z)
                float x = radius * sinPhi * cosTheta;
                float y = radius * cosPhi;
                float z = radius * sinPhi * sinTheta;
                // 下一个经度点 (x_next, y_next, z_next)
                float theta_next = (float) (2.0 * Math.PI * (j + 1) / slices);
                float sinTheta_next = (float) Math.sin(theta_next);
                float cosTheta_next = (float) Math.cos(theta_next);
                float x_next = radius * sinPhi * cosTheta_next;
                float y_next = radius * cosPhi;
                float z_next = radius * sinPhi * sinTheta_next;
                // 下一个纬度点 (x_up, y_up, z_up)
                float phi_up = (float) Math.PI * (i + 1) / stacks;
                float sinPhi_up = (float) Math.sin(phi_up);
                float cosPhi_up = (float) Math.cos(phi_up);
                float x_up = radius * sinPhi_up * cosTheta;
                float y_up = radius * cosPhi_up;
                float z_up = radius * sinPhi_up * sinTheta;
                // 下一个经度和纬度点 (x_up_next, y_up_next, z_up_next)
                float x_up_next = radius * sinPhi_up * cosTheta_next;
                float y_up_next = radius * cosPhi_up;
                float z_up_next = radius * sinPhi_up * sinTheta_next;
                // 绘制两个三角形形成一个四边形
                // 顶点顺序：(当前点) -> (下一个经度点) -> (下一个纬度点) -> (下一个经度和纬度点)
                // 三角形 1: (x, y, z), (x_next, y_next, z_next), (x_up, y_up, z_up)
                // 注意：对于球体，法线就是归一化后的顶点位置
                consumer.addVertex(modelViewMatrix, x, y, z).setColor(r, g, b, a).setNormal(poseStack.last(), x, y, z);
                consumer.addVertex(modelViewMatrix, x_next, y_next, z_next).setColor(r, g, b, a).setNormal(poseStack.last(), x_next, y_next, z_next);
                consumer.addVertex(modelViewMatrix, x_up, y_up, z_up).setColor(r, g, b, a).setNormal(poseStack.last(), x_up, y_up, z_up);
                // 三角形 2: (x_next, y_next, z_next), (x_up_next, y_up_next, z_up_next), (x_up, y_up, z_up)
                consumer.addVertex(modelViewMatrix, x_next, y_next, z_next).setColor(r, g, b, a).setNormal(poseStack.last(), x_next, y_next, z_next);
                consumer.addVertex(modelViewMatrix, x_up_next, y_up_next, z_up_next).setColor(r, g, b, a).setNormal(poseStack.last(), x_up_next, y_up_next, z_up_next);
                consumer.addVertex(modelViewMatrix, x_up, y_up, z_up).setColor(r, g, b, a).setNormal(poseStack.last(), x_up, y_up, z_up);
            }
        }
    }
}
