package dev.anye.mc.cores.mixin;

import net.minecraft.server.level.ServerChunkCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerChunkCache.class)
public class ServerChunkCacheMixin {
    @ModifyVariable(method = "getChunk", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public int cores$getChunk$x(int x) {
        return 1;
    }
    @ModifyVariable(method = "getChunk", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    public int cores$getChunk$z(int z) {
        return 1;
    }
}
