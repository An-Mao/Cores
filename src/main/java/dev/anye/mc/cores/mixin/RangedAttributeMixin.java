package dev.anye.mc.cores.mixin;

import dev.anye.mc.cores.am.config.Configs;
import dev.anye.mc.cores.am.config.MixinConfigs;
import dev.anye.mc.cores.am.config.attribute.AttributeData;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RangedAttribute.class)
public class RangedAttributeMixin {
	@Mutable
	@Shadow
	@Final
	private double minValue;
	@Mutable
	@Shadow
	@Final
	private double maxValue;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void cores$init$modifyMaxHealth(String descriptionId, double defaultValue, double minValue, double maxValue, CallbackInfo ci) {
		if (MixinConfigs.EnableFixAttributes) {
			Configs.ATTRIBUTE.ifPresent(map -> {
				AttributeData attributeData = map.getOrDefault(descriptionId,null);
				if (attributeData != null) {
					this.minValue = attributeData.min();
					this.maxValue = attributeData.max();
				}
			});
		}
	}
}
