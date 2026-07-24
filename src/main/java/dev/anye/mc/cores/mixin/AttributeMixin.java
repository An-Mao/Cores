package dev.anye.mc.cores.mixin;

import dev.anye.mc.cores.amlib.config.Configs;
import dev.anye.mc.cores.amlib.config.attribute.AttributeData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Attribute.class)
public class AttributeMixin {
    @Mutable
    @Shadow @Final private double defaultValue;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void cores$init$fix(String pDescriptionId, double pDefaultValue, CallbackInfo ci) {
		Configs.GENERAL.ifPresent(generalConfigData -> {
			if (generalConfigData.isMixinAttributes()){
				Configs.ATTRIBUTE.ifPresent(stringAttributeDataMap -> {
					AttributeData attributeData = stringAttributeDataMap.getOrDefault(pDescriptionId,null);
					if (attributeData != null) {
						this.defaultValue = attributeData.def();
					}
				});
			}
		});
    }
}
