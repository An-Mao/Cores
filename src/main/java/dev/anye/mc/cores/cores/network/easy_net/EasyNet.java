package dev.anye.mc.cores.cores.network.easy_net;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EasyNet extends EasyNetCore{
    @Override
    public void client(Supplier<NetworkEvent.Context> contextSupplier , CompoundTag dat) {
        contextSupplier.get().setPacketHandled(true);
    }
    @Override
    public void server(Supplier<NetworkEvent.Context> contextSupplier, CompoundTag dat) {
        contextSupplier.get().setPacketHandled(true);
    }
}
