package dev.anye.mc.cores.render.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public record SphereRenderState(PoseStack.Pose pose, float radius, int slices, int stacks, int color) {
	public void render(VertexConsumer vertexConsumer) {
		Matrix4f modelViewMatrix = pose.pose();
		for (int i = 0; i < stacks; i++) {
			float phi = (float) Math.PI * i / stacks;
			float sinPhi = (float) Math.sin(phi);
			float cosPhi = (float) Math.cos(phi);
			for (int j = 0; j < slices; j++) {
				float theta = (float) (2.0 * Math.PI * j / slices);
				float sinTheta = (float) Math.sin(theta);
				float cosTheta = (float) Math.cos(theta);
				float x = radius * sinPhi * cosTheta;
				float y = radius * cosPhi;
				float z = radius * sinPhi * sinTheta;
				float theta_next = (float) (2.0 * Math.PI * (j + 1) / slices);
				float sinTheta_next = (float) Math.sin(theta_next);
				float cosTheta_next = (float) Math.cos(theta_next);
				float x_next = radius * sinPhi * cosTheta_next;
				float y_next = radius * cosPhi;
				float z_next = radius * sinPhi * sinTheta_next;
				float phi_up = (float) Math.PI * (i + 1) / stacks;
				float sinPhi_up = (float) Math.sin(phi_up);
				float cosPhi_up = (float) Math.cos(phi_up);
				float x_up = radius * sinPhi_up * cosTheta;
				float y_up = radius * cosPhi_up;
				float z_up = radius * sinPhi_up * sinTheta;
				float x_up_next = radius * sinPhi_up * cosTheta_next;
				float y_up_next = radius * cosPhi_up;
				float z_up_next = radius * sinPhi_up * sinTheta_next;
				vertexConsumer.addVertex(modelViewMatrix, x, y, z).setColor(color).setNormal(pose, x, y, z);
				vertexConsumer.addVertex(modelViewMatrix, x_next, y_next, z_next).setColor(color).setNormal(pose, x_next, y_next, z_next);
				vertexConsumer.addVertex(modelViewMatrix, x_up, y_up, z_up).setColor(color).setNormal(pose, x_up, y_up, z_up);
				vertexConsumer.addVertex(modelViewMatrix, x_next, y_next, z_next).setColor(color).setNormal(pose, x_next, y_next, z_next);
				vertexConsumer.addVertex(modelViewMatrix, x_up_next, y_up_next, z_up_next).setColor(color).setNormal(pose, x_up_next, y_up_next, z_up_next);
				vertexConsumer.addVertex(modelViewMatrix, x_up, y_up, z_up).setColor(color).setNormal(pose, x_up, y_up, z_up);
			}
		}
	}

	public void oldRender(VertexConsumer vertexConsumer) {
		Matrix4f modelViewMatrix = pose.pose();// poseStack.last().pose();
		Matrix3f normalMatrix = pose.normal();//poseStack.last().normal();
		float a = ((color >> 24) & 0xFF) / 255.0F;
		float r = ((color >> 16) & 0xFF) / 255.0F;
		float g = ((color >> 8) & 0xFF) / 255.0F;
		float b = (color & 0xFF) / 255.0F;
		for (int i = 0; i < stacks; i++) {
			float phi = (float) Math.PI * i / stacks;
			float sinPhi = (float) Math.sin(phi);
			float cosPhi = (float) Math.cos(phi);
			for (int j = 0; j < slices; j++) {
				float theta = (float) (2.0 * Math.PI * j / slices);
				float sinTheta = (float) Math.sin(theta);
				float cosTheta = (float) Math.cos(theta);
				float x = radius * sinPhi * cosTheta;
				float y = radius * cosPhi;
				float z = radius * sinPhi * sinTheta;
				float theta_next = (float) (2.0 * Math.PI * (j + 1) / slices);
				float sinTheta_next = (float) Math.sin(theta_next);
				float cosTheta_next = (float) Math.cos(theta_next);
				float x_next = radius * sinPhi * cosTheta_next;
				float y_next = radius * cosPhi;
				float z_next = radius * sinPhi * sinTheta_next;
				float phi_up = (float) Math.PI * (i + 1) / stacks;
				float sinPhi_up = (float) Math.sin(phi_up);
				float cosPhi_up = (float) Math.cos(phi_up);
				float x_up = radius * sinPhi_up * cosTheta;
				float y_up = radius * cosPhi_up;
				float z_up = radius * sinPhi_up * sinTheta;
				float x_up_next = radius * sinPhi_up * cosTheta_next;
				float y_up_next = radius * cosPhi_up;
				float z_up_next = radius * sinPhi_up * sinTheta_next;
				vertexConsumer.addVertex(modelViewMatrix, x, y, z).setColor(r, g, b, a).setNormal(
						pose //poseStack.last()
						, x,
						y, z);
				vertexConsumer.addVertex(modelViewMatrix, x_next, y_next, z_next).setColor(r, g, b, a)
						.setNormal(pose, x_next, y_next, z_next);
				vertexConsumer.addVertex(modelViewMatrix, x_up, y_up, z_up).setColor(r, g, b, a)
						.setNormal(pose, x_up, y_up, z_up);
				vertexConsumer.addVertex(modelViewMatrix, x_next, y_next, z_next).setColor(r, g, b, a)
						.setNormal(pose, x_next, y_next, z_next);
				vertexConsumer.addVertex(modelViewMatrix, x_up_next, y_up_next, z_up_next)
						.setColor(r, g, b, a)
						.setNormal(pose, x_up_next, y_up_next, z_up_next);
				vertexConsumer.addVertex(modelViewMatrix, x_up, y_up, z_up).setColor(r, g, b, a)
						.setNormal(pose, x_up, y_up, z_up);
			}
		}
	}
}
