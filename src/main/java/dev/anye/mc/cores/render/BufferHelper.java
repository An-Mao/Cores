package dev.anye.mc.cores.render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import org.joml.Matrix4f;

public class BufferHelper {
    public static void addColorVertex(BufferBuilder buffer, int x, int y,int color) {
        addColorVertex(buffer,x,y,0,color);
    }
    public static void addColorVertex(BufferBuilder buffer, int x, int y,int z, int color) {
        buffer.addVertex(x, y, z).setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);
    }
    public static void addColorVertex(BufferBuilder buffer, Matrix4f matrix, int x, int y,int color) {
        addColorVertex(buffer,matrix,x,y,0,color);
    }
    public static void addColorVertex(BufferBuilder buffer, Matrix4f matrix, int x, int y,int z, int color) {
        buffer.addVertex(matrix,x, y, z).setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);
    }
}
