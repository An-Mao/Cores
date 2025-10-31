package dev.anye.mc.cores.mixin;

import net.minecraft.client.multiplayer.ClientChunkCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientChunkCache.class)
public class ClientChunkCacheMixin {

    @ModifyVariable(method = "getChunk(IILnet/minecraft/world/level/chunk/status/ChunkStatus;Z)Lnet/minecraft/world/level/chunk/LevelChunk;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public int cores$getChunk$x(int x) {
        return 1;
    }
    @ModifyVariable(method = "getChunk(IILnet/minecraft/world/level/chunk/status/ChunkStatus;Z)Lnet/minecraft/world/level/chunk/LevelChunk;", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    public int cores$getChunk$z(int z) {
        return 1;
    }
}
