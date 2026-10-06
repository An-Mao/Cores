package dev.anye.mc.cores.helper;

import net.minecraft.core.BlockPos;

public class PosHelper {
    public static BlockPos getBlockPos(BlockPos pos, int r){
        return getBlockPos(pos,r,r);
    }
    public static BlockPos getBlockPos(BlockPos pos,int r,int d){
        return new BlockPos(pos.getX() + r,pos.getY() + d,pos.getZ() + r);
    }
}
