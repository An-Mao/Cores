package nws.mc.cores.amlib.the$world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class TheWorld {
    public static final String TheWorldSaveKeyEnable = "TheWorld";
    public static final String TheWorldSaveKeyTime = "TheWorld.Time";
    public static boolean GetTheWorldState(Entity entity){
        return GetTheWorldState(entity.getPersistentData());
    }
    public static boolean GetTheWorldState(CompoundTag tag){
        return tag.getBoolean(TheWorldSaveKeyEnable);
    }
    public static void SetTheWorldState(Entity entity,boolean state){
        SetTheWorldState(entity.getPersistentData(),state);
    }
    public static void SetTheWorldState(CompoundTag tag,boolean state){
        tag.putBoolean(TheWorldSaveKeyEnable,state);
    }
    public static int GetTheWorldTime(Entity entity){
        return GetTheWorldTime(entity.getPersistentData());
    }
    public static int GetTheWorldTime(CompoundTag tag){
        return tag.getInt(TheWorldSaveKeyTime);
    }
    public static void SetTheWorldTime(Entity entity,long time){
        SetTheWorldTime(entity.getPersistentData(),time);
    }
    public static void SetTheWorldTime(CompoundTag tag,long time){
        tag.putLong(TheWorldSaveKeyTime,time);
    }
}
