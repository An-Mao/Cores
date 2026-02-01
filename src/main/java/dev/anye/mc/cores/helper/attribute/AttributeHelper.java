package dev.anye.mc.cores.helper.attribute;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Collection;

public class AttributeHelper {
    public static AttributeInstance getAttribute(LivingEntity entity, Holder<Attribute>  attribute){
        return entity.getAttribute(attribute);
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
    public static void setAttribute(LivingEntity entity, Holder<Attribute>  attribute, AttributeModifier modifier){
        AttributeInstance att = getAttribute(entity,attribute);
        if (att != null) {
            if (att.hasModifier(modifier.id())) att.removeModifier(modifier.id());
            att.addPermanentModifier(modifier);
        }
    }
    public static void setAttribute(LivingEntity entity, Holder<Attribute>  attribute, String name, double amount, AttributeModifier.Operation operation){
        setAttribute(entity,attribute,new AttributeModifier(Identifier.parse(name), amount, operation));
    }
    public static void setTempAttribute(LivingEntity entity, Holder<Attribute>  attribute, String name, double amount, AttributeModifier.Operation operation,int tick){
        setTempAttribute(entity,attribute, new AttributeModifier(Identifier.parse(name), amount, operation),tick);
    }
    public static void setTempAttribute(LivingEntity entity, Holder<Attribute>  attribute, AttributeModifier modifier ,int tick){
        AttributeInstance att = getAttribute(entity,attribute);
        MinecraftServer server = entity.level().getServer();
        if (att != null && server != null) {
            if (att.hasModifier(modifier.id())) att.removeModifier(modifier.id());
            att.addPermanentModifier(modifier);
            server.doRunTask(new TickTask(tick, () -> att.removeModifier(modifier)));
        }
    }
}
