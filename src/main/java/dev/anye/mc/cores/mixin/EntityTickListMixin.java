package dev.anye.mc.cores.mixin;

import dev.anye.mc.cores.amlib.config.Configs;
import dev.anye.mc.cores.amlib.the$world.TheWorld;
import dev.anye.mc.cores.entity.EntityHelper;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityTickList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.function.Consumer;

@Mixin(EntityTickList.class)
public class EntityTickListMixin {
    @Shadow
    private Int2ObjectMap<Entity> active;
    @Shadow
    @Nullable
    private Int2ObjectMap<Entity> iterated;
    /**
     * @author AnMao
     * @reason The World
     */
    @Overwrite
    public void forEach(Consumer<Entity> pEntity) {
        if (this.iterated != null) {
            throw new UnsupportedOperationException("Only one concurrent iteration supported");
        } else {
            this.iterated = this.active;

            try {
                for(Entity entity : this.active.values()) {
                    if (Configs.THE_WORLD.getData().isEnable()) {
                        if (TheWorld.GetTheWorldState(entity)) {
                            int time = TheWorld.GetTheWorldTime(entity);
                            if (Configs.THE_WORLD.getData().getType() == 1) {
                                if (EntityHelper.getLevelTime(entity) - time < Configs.THE_WORLD.getData().getStopTime()) {
                                    continue;
                                }
                            } else if (Configs.THE_WORLD.getData().getType() == 2) {
                                if (time < Configs.THE_WORLD.getData().getStopTime()) {
                                    TheWorld.SetTheWorldTime(entity,time+1);
                                    continue;
                                }
                            }
                            TheWorld.SetTheWorldState(entity,false);
                        }
                    }
                    pEntity.accept(entity);
                }
            } finally {
                this.iterated = null;
            }

        }
    }
}
