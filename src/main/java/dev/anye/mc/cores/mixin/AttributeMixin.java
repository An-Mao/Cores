package dev.anye.mc.cores.mixin;

import dev.anye.mc.cores.am.config.Configs;
import dev.anye.mc.cores.am.config.MixinConfigs;
import dev.anye.mc.cores.am.config.attribute.AttributeData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Attribute.class,remap = false)
public class AttributeMixin {
    @Mutable
    @Shadow @Final private double defaultValue;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void cores$init$fix(String pDescriptionId, double pDefaultValue, CallbackInfo ci) {
        if (MixinConfigs.EnableFixAttributes){
            AttributeData attributeData = Configs.attribute.getConfig(pDescriptionId);
            if (attributeData != null) this.defaultValue = attributeData.getDef();
        }
    }
}
