package dev.anye.mc.cores.mixin;

import com.mojang.logging.LogUtils;
import dev.anye.mc.cores.am.data.entity.TheWorld;
import dev.anye.mc.cores.am.register.DataReg;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityTickList;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;
import java.util.function.Consumer;

@Mixin(value = EntityTickList.class,priority = Integer.MAX_VALUE,remap = false)
public class EntityTickListMixin {
    @Unique
    private static final Logger Cores$LOGGER = LogUtils.getLogger();
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
            Cores$LOGGER.warn("Concurrent iteration of entity tick list");
            throw new UnsupportedOperationException("Only one concurrent iteration supported");
        } else {
            this.iterated = this.active;
            try {
                for(Entity entity : this.active.values()) {
                    TheWorld theWorld = entity.get(DataReg.THE_WORLD.get());
                    if (theWorld != null && theWorld.time() > 0) {
                        entity.setComponent(DataReg.THE_WORLD.get(), new TheWorld(theWorld.time() - 1));
                        continue;
                    }
                    pEntity.accept(entity);
                }
            } finally {
                this.iterated = null;
            }
        }
    }
}
