package dev.anye.mc.cores.capabilities;

import net.minecraft.nbt.CompoundTag;

public interface EasyCapabilityCore {
    void save(CompoundTag nbt);
    void load(CompoundTag nbt);
}
