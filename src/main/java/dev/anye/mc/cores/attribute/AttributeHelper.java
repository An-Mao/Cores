package dev.anye.mc.cores.attribute;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Collection;

public class AttributeHelper {
    public static AttributeInstance getAttribute(LivingEntity entity, Attribute attribute){
        return entity.getAttribute(attribute);
    }
    public static double getAttributeModifierValue(Collection<AttributeModifier> attlist) {
        double dadd = 0;
        double dbase = 0;
        double dtotal = 1;
        for (AttributeModifier al : attlist) {
            if(al.getOperation() == AttributeModifier.Operation.ADDITION){
                dadd += al.getAmount();
            } else if (al.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                dbase += al.getAmount();
            } else if (al.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
                dtotal *= 1.0D + al.getAmount();
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
            AttributeModifier add = new AttributeModifier(name, amount, operation);
            att.addPermanentModifier(add);
        }
    }
    public static void setTempAttribute(LivingEntity entity, Attribute attribute, String name, double amount, AttributeModifier.Operation operation,int tick){
        AttributeInstance att = getAttribute(entity,attribute);
        MinecraftServer server = entity.getServer();
        if (att != null && server != null) {
            AttributeModifier add = new AttributeModifier(name, amount, operation);
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
