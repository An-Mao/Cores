package dev.anye.mc.cores.cores.the_world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class TheWorld {
    public static final String THE_WORLD_SAVE_KEY_ENABLE = "TheWorld";
    public static final String THE_WORLD_SAVE_KEY_TIME = "TheWorld.Time";
    public static boolean GetTheWorldState(Entity entity){
        return GetTheWorldState(entity.getPersistentData());
    }
    public static boolean GetTheWorldState(CompoundTag tag){
        return tag.getBoolean(THE_WORLD_SAVE_KEY_ENABLE);
    }
    public static void SetTheWorldState(Entity entity,boolean state){
        SetTheWorldState(entity.getPersistentData(),state);
    }
    public static void SetTheWorldState(CompoundTag tag,boolean state){
        tag.putBoolean(THE_WORLD_SAVE_KEY_ENABLE,state);
    }
    public static long GetTheWorldTime(Entity entity){
        return GetTheWorldTime(entity.getPersistentData());
    }
    public static long GetTheWorldTime(CompoundTag tag){
        return tag.getLong(THE_WORLD_SAVE_KEY_TIME);
    }
    public static void SetTheWorldTime(Entity entity,long time){
        SetTheWorldTime(entity.getPersistentData(),time);
    }
    public static void SetTheWorldTime(CompoundTag tag,long time){
        tag.putLong(THE_WORLD_SAVE_KEY_TIME,time);
    }
}
