package nws.mc.cores.mixin;

import net.minecraft.world.entity.ai.attributes.Attribute;
import nws.mc.cores.amlib.config.Configs;
import nws.mc.cores.amlib.config.attribute.AttributeData;
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
        if (Configs.general.getDatas().isMixinAttributes()){
            AttributeData attributeData = Configs.attribute.getConfig(pDescriptionId);
            if (attributeData != null) {
                this.defaultValue = attributeData.getDef();
            }
        }
    }
}
