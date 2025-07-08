package dev.anye.mc.cores.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EasyCapabilityProvider<CAP extends EasyCapability> implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    protected static Capability<EasyCapability> Capability = CapabilityManager.get(new CapabilityToken<>() {});
    protected CAP Cap = null;
    protected final LazyOptional<CAP> optional = LazyOptional.of(this::create);
    protected EasyCapabilityProvider(){}
    protected CAP create() {
        if (Cap == null){
            Cap = (CAP) new EasyCapability();
        }
        return Cap;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == Capability){
            return optional.cast();
        }
        return LazyOptional.empty();
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        create().save(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        create().load(nbt);
    }
}
