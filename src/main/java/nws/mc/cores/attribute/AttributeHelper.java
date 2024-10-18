package nws.mc.cores.attribute;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Collection;

public class AttributeHelper {
    public static AttributeInstance getAttribute(LivingEntity entity, Attribute attribute){
        return entity.getAttribute(Holder.direct(attribute));
    }
    public static double getAttributeModifierValue(Collection<AttributeModifier> attlist) {
        double dadd = 0;
        double dbase = 0;
        double dtotal = 1;
        for (AttributeModifier al : attlist) {
            if(al.operation() == AttributeModifier.Operation.ADD_VALUE){
                dadd += al.amount();
            } else if (al.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE) {
                dbase += al.amount();
            } else if (al.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                dtotal *= 1.0D + al.amount();
            }
        }
        return (dadd + dadd * dbase) * dtotal;
    }
    public static void setAttribute(LivingEntity entity, Attribute attribute, AttributeModifier modifier){
        AttributeInstance att = getAttribute(entity,attribute);
        if (att != null) {
            att.addPermanentModifier(modifier);
        }
    }
    public static void setAttribute(LivingEntity entity, Attribute attribute, String name, double amount, AttributeModifier.Operation operation){
        AttributeInstance att = getAttribute(entity,attribute);
        if (att != null) {
            AttributeModifier add = new AttributeModifier(ResourceLocation.tryParse(name), amount, operation);
            att.addPermanentModifier(add);
        }
    }
    public static void setTempAttribute(LivingEntity entity, Attribute attribute, String name, double amount, AttributeModifier.Operation operation,int tick){
        AttributeInstance att = getAttribute(entity,attribute);
        MinecraftServer server = entity.getServer();
        if (att != null && server != null) {
            AttributeModifier add = new AttributeModifier(ResourceLocation.tryParse(name), amount, operation);
            att.addPermanentModifier(add);
            server.doRunTask(new TickTask(tick, () -> att.removeModifier(add)));
        }
    }
    public static void setTempAttribute(LivingEntity entity, Attribute attribute, AttributeModifier modifier ,int tick){
        AttributeInstance att = getAttribute(entity,attribute);
        MinecraftServer server = entity.getServer();
        if (att != null && server != null) {
            att.addPermanentModifier(modifier);
            server.doRunTask(new TickTask(tick, () -> att.removeModifier(modifier)));
        }
    }
}
