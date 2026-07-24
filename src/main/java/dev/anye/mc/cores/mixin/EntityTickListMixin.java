package dev.anye.mc.cores.mixin;

import dev.anye.mc.cores.cores.config.Configs;
import dev.anye.mc.cores.cores.config.the_world.TheWorldConfigData;
import dev.anye.mc.cores.cores.the_world.TheWorld;
import dev.anye.mc.cores.entity.EntityHelper;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityTickList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    @Inject(method = "forEach",at = @At("HEAD"), cancellable = true)
    public void cores$forEach$theWorld(Consumer<Entity> pEntity, CallbackInfo ci) {
        if (this.iterated != null) {
            throw new UnsupportedOperationException("Only one concurrent iteration supported");
        } else {
            this.iterated = this.active;
            try {
				TheWorldConfigData theWorldConfigData = Configs.THE_WORLD.orElse(null);
				if (theWorldConfigData == null){
					this.active.forEach((integer, entity) -> pEntity.accept(entity));
				}else {
					this.active.forEach((integer, entity) -> {
						if (theWorldConfigData.isEnable() && TheWorld.GetTheWorldState(entity)) {
							if (theWorldConfigData.getType() == 1) {
								if (EntityHelper.getLevelTime(entity) - TheWorld.GetTheWorldTime(entity) < theWorldConfigData.getStopTime()) return;
							} else if (theWorldConfigData.getType() == 2) {
								long time = TheWorld.GetTheWorldTime(entity);
								if (time < theWorldConfigData.getStopTime()) {
									TheWorld.SetTheWorldTime(entity, time + 1);
									return;
								}
							}
							TheWorld.SetTheWorldState(entity, false);
						}
						pEntity.accept(entity);
					});
				}
            } finally {
                this.iterated = null;
            }
        }
		ci.cancel();
    }
}
